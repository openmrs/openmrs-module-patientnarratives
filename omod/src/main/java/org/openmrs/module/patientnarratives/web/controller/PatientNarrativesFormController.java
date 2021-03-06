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
import org.openmrs.api.context.Context;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.mvc.SimpleFormController;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 *  Main Controller of the Patient Narratives form
 */
public class PatientNarrativesFormController extends SimpleFormController{

    protected final Log log = LogFactory.getLog(getClass());

    @Override
    protected Map referenceData(HttpServletRequest request, Object obj, Errors err) throws Exception {
        HashMap<String,Object> map = new HashMap<String,Object>();

        /* If the user isn't authenticated use anonymous login */
        if (Context.isAuthenticated() == false){
            Context.authenticate("anonymous", "Password123");
        }

        String formType = Context.getAdministrationService().getGlobalProperty("patientnarratives.formtype");
        map.put("formType", formType);

        return map;
    }

//    @Override
//    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object object, BindException errors) throws Exception {
//
//        return new ModelAndView(FORM_PATH, "command", null);
//    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {

        return "Not Yet";
    }
}