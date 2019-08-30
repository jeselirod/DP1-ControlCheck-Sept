
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.QuoletRepository;
import security.LoginService;
import security.UserAccount;
import domain.Conference;
import domain.Quolet;

@Service
@Transactional
public class QuoletService {

	@Autowired
	private QuoletRepository	quoletRepository;
	@Autowired
	private Validator			validator;


	public Quolet create() {
		final Quolet res = new Quolet();

		res.setConference(new Conference());
		res.setTicker("");
		res.setPublicationMoment(null);
		res.setDraftMode(0);
		res.setBody("");
		res.setNumMonth(null);
		res.setPicture("");
		res.setXxxx("");

		return res;
	}

	public Collection<Quolet> findAll() {
		return this.quoletRepository.findAll();
	}

	public Quolet findOne(final Integer id) {
		return this.quoletRepository.findOne(id);
	}

	public Quolet save(final Quolet q) {
		Quolet res;

		//COMPROBAMOS QUE LA QUE ESTA EN BD ESTA EN DRAFT MODE
		if (q.getId() != 0) {
			final Quolet old = this.quoletRepository.findOne(q.getId());
			Assert.isTrue(old.getDraftMode() == 0);
		}

		//COMPROBAMOS QUE EL ACTOR LOGEADO ES UN ADMIN
		final UserAccount user = LoginService.getPrincipal();
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		res = this.quoletRepository.save(q);

		return res;

	}

	public void delete(final Quolet q) {
		Assert.isTrue(q.getDraftMode() == 0);
		final UserAccount user = LoginService.getPrincipal();
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		this.quoletRepository.delete(q);

	}

	public Quolet reconstruct(final Quolet quolet, final BindingResult binding) {
		Quolet res;

		if (quolet.getId() == 0) {
			res = quolet;

			res.setTicker(this.generarTicker());
			res.setPublicationMoment(null);
			res.setNumMonth(null);

			this.validator.validate(res, binding);
			return res;

		} else {
			res = this.quoletRepository.findOne(quolet.getId());
			final Quolet copy = new Quolet();

			copy.setConference(res.getConference());
			copy.setTicker(res.getTicker());
			copy.setPublicationMoment(res.getPublicationMoment());
			copy.setNumMonth(quolet.getNumMonth());

			copy.setDraftMode(quolet.getDraftMode());
			copy.setBody(quolet.getBody());
			copy.setPicture(quolet.getPicture());
			copy.setXxxx(quolet.getXxxx());

			this.validator.validate(copy, binding);

			return copy;
		}

	}

	//METODOS AUXILIARES

	//Generar ticker
	public String generarTicker() {
		final int tamLetras = 3;
		final int tam = 4;
		String d = "";
		final String b = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		for (int i = 0; i < tamLetras; i++) {
			final Integer random = (int) (Math.floor(Math.random() * b.length()) % b.length());
			d = d + b.charAt(random);
		}

		String ticker = d + "-";
		final String a = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		for (int i = 0; i < tam; i++) {
			final Integer random = (int) (Math.floor(Math.random() * a.length()) % a.length());
			ticker = ticker + a.charAt(random);
		}

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
	public Integer getMonths(final Quolet quolet) {
		Integer res = 0;

		final Date dateCheck = quolet.getPublicationMoment();
		final Date actual = new Date();

		final Integer difA = ((actual.getYear() + 1900) - (dateCheck.getYear() + 1900));
		res = difA * 12 + actual.getMonth() - dateCheck.getMonth();

		return res;
	}
}
