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
import org.openmrs.*;
import org.openmrs.api.context.Context;
import org.openmrs.module.htmlformentry.*;
import org.openmrs.obs.ComplexData;
import org.openmrs.util.OpenmrsConstants;
import org.openmrs.web.WebConstants;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class PatientNarrativesFormController extends SimpleFormController{

    /** Logger for this class and subclasses */
    protected final Log log = LogFactory.getLog(getClass());
    public final static String FORM_IN_PROGRESS_KEY = "HTML_FORM_IN_PROGRESS_KEY";
    public final static String FORM_IN_PROGRESS_VALUE = "HTML_FORM_IN_PROGRESS_VALUE";
    public final static String FORM_PATH = "/module/patientnarratives/htmlFormEntry";

    private String mergedUrl = "/home/harshadura/gsoc2013/TestWebm/";

    @Override
    protected Map referenceData(HttpServletRequest request, Object obj, Errors err) throws Exception {
        HashMap<String,Object> map = new HashMap<String,Object>();

        if (Context.isAuthenticated() == false){
            Context.authenticate("anonymous", "Password123");
        }

        String formType = Context.getAdministrationService().getGlobalProperty("patientnarratives.formtype");
        map.put("formType", formType);

        return map;
    }


    //Can't see current usage for this.
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object object, BindException errors) throws Exception {

        String hello = request.getParameter("ss");

        List<Encounter> encounters = Context.getEncounterService().getEncounters(null, null, null, null, null, null, true);

        Encounter lastEncounter = encounters.get(encounters.size()-1);
        Integer lastEncId = lastEncounter.getId();

        Person patient = lastEncounter.getPatient();
        ConceptComplex conceptComplex = Context.getConceptService().getConceptComplex(14);
        Location location = Context.getLocationService().getDefaultLocation();
        Obs obs = new Obs(patient, conceptComplex, new Date(), location) ;

        InputStream out1 = new FileInputStream(new File(mergedUrl, "mergedFile1.flv"));
        ComplexData complexData = new ComplexData("mergedFile1.flv", out1);
        obs.setComplexData(complexData);
        obs.setEncounter(lastEncounter);

        Context.getObsService().saveObs(obs, null);

        return new ModelAndView(FORM_PATH, "command", null);
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {

        return "Not Yet";
    }
}