
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConferenceService;
import services.QuoletService;
import domain.Conference;
import domain.Quolet;

@Controller
@RequestMapping("/quolet/administrator")
public class QuoletAdministratorController {

	@Autowired
	private QuoletService		quoletService;

	@Autowired
	private ConferenceService	conferenceService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		final Quolet quolet = this.quoletService.create();
		final Collection<Conference> conferences = this.conferenceService.getConferencesInSaveMode();

		result = new ModelAndView("quolet/edit");
		result.addObject("quolet", quolet);
		result.addObject("conferences", conferences);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(final Quolet quolet, final BindingResult binding) {

		ModelAndView result;
		try {
			final Quolet q = this.quoletService.reconstruct(quolet, binding);
			if (!binding.hasErrors()) {
				this.quoletService.save(q);
				result = new ModelAndView("redirect:../../conference/administrator/list.do");
			} else {
				final Collection<Conference> conferences = this.conferenceService.getConferencesInSaveMode();
				result = new ModelAndView("quolet/edit");
				result.addObject("quolet", quolet);
				result.addObject("conferences", conferences);
			}
		} catch (final Exception e) {
			final Collection<Conference> conferences = this.conferenceService.getConferencesInSaveMode();
			result = new ModelAndView("quolet/edit");
			result.addObject("quolet", quolet);
			result.addObject("conferences", conferences);
			result.addObject("exception", e);
		}
		return result;
	}

}