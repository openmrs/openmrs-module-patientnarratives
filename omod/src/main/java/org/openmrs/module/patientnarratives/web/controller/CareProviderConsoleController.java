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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.*;
import org.openmrs.api.EncounterService;
import org.openmrs.api.PatientService;
import org.openmrs.api.PersonService;
import org.openmrs.api.context.Context;
import org.openmrs.web.WebConstants;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

public class CareProviderConsoleController extends SimpleFormController {

    protected final Log log = LogFactory.getLog(getClass());

    @Override
    protected Map referenceData(HttpServletRequest request, Object obj, Errors err) throws Exception {
        HashMap<String,Object> map = new HashMap<String,Object>();


        Context.addProxyPrivilege("View Encounters");
//        Patient defaultNarrativePatient = Context.getPatientService().getPatient(2);

        String globalPropertyEnctype = Context.getAdministrationService().getGlobalProperty("patientnarratives.enctype");
//        String encounterType = "patient_narratives";

        List<EncounterType> var = new ArrayList<EncounterType>();
        var.add(Context.getEncounterService().getEncounterType(globalPropertyEnctype));

        List<Encounter> encounters = Context.getEncounterService().getEncounters(null, null, null, null, null, var, true);
        Context.removeProxyPrivilege("View Encounters");

        map.put("encounters", encounters);

//        Encounter enc = new Encounter();
//        enc.
        return map;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object object, BindException exceptions) throws Exception {

        Integer encId = Integer.parseInt(request.getParameter("textEncID"));

        PatientService patientService = Context.getPatientService();
        Patient patient = new Patient();

        EncounterService encounterService = Context.getEncounterService();
        Encounter encounter = encounterService.getEncounter(encId);

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

//        Set<PatientIdentifier> patientIdentifierSet = new TreeSet<PatientIdentifier>();
//        patientIdentifierSet.add(patientIdentifier);
//        patient.setIdentifiers(patientIdentifierSet);

//        patient.setPatientId(newPatientId);

//        PersonService personService = Context.getPersonService();
//        Person person = new Person();
//
//        person.setId(newPatientId);
//        person.setGender(patientGender);
//        person.setNames(personNameSet);
//
//        personService.savePerson(patient);

        patientService.savePatient(patient);

        int patientId = patientService.getPatients(String.valueOf(newPatientId)).get(0).getPatientId();
        encounter.setPatient(patientService.getPatient(patientId));

        encounterService.saveEncounter(encounter);

        request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "patientnarratives.newpatient.created.alert");

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
