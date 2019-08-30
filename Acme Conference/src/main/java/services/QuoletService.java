
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
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

}
