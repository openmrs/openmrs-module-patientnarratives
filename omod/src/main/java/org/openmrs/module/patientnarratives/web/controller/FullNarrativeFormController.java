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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.*;
import org.openmrs.api.ConceptService;
import org.openmrs.api.EncounterService;
import org.openmrs.api.ObsService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientnarratives.NarrativeComments;
import org.openmrs.module.patientnarratives.api.PatientNarrativesService;
import org.openmrs.obs.ComplexData;
import org.openmrs.util.OpenmrsConstants;
import org.openmrs.web.WebConstants;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

public class FullNarrativeFormController extends SimpleFormController {

    protected final Log log = LogFactory.getLog(getClass());

    @Override
    protected Map referenceData(HttpServletRequest request, Object obj, Errors err) throws Exception {
        HashMap<String,Object> map = new HashMap<String,Object>();

        HashMap<Integer,String> uploadedFilesMap = new HashMap<Integer,String>();

        PatientNarrativesService patientNarrativesService = Context.getService(PatientNarrativesService.class);
        map.put("comments", patientNarrativesService.getNarrativeComments(Integer.parseInt(request.getParameter("encounterId"))));

        int encounterId  = Integer.parseInt(request.getParameter("encounterId"));
        Encounter encounter = Context.getEncounterService().getEncounter(encounterId);

        map.put("encDate", encounter.getEncounterDatetime());
        map.put("encounterId", encounterId);

        map.put("patientId", encounter.getPatient().getPatientId());

        String globalPropertyPatientId = Context.getAdministrationService().getGlobalProperty("patientnarratives.patientid");
        map.put("defaultPatientId", globalPropertyPatientId);

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
                case 14: map.put("videoObsId", nowOb.getObsId()); continue;
                case 17: uploadedFilesMap.put(nowOb.getObsId(), getFilename(nowOb.getObsId())); continue;
                default: continue;
            }
        }
        map.put("uploadedFilesMap", uploadedFilesMap);

        return map;
    }

    public File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") +
                multipart.getOriginalFilename());
        multipart.transferTo(tmpFile);
        return tmpFile;
    }


    public String getFilename( Integer obsId ){
        Obs complexObs = Context.getObsService().getComplexObs(obsId, OpenmrsConstants.RAW_VIEW);
        ComplexData complexData = complexObs.getComplexData();
        String fileExt = complexData.getTitle();
        String arr[] = fileExt.split(" ");
        return arr[0];
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object object, BindException exceptions) throws Exception {

        try{
            if (StringUtils.hasLength(request.getParameter("comment"))) {
                PatientNarrativesService patientNarrativesService = Context.getService(PatientNarrativesService.class);

                NarrativeComments narrativeComments = new NarrativeComments();
                narrativeComments.setComment(request.getParameter("comment"));

                String encounterId = request.getParameter("encounterId");
                narrativeComments.setEncounterId(Integer.parseInt(encounterId));
                patientNarrativesService.saveNarrativesComment(narrativeComments);

                request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "patientnarratives.comment.added");
            }

            if (StringUtils.hasLength(request.getParameter("newStatus"))) {

                String newStatus = request.getParameter("newStatus");
                int encounterId  = Integer.parseInt(request.getParameter("encounterId"));
                Encounter encounter = Context.getEncounterService().getEncounter(encounterId);

                Set<Obs> obs = encounter.getObs();
                Iterator<Obs> observation = obs.iterator();

                Integer statusObsId = null;
                Obs nowOb = null;

                while(observation.hasNext()) {
                    nowOb = observation.next();
                    if (nowOb.getConcept().getConceptId() == 15 && !nowOb.isVoided()) {
                        statusObsId = nowOb.getId();
                        Obs statusObs = Context.getObsService().getObs(statusObsId);
                        Context.getObsService().voidObs(statusObs, "obs voided");
                        break;
                    }
                }

                Person patient = encounter.getPatient();
                Location location = Context.getLocationService().getDefaultLocation();

                Obs newObs = new Obs(patient, nowOb.getConcept(), new Date(), location) ;
                newObs.setValueText(newStatus);
                newObs.setEncounter(encounter);

                Context.getObsService().saveObs(newObs, null);

                request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "patientnarratives.status.updated");
            }

            if (StringUtils.hasLength(request.getParameter("registerEncounterId"))){
                Integer encounterId = Integer.parseInt(request.getParameter("registerEncounterId"));

                PatientService patientService = Context.getPatientService();
                Patient patient = new Patient();

                EncounterService encounterService = Context.getEncounterService();
                Encounter encounter = encounterService.getEncounter(encounterId);

                String patientName = "";
                String patientAge = "";
                String patientGender = "";

                Set<Obs> obs = encounter.getObs();
                Iterator<Obs> observation = obs.iterator();

                while(observation.hasNext()) {
                    Obs nowOb = observation.next();

                    if (nowOb.getConcept().getConceptId() == 12) {
                        patientName = nowOb.getValueText();
                    }
                    if (nowOb.getConcept().getConceptId() == 8) {
                        patientAge = nowOb.getValueText();
                    }
                    if (nowOb.getConcept().getConceptId() == 13) {
                        patientGender = nowOb.getValueText();
                    }
                }

                PersonName personName = new PersonName();
                int patientCount = patientService.getAllPatients().size();
                int newPatientId = patientCount + 5;

                String nameArr[] = patientName.split(" ");

                if (nameArr.length == 1){
                    personName.setGivenName(nameArr[0]);
                    personName.setFamilyName("----");
                }
                else if (nameArr.length == 2){
                    personName.setGivenName(nameArr[0]);
                    personName.setFamilyName(nameArr[1]);
                }
                else if (nameArr.length >= 3){
                    String nameArr2[] = parseFullName(patientName);

                    personName.setGivenName(nameArr2[0]);
                    personName.setMiddleName(nameArr2[1]);
                    personName.setFamilyName(nameArr2[2]);
                }

                Set<PersonName> personNameSet = new TreeSet<PersonName>();
                personNameSet.add(personName);

                patient.setNames(personNameSet);
                patient.setGender(patientGender);

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                patient.setBirthdateFromAge(Integer.parseInt(patientAge), df.parse(date));

                PatientIdentifierType patientIdentifierType = Context.getPatientService().getPatientIdentifierType(2);
                Location location = Context.getLocationService().getDefaultLocation();

                PatientIdentifier patientIdentifier = new PatientIdentifier(String.valueOf(newPatientId), patientIdentifierType, location);
                patient.addIdentifier(patientIdentifier);
                patientService.savePatient(patient);

                int patientId = patientService.getPatients(String.valueOf(newPatientId)).get(0).getPatientId();
                encounter.setPatient(patientService.getPatient(patientId));
                encounterService.saveEncounter(encounter);
                request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "patientnarratives.newpatient.created.alert");

            }
        }catch (Exception e){
            e.getMessage();
        }

        return new ModelAndView(new RedirectView(getSuccessView()));

    }

    public String[] parseFullName(String name) {
        String nameArr[] = new String[3];

        int start = name.indexOf(' ');
        int end = name.lastIndexOf(' ');

        String firstName = "";
        String middleName = "";
        String lastName = "";

        if (start >= 0) {
            firstName = name.substring(0, start);
            if (end > start)
                middleName = name.substring(start + 1, end);
            lastName = name.substring(end + 1, name.length());
        }

        nameArr[0] = firstName;
        nameArr[1] = middleName;
        nameArr[2] = lastName;

        return nameArr;
    }


    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {

        return "Not Yet";
    }
}
