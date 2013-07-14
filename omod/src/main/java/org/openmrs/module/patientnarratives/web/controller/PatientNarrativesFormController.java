/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.patientnarratives.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.FormService;
import org.openmrs.api.context.Context;
import org.openmrs.module.htmlformentry.*;
import org.openmrs.module.xforms.XformConstants;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientNarrativesFormController extends SimpleFormController{

    /** Logger for this class and subclasses */
    protected final Log log = LogFactory.getLog(getClass());
    public final static String FORM_IN_PROGRESS_KEY = "HTML_FORM_IN_PROGRESS_KEY";
    public final static String FORM_IN_PROGRESS_VALUE = "HTML_FORM_IN_PROGRESS_VALUE";
    public final static String FORM_PATH = "/module/patientnarratives/htmlFormEntry";

    @Override
    protected Map referenceData(HttpServletRequest request, Object obj, Errors err) throws Exception {
        HashMap<String,Object> map = new HashMap<String,Object>();

        return map;
    }


    //Can't see current usage for this.
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object object, BindException errors) throws Exception {

        HtmlFormEntryPortletController htmlFormEntryPortletController = new HtmlFormEntryPortletController();
        FormEntrySession session = htmlFormEntryPortletController.getFormEntrySession(request);
//        Errors errors;

        try {
            List<FormSubmissionError> validationErrors = session.getSubmissionController().validateSubmission(session.getContext(), request);
            if (validationErrors != null && validationErrors.size() > 0) {
                errors.reject("Fix errors");
            }
        } catch (Exception ex) {
            log.error("Exception during form validation", ex);
            errors.reject("Exception during form validation, see log for more details: " + ex);
        }

        if (errors.hasErrors()) {
            return new ModelAndView(FORM_PATH, "command", session);
        }

        // no form validation errors, proceed with submission

        session.prepareForSubmit();

        if (session.getContext().getMode() == FormEntryContext.Mode.ENTER && session.hasPatientTag() && session.getPatient() == null
                && (session.getSubmissionActions().getPersonsToCreate() == null || session.getSubmissionActions().getPersonsToCreate().size() == 0))
            throw new IllegalArgumentException("This form is not going to create an Patient");

        if (session.getContext().getMode() == FormEntryContext.Mode.ENTER && session.hasEncouterTag() && (session.getSubmissionActions().getEncountersToCreate() == null || session.getSubmissionActions().getEncountersToCreate().size() == 0))
            throw new IllegalArgumentException("This form is not going to create an encounter");

        try {
            session.getSubmissionController().handleFormSubmission(session, request);
            HtmlFormEntryUtil.getService().applyActions(session);
            String successView = session.getReturnUrlWithParameters();
            if (successView == null)
                successView = request.getContextPath() + "/patientDashboard.form" + getQueryPrameters(request, session);
            if (StringUtils.hasText(request.getParameter("closeAfterSubmission"))) {
//                return new ModelAndView(closeDialogView, "dialogToClose", request.getParameter("closeAfterSubmission"));
            } else {
                return new ModelAndView(new RedirectView(successView));
            }
        } catch (ValidationException ex) {
            log.error("Invalid input:", ex);
            errors.reject(ex.getMessage());
        } catch (BadFormDesignException ex) {
            log.error("Bad Form Design:", ex);
            errors.reject(ex.getMessage());
        } catch (Exception ex) {
            log.error("Exception trying to submit form", ex);
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            errors.reject("Exception! " + ex.getMessage() + "<br/>" + sw.toString());
        }

        // if we get here it's because we caught an error trying to submit/apply
        return new ModelAndView(FORM_PATH, "command", session);
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {

        return "Not Yet";
    }

    protected String getQueryPrameters(HttpServletRequest request, FormEntrySession formEntrySession) {
        return "?patientId=" + formEntrySession.getPatient().getPersonId();
    }
}