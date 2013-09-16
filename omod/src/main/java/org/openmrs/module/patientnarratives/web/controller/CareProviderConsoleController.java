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

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.*;
import org.openmrs.api.context.Context;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

/**
 *  @CareProviderConsoleController
 */
public class CareProviderConsoleController extends SimpleFormController {

    protected final Log log = LogFactory.getLog(getClass());

    @Override
    protected Map referenceData(HttpServletRequest request, Object obj, Errors err) throws Exception {

        HashMap<String,Object> map = new HashMap<String,Object>();
        Context.addProxyPrivilege("View Encounters");

        /*
         * Retrieving encounters filtered by "patientnarratives.enctype" global property
         * where the system lets the user to enter at Patient Narratives - Module settings
         */
        String globalPropertyEnctype = Context.getAdministrationService().getGlobalProperty("patientnarratives.enctype");
        List<EncounterType> var = new ArrayList<EncounterType>();
        var.add(Context.getEncounterService().getEncounterType(globalPropertyEnctype));
        List<Encounter> encounters = Context.getEncounterService().getEncounters(null, null, null, null, null, var, true);

        Context.removeProxyPrivilege("View Encounters");

        map.put("encounters", encounters);

        return map;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object object, BindException exceptions) throws Exception {

        return new ModelAndView(new RedirectView(getSuccessView()));

    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {

        return "Not Yet";
    }
}
