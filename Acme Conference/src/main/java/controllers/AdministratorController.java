/*
 * AdministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ConferenceService;
import services.CustomizableSystemService;
import services.QuoletService;
import domain.Administrator;
import forms.RegistrationFormAdmin;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private ConferenceService			conferenceService;

	@Autowired
	private AdministratorService		administratorService;

	@Autowired
	private CustomizableSystemService	customizableService;

	@Autowired
	private QuoletService				quoletService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	@RequestMapping("/dashboard")
	public ModelAndView dashboard() {
		final ModelAndView result;

		final List<Object[]> getAvgDesvQuoletsByConference = this.quoletService.getAvgDesvNumberQuoletsByConference();
		final Double getAvgQuoletsByConference = (Double) getAvgDesvQuoletsByConference.get(0)[0];
		final Double getDesvQuoletsByConference = (Double) getAvgDesvQuoletsByConference.get(0)[1];

		final Double ratioInDraftMode = this.quoletService.RatioInDraftMode();
		final Double ratioOutDraftMode = this.quoletService.RatioOutDraftMode();

		final List<Object[]> getAvgMinMaxDesvSubmissionsByConference = this.conferenceService.getAvgMinMaxDesvSubmissionsByConference();
		final Double getAvgSubmissionsByConference = (Double) getAvgMinMaxDesvSubmissionsByConference.get(0)[0];
		final Double getMinSubmissionsByConference = (Double) getAvgMinMaxDesvSubmissionsByConference.get(0)[1];
		final Double getMaxSubmissionsByConference = (Double) getAvgMinMaxDesvSubmissionsByConference.get(0)[2];
		final Double getDesvSubmissionsByConference = (Double) getAvgMinMaxDesvSubmissionsByConference.get(0)[3];

		final List<Object[]> getAvgMinMaxDesvRegistrationByConference = this.conferenceService.getAvgMinMaxDesvRegistrationByConference();
		final Double getAvgRegistrationByConference = (Double) getAvgMinMaxDesvRegistrationByConference.get(0)[0];
		final Double getMinRegistrationByConference = (Double) getAvgMinMaxDesvRegistrationByConference.get(0)[1];
		final Double getMaxRegistrationByConference = (Double) getAvgMinMaxDesvRegistrationByConference.get(0)[2];
		final Double getDesvRegistrationByConference = (Double) getAvgMinMaxDesvRegistrationByConference.get(0)[3];

		final List<Object[]> getAvgMinMaxDesvFeesByConference = this.conferenceService.getAvgMinMaxDesvFeesByConference();
		final Double getAvgFeesByConference = (Double) getAvgMinMaxDesvFeesByConference.get(0)[0];
		final Double getMinFeesByConference = (Double) getAvgMinMaxDesvFeesByConference.get(0)[1];
		final Double getMaxFeesByConference = (Double) getAvgMinMaxDesvFeesByConference.get(0)[2];
		final Double getDesvFeesByConference = (Double) getAvgMinMaxDesvFeesByConference.get(0)[3];

		final List<Object[]> getAvgMinMaxDesvDaysByConference = this.conferenceService.getAvgMinMaxDesvDaysByConference();
		final Double getAvgDaysByConference = (Double) getAvgMinMaxDesvDaysByConference.get(0)[0];
		final Integer getMinDaysByConference = (Integer) getAvgMinMaxDesvDaysByConference.get(0)[1];
		final Integer getMaxDaysByConference = (Integer) getAvgMinMaxDesvDaysByConference.get(0)[2];
		final Double getDesvDaysByConference = (Double) getAvgMinMaxDesvDaysByConference.get(0)[3];

		result = new ModelAndView("administrator/dashboard");

		result.addObject("getAvgQuoletsByConference", getAvgQuoletsByConference);
		result.addObject("getDesvQuoletsByConference", getDesvQuoletsByConference);

		result.addObject("ratioInDraftMode", ratioInDraftMode);
		result.addObject("ratioOutDraftMode", ratioOutDraftMode);

		result.addObject("getAvgSubmissionsByConference", getAvgSubmissionsByConference);
		result.addObject("getMinSubmissionsByConference", getMinSubmissionsByConference);
		result.addObject("getMaxSubmissionsByConference", getMaxSubmissionsByConference);
		result.addObject("getDesvSubmissionsByConference", getDesvSubmissionsByConference);

		result.addObject("getAvgRegistrationByConference", getAvgRegistrationByConference);
		result.addObject("getMinRegistrationByConference", getMinRegistrationByConference);
		result.addObject("getMaxRegistrationByConference", getMaxRegistrationByConference);
		result.addObject("getDesvRegistrationByConference", getDesvRegistrationByConference);

		result.addObject("getAvgFeesByConference", getAvgFeesByConference);
		result.addObject("getMinFeesByConference", getMinFeesByConference);
		result.addObject("getMaxFeesByConference", getMaxFeesByConference);
		result.addObject("getDesvFeesByConference", getDesvFeesByConference);

		result.addObject("getAvgDaysByConference", getAvgDaysByConference);
		result.addObject("getMinDaysByConference", getMinDaysByConference);
		result.addObject("getMaxDaysByConference", getMaxDaysByConference);
		result.addObject("getDesvDaysByConference", getDesvDaysByConference);

		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView result;
		RegistrationFormAdmin registrationForm = new RegistrationFormAdmin();

		registrationForm = registrationForm.createToAdmin();

		final String telephoneCode = this.customizableService.getTelephoneCode();
		registrationForm.setPhone(telephoneCode + " ");

		result = new ModelAndView("administrator/create");
		result.addObject("registrationForm", registrationForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("registrationForm") final RegistrationFormAdmin registrationForm, final BindingResult binding) {
		ModelAndView result;
		Administrator admin = null;

		try {
			admin = this.administratorService.reconstruct(registrationForm, binding);
			if (!binding.hasErrors() && registrationForm.getUserAccount().getPassword().equals(registrationForm.getPassword())) {
				this.administratorService.save(admin);
				result = new ModelAndView("redirect:/");
			} else {

				result = new ModelAndView("administrator/create");
				result.addObject("registrationForm", registrationForm);
			}
		} catch (final Exception e) {

			result = new ModelAndView("administrator/create");
			result.addObject("exception", e);
			result.addObject("registrationForm", registrationForm);

		}

		return result;
	}

}
