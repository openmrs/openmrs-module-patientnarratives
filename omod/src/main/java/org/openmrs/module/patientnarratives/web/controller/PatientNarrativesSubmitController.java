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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.Form;
import org.openmrs.api.FormService;
import org.openmrs.api.context.Context;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Provides browser based XForm data entry services.
 *
 * @author Daniel
 *
 */
public class PatientNarrativesSubmitController extends SimpleFormController{

    /** Logger for this class and subclasses */
    protected final Log log = LogFactory.getLog(getClass());

    @Override
    protected Map referenceData(HttpServletRequest request, Object obj, Errors err) throws Exception {
        HashMap<String,Object> map = new HashMap<String,Object>();

            Integer formId = 1; //Integer.parseInt(request.getParameter("formId"));
            map.put("formId", formId);
            map.put("patientId", 2); // Integer.parseInt(request.getParameter("patientId")));
            map.put("formName", ((FormService)Context.getService(FormService.class)).getForm(formId).getName());
            map.put("entityFormDefDownloadUrlSuffix", "moduleServlet/xforms/xformDownload?target=xformentry&contentType=xml&");
            map.put("formDataUploadUrlSuffix", "module/xforms/xformDataUpload.form");
            map.put("afterSubmitUrlSuffix", "patientDashboard.form?");

        return map;
    }


    //Can't see current usage for this.
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object object, BindException exceptions) throws Exception {

        // Captcha: https://developers.google.com/recaptcha/docs/java

        String remoteAddr = request.getRemoteAddr();
        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
        reCaptcha.setPrivateKey("6LdAWuMSAAAAALxWgnM5yRj_tGVRQCk4lit8rLHb");

        String challenge = request.getParameter("recaptcha_challenge_field");
        String uresponse = request.getParameter("recaptcha_response_field");
        ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);

        if (reCaptchaResponse.isValid()) {
//            out.print("Answer was entered correctly!");
        } else {
//            out.print("Answer is wrong");
        }

        return new ModelAndView(new RedirectView(getSuccessView()));

    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {

        return "Not Yet";
    }
}