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

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.Form;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.htmlformentry.FormEntryContext.Mode;
import org.openmrs.module.htmlformentry.FormEntrySession;
import org.openmrs.module.htmlformentry.HtmlForm;
import org.openmrs.module.htmlformentry.HtmlFormEntryUtil;
import org.openmrs.util.OpenmrsUtil;
import org.openmrs.web.controller.PortletController;
import org.springframework.util.StringUtils;

public class HtmlFormEntryPortletController extends PortletController {

    protected final Log log = LogFactory.getLog(getClass());
    //    public final static String closeDialogView = "/module/htmlformentry/closeDialog";
    public final static String FORM_IN_PROGRESS_KEY = "HTML_FORM_IN_PROGRESS_KEY";
    public final static String FORM_IN_PROGRESS_VALUE = "HTML_FORM_IN_PROGRESS_VALUE";
    public final static String FORM_PATH = "/module/patientnarratives/htmlFormEntry";

    @Override
    protected void populateModel(HttpServletRequest request, Map<String, Object> model) {

        model.put("command", getFormEntrySession(request));
    }

    public FormEntrySession getFormEntrySession(HttpServletRequest request){

        long ts = System.currentTimeMillis();
        Mode mode = Mode.VIEW;

        String returnUrl = null;
        Long formModifiedTimestamp = null;
        Long encounterModifiedTimestamp = null;
        String hasChangedInd = null;

        String globalPropertyFormId = Context.getAdministrationService().getGlobalProperty("patientnarratives.formid");
        String globalPropertyPatientId = Context.getAdministrationService().getGlobalProperty("patientnarratives.patientid");

        Integer formId = Integer.valueOf(globalPropertyFormId);
        Integer patientId = Integer.valueOf(globalPropertyPatientId);

        Integer personId = null;
        Integer htmlFormId = null;

        String modeParam = request.getParameter("mode");
        if ("enter".equalsIgnoreCase(modeParam)) {
            mode = Mode.ENTER;
        }
        else if ("edit".equalsIgnoreCase(modeParam)) {
            mode = Mode.EDIT;
        }

        Patient patient = null;
        Encounter encounter = null;
        Form form = null;
        HtmlForm htmlForm = null;

        if (StringUtils.hasText(request.getParameter("encounterId"))) {

            Integer encounterId = Integer.valueOf(request.getParameter("encounterId"));
            encounter = Context.getEncounterService().getEncounter(encounterId);
            if (encounter == null)
                throw new IllegalArgumentException("No encounter with id=" + encounterId);
            patient = encounter.getPatient();
            patientId = patient.getPatientId();
            personId = patient.getPersonId();

            if (formId != null) { // I think formId is allowed to differ from encounter.form.id because of HtmlFormFlowsheet
                form = Context.getFormService().getForm(formId);
                htmlForm = HtmlFormEntryUtil.getService().getHtmlFormByForm(form);
                if (htmlForm == null)
                    throw new IllegalArgumentException("No HtmlForm associated with formId " + formId);
            } else {
                form = encounter.getForm();
                htmlForm = HtmlFormEntryUtil.getService().getHtmlFormByForm(encounter.getForm());
                if (htmlForm == null)
                    throw new IllegalArgumentException("The form for the specified encounter (" + encounter.getForm() + ") does not have an HtmlForm associated with it");
            }

        } else { // no encounter specified

            // get person from patientId/personId (register module uses patientId, htmlformentry uses personId)
            if (patientId != null) {
                personId = patientId;
            }
            if (personId != null) {
                patient = Context.getPatientService().getPatient(personId);
            }

            // determine form
            if (htmlFormId != null) {
                htmlForm = HtmlFormEntryUtil.getService().getHtmlForm(htmlFormId);
            } else if (formId != null) {
                form = Context.getFormService().getForm(formId);
                htmlForm = HtmlFormEntryUtil.getService().getHtmlFormByForm(form);
            }
            if (htmlForm == null) {
                throw new IllegalArgumentException("You must specify either an htmlFormId or a formId for a valid html form");
            }

            String which = request.getParameter("which");
            if (StringUtils.hasText(which)) {
                if (patient == null)
                    throw new IllegalArgumentException("Cannot specify 'which' without specifying a person/patient");
                List<Encounter> encs = Context.getEncounterService().getEncounters(patient, null, null, null, Collections.singleton(form), null, null, false);
                if (which.equals("first")) {
                    encounter = encs.get(0);
                } else if (which.equals("last")) {
                    encounter = encs.get(encs.size() - 1);
                } else {
                    throw new IllegalArgumentException("which must be 'first' or 'last'");
                }
            }
        }

        try{
            if (mode != Mode.ENTER && patient == null)
                throw new IllegalArgumentException("No patient with id of personId=" + personId + " or patientId=" + patientId);

            FormEntrySession session = null;
            if (mode == Mode.ENTER && patient == null) {
                patient = new Patient();
            }
            if (encounter != null) {
                session = new FormEntrySession(patient, encounter, mode, htmlForm, request.getSession());
            }
            else {
                session = new FormEntrySession(patient, htmlForm, request.getSession());
            }

            Context.setVolatileUserData(FORM_IN_PROGRESS_KEY, session);

            log.info("Took " + (System.currentTimeMillis() - ts) + " ms");

            if (StringUtils.hasText(returnUrl)) {
                session.setReturnUrl(returnUrl);
            }

            // Since we're not using a sessionForm, we need to check for the case where the underlying form was modified while a user was filling a form out
            if (formModifiedTimestamp != null) {
                if (!OpenmrsUtil.nullSafeEquals(formModifiedTimestamp, session.getFormModifiedTimestamp())) {
                    throw new RuntimeException(Context.getMessageSourceService().getMessage("htmlformentry.error.formModifiedBeforeSubmission"));
                }
            }

            // Since we're not using a sessionForm, we need to make sure this encounter hasn't been modified since the user opened it
            if (encounter != null) {
                if (encounterModifiedTimestamp != null && !OpenmrsUtil.nullSafeEquals(encounterModifiedTimestamp, session.getEncounterModifiedTimestamp())) {
                    throw new RuntimeException(Context.getMessageSourceService().getMessage("htmlformentry.error.encounterModifiedBeforeSubmission"));
                }
            }

            if (hasChangedInd != null) session.setHasChangedInd(hasChangedInd);

            return session;
        }catch(Exception e){

        }
        return null;
    }

}
