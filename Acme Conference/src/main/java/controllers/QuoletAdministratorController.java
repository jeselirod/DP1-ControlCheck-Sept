
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.AdministratorService;
import services.ConferenceService;
import services.QuoletService;
import domain.Actor;
import domain.Administrator;
import domain.Conference;
import domain.Quolet;

@Controller
@RequestMapping("/quolet/administrator")
public class QuoletAdministratorController {

	@Autowired
	private QuoletService			quoletService;

	@Autowired
	private ConferenceService		conferenceService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			final UserAccount user = LoginService.getPrincipal();
			final Administrator admin = this.administratorService.getAdministratorByUserAccount(user.getId());
			final Collection<Quolet> quolets = this.quoletService.getQuoletsByAdmin(admin.getId());

			final String lang = LocaleContextHolder.getLocale().getLanguage();

			this.quoletService.updateMonths();

			result = new ModelAndView("quolet/list");
			result.addObject("quolets", quolets);
			result.addObject("lang", lang);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView showQuolet(@RequestParam final Integer quoletId) {
		ModelAndView result;
		try {
			final Quolet quolet = this.quoletService.findOne(quoletId);
			final Conference conference = this.conferenceService.findOne(quolet.getConference().getId());
			Assert.notNull(conference);
			Assert.notNull(quolet);

			final String lang = LocaleContextHolder.getLocale().getLanguage();

			result = new ModelAndView("quolet/show");
			result.addObject("quolet", quolet);
			result.addObject("conference", conference);
			result.addObject("lang", lang);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:list.do");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		final Quolet quolet = this.quoletService.create();

		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());
		final Collection<Conference> conferences = this.conferenceService.getConferecesInFinalModeByAdmin(a.getId());

		result = new ModelAndView("quolet/edit");
		result.addObject("quolet", quolet);
		result.addObject("conferences", conferences);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer quoletId) {
		ModelAndView result;
		try {
			final Quolet quolet = this.quoletService.findOne(quoletId);
			final UserAccount user = LoginService.getPrincipal();
			final Administrator admin = this.administratorService.getAdministratorByUserAccount(user.getId());

			final Collection<Conference> conferences = this.conferenceService.getConferecesInFinalModeByAdmin(admin.getId());
			Assert.notNull(quolet);
			Assert.notNull(conferences);
			result = new ModelAndView("quolet/edit");
			result.addObject("quolet", quolet);
			result.addObject("conferences", conferences);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(final Quolet quolet, final BindingResult binding) {

		ModelAndView result;
		try {
			final Quolet q = this.quoletService.reconstruct(quolet, binding);
			if (!binding.hasErrors()) {
				this.quoletService.save(q);
				result = new ModelAndView("redirect:list.do");
			} else {
				final UserAccount user = LoginService.getPrincipal();
				final Actor a = this.actorService.getActorByUserAccount(user.getId());
				final Collection<Conference> conferences = this.conferenceService.getConferecesInFinalModeByAdmin(a.getId());
				result = new ModelAndView("quolet/edit");
				result.addObject("quolet", quolet);
				result.addObject("conferences", conferences);
			}
		} catch (final Exception e) {
			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorService.getActorByUserAccount(user.getId());
			final Collection<Conference> conferences = this.conferenceService.getConferecesInFinalModeByAdmin(a.getId());
			result = new ModelAndView("quolet/edit");
			result.addObject("quolet", quolet);
			result.addObject("conferences", conferences);
			result.addObject("exception", e);
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Quolet quolet, final BindingResult binding) {
		ModelAndView result;
		try {
			final Quolet q = this.quoletService.reconstruct(quolet, binding);
			if (!binding.hasErrors()) {
				this.quoletService.delete(q);
				result = new ModelAndView("redirect:list.do");
			} else {
				final UserAccount user = LoginService.getPrincipal();
				final Actor a = this.actorService.getActorByUserAccount(user.getId());
				final Collection<Conference> conferences = this.conferenceService.getConferecesInFinalModeByAdmin(a.getId());
				result = new ModelAndView("quolet/edit");
				result.addObject("quolet", quolet);
				result.addObject("conferences", conferences);
			}
		} catch (final Exception e) {
			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorService.getActorByUserAccount(user.getId());
			final Collection<Conference> conferences = this.conferenceService.getConferecesInFinalModeByAdmin(a.getId());
			result = new ModelAndView("quolet/edit");
			result.addObject("quolet", quolet);
			result.addObject("conferences", conferences);
			result.addObject("exception", e);
		}
		return result;
	}

}
