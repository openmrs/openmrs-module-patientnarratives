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
    import org.openmrs.module.patientnarratives.api.NarrativeComments;
    import org.openmrs.module.patientnarratives.api.PatientNarrativesService;
    import org.springframework.util.StringUtils;
    import org.springframework.validation.BindException;
    import org.springframework.validation.Errors;
    import org.springframework.web.servlet.ModelAndView;
    import org.springframework.web.servlet.mvc.SimpleFormController;
    import org.springframework.web.servlet.view.RedirectView;

    public class FullNarrativeFormController extends SimpleFormController {

        protected final Log log = LogFactory.getLog(getClass());

        @Override
        protected Map referenceData(HttpServletRequest request, Object obj, Errors err) throws Exception {
            HashMap<String,Object> map = new HashMap<String,Object>();

            PatientNarrativesService patientNarrativesService = Context.getService(PatientNarrativesService.class);
            map.put("comments", patientNarrativesService.getNarrativeComments(Integer.parseInt(request.getParameter("encId"))));

            int encId  = Integer.parseInt(request.getParameter("encId"));
            Encounter encounter = Context.getEncounterService().getEncounter(encId);

            map.put("encDate", encounter.getEncounterDatetime());
            map.put("encId", encId);

            Set<Obs> obs = encounter.getObs();
            Iterator<Obs> observation = obs.iterator();

            while(observation.hasNext()) {
                Obs nowOb = observation.next();

                switch (nowOb.getConcept().getConceptId() ) {
                    case 9: map.put("story", nowOb.getValueText()); continue;
                    case 11: map.put("tp", nowOb.getValueText()); continue;
                    case 13: map.put("sex", nowOb.getValueText()); continue;
                    case 7: map.put("city", nowOb.getValueText()); continue;
                    case 12: map.put("name", nowOb.getValueText()); continue;
                    case 10: map.put("email", nowOb.getValueText()); continue;
                    case 8: map.put("age", nowOb.getValueText()); continue;
                    case 15: map.put("status", nowOb.getValueText()); continue;
                    case 16: map.put("subject", nowOb.getValueText()); continue;
                    default: continue;
                }
            }

            return map;
        }

        @Override
        protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object object, BindException exceptions) throws Exception {

            if (StringUtils.hasLength(request.getParameter("comment"))) {
                PatientNarrativesService patientNarrativesService = Context.getService(PatientNarrativesService.class);

                NarrativeComments narrativeComments = new NarrativeComments();
                narrativeComments.setComment(request.getParameter("comment"));

                String encId = request.getParameter("encId");
                narrativeComments.setEncounterId(Integer.parseInt(encId));
                patientNarrativesService.saveNarrativesComment(narrativeComments);
            }


            return new ModelAndView(new RedirectView(getSuccessView()));

        }

        @Override
        protected Object formBackingObject(HttpServletRequest request) throws Exception {

            return "Not Yet";
        }
    }
