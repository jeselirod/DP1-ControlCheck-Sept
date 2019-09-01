
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.QuoletRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Conference;
import domain.Quolet;

@Service
@Transactional
public class QuoletService {

	@Autowired
	private QuoletRepository		quoletRepository;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private Validator				validator;
	@Autowired
	private AdministratorService	administratorService;


	public Quolet create() {
		final Quolet res = new Quolet();

		res.setConference(new Conference());
		res.setTicker("");
		res.setPublicationMoment(null);
		res.setDraftMode(1);
		res.setBody("");
		res.setNumMonth(null);
		res.setPicture("");
		res.setXxxx("");
		res.setAdmin(new Administrator());

		return res;
	}

	public Collection<Quolet> findAll() {
		return this.quoletRepository.findAll();
	}

	public Quolet findOne(final Integer id) {
		return this.quoletRepository.findOne(id);
	}

	public Collection<Quolet> getQuoletsByAdmin(final Integer adminId) {
		return this.quoletRepository.getQuoletsByAdmin(adminId);
	}

	public Quolet save(final Quolet q) {
		Quolet res;

		//COMPROBAMOS QUE LA QUE ESTA EN BD ESTA EN DRAFT MODE
		if (q.getId() != 0) {
			final Quolet old = this.quoletRepository.findOne(q.getId());
			Assert.isTrue(old.getDraftMode() == 1);
		}

		//COMPROBAMOS QUE EL ACTOR LOGEADO ES UN ADMIN
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());
		Assert.isTrue(a.equals(q.getAdmin()));

		res = this.quoletRepository.save(q);

		return res;

	}

	public void delete(final Quolet q) {
		Assert.isTrue(q.getDraftMode() == 1);
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());
		Assert.isTrue(a.equals(q.getAdmin()));

		this.quoletRepository.delete(q);

	}

	public Quolet reconstruct(final Quolet quolet, final BindingResult binding) {
		Quolet res;

		final UserAccount user = LoginService.getPrincipal();
		final Administrator admin = this.administratorService.getAdministratorByUserAccount(user.getId());

		if (quolet.getId() == 0) {
			res = quolet;
			res.setAdmin(admin);
			res.setTicker(this.generarTicker());
			res.setPublicationMoment(null);
			res.setNumMonth(null);

			if (res.getDraftMode() == 0) {
				res.setPublicationMoment(this.fechaPasado());
				res.setNumMonth(QuoletService.getMonths(res));
			}

			if (res.getConference() != null)
				if (res.getConference().getFinalMode() == 0 || !(res.getConference().getAdmin().equals(admin)))
					binding.rejectValue("conference", "ConferenceNoValid");

			this.validator.validate(res, binding);
		} else {
			res = this.quoletRepository.findOne(quolet.getId());
			final Quolet copy = new Quolet();
			copy.setId(res.getId());
			copy.setVersion(res.getVersion());
			copy.setTicker(res.getTicker());
			copy.setAdmin(res.getAdmin());

			copy.setConference(quolet.getConference());
			copy.setDraftMode(quolet.getDraftMode());
			copy.setBody(quolet.getBody());
			copy.setPicture(quolet.getPicture());
			copy.setXxxx(quolet.getXxxx());

			if (quolet.getDraftMode() == 0) {
				copy.setPublicationMoment(this.fechaPasado());
				copy.setNumMonth(QuoletService.getMonths(copy));
			} else {
				copy.setPublicationMoment(null);
				copy.setNumMonth(null);
			}

			if (res.getConference() != null)
				if (res.getConference().getFinalMode() == 0 || !(res.getConference().getAdmin().equals(admin)))
					binding.rejectValue("conference", "ConferenceNoValid");

			this.validator.validate(copy, binding);
			res = copy;
		}
		return res;
	}

	//METODOS AUXILIARES

	//Generar ticker
	public String generarTicker() {
		String ticker = "";

		final int tam = 4;
		final String a = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		for (int i = 0; i < tam; i++) {
			final Integer random = (int) (Math.floor(Math.random() * a.length()) % a.length());
			ticker = ticker + a.charAt(random);
		}
		ticker = ticker + "-";

		final Integer ano = new Date().getYear() + 1900;
		final Integer mes = new Date().getMonth() + 1;
		final Integer dia = new Date().getDate();

		String day = dia.toString();
		String month = mes.toString();
		if (mes < 10)
			month = "0" + mes.toString();
		if (dia < 10)
			day = "0" + dia.toString();
		ticker = ticker + ano.toString().substring(ano.toString().length() - 2, ano.toString().length()) + month + day;

		return ticker;
	}

	//Fecha en pasado
	public Date fechaPasado() {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date()); //tuFechaBase es un Date;
		calendar.add(Calendar.MINUTE, -2); //minutosASumar es int.
		//lo que más quieras sumar
		final Date fechaSalida = calendar.getTime(); //Y ya tienes la fecha sumada.
		return fechaSalida;
	}

	//Calcula los meses de diferencia de un quolet publicado
	public static Integer getMonths(final Quolet quolet) {
		Integer res = 0;

		final Date dateCheck = quolet.getPublicationMoment();
		final Date actual = new Date();

		final Integer difA = ((actual.getYear() + 1900) - (dateCheck.getYear() + 1900));
		res = difA * 12 + actual.getMonth() - dateCheck.getMonth();

		return res;
	}

	//METODO QUE ACTUALIZA LOS MESES DE LOS QuoletS QUE ESTAN EN SAVE MODE
	public void updateMonths() {
		List<Quolet> quolets = new ArrayList<>();

		final UserAccount user = LoginService.getPrincipal();

		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
		final Administrator a = (Administrator) this.actorService.getActorByUserAccount(user.getId());
		quolets = (List<Quolet>) this.quoletRepository.getQuoletsByAdmin(a.getId());

		for (int i = 0; i < quolets.size(); i++)
			if (quolets.get(i).getDraftMode() == 0) {
				final Integer months = QuoletService.getMonths(quolets.get(i));
				quolets.get(i).setNumMonth(months);
				this.quoletRepository.save(quolets.get(i));
			}
	}
}
