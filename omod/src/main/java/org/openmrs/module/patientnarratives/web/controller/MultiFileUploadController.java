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
import org.openmrs.obs.ComplexData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *  This Class defines the controller which handles the user files upload on the patient narratives form.
 *  see patientNarrativesForm.jsp
 */
@Controller
public class MultiFileUploadController {

    private String returnUrl;
    public final static String FORM_PATH = "/module/patientnarratives/multiFileUpload.form";
    protected final Log log = LogFactory.getLog(getClass());
    private File tempFile = null;

    @RequestMapping(FORM_PATH)
    public ModelAndView handleRequest(HttpServletRequest request) throws Exception {

        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            List<MultipartFile> uploadedFiles = multipartRequest.getFiles("files[]");

            try{
                Iterator<MultipartFile> iterator = uploadedFiles.iterator();
                while (iterator.hasNext()) {
                    MultipartFile multipartFile = iterator.next();
                    tempFile =  multipartToFile(multipartFile);
                    saveAndTransferFileComplexObs();
                }
            }
            catch (Exception e){
                log.error(e);
                e.getStackTrace();
            }
        }

        returnUrl = request.getContextPath() + "/module/patientnarratives/patientNarrativesForm.form";
        return new ModelAndView(new RedirectView(returnUrl));
    }

    public File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") +
                multipart.getOriginalFilename());
        multipart.transferTo(tmpFile);
        return tmpFile;
    }

    public void saveAndTransferFileComplexObs(){

        try{
            List<Encounter> encounters = Context.getEncounterService().getEncounters(null, null, null, null, null, null, true);
            Encounter lastEncounter = encounters.get(encounters.size()-1);

            Person patient = lastEncounter.getPatient();
            ConceptComplex conceptComplex = Context.getConceptService().getConceptComplex(17);
            Location location = Context.getLocationService().getDefaultLocation();
            Obs obs = new Obs(patient, conceptComplex, new Date(), location) ;

            String mergedUrl = tempFile.getCanonicalPath();
            InputStream out1 = new FileInputStream(new File(mergedUrl));

            ComplexData complexData = new ComplexData(tempFile.getName(), out1);
            obs.setComplexData(complexData);
            obs.setEncounter(lastEncounter);

            Context.getObsService().saveObs(obs, null);
            tempFile.delete();

        }catch (Exception e){
            log.error(e);
        }
    }
}
