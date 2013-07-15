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

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.openmrs.Form;
import org.openmrs.Person;
import org.openmrs.module.Extension;
import org.openmrs.module.ModuleFactory;
import org.openmrs.module.web.FormEntryContext;
import org.openmrs.module.web.extension.FormEntryHandler;
import org.openmrs.module.xforms.XformConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.openmrs.api.FormService;
import org.openmrs.api.context.Context;

import org.openmrs.util.OpenmrsUtil;
import org.openmrs.web.controller.PortletController;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

public class XFormEntryPortletController extends PortletController{
    /** Logger for this class and subclasses */
    protected final Log log = LogFactory.getLog(getClass());

//    @Override
//    protected Map referenceData(HttpServletRequest request, Object obj, Errors err) throws Exception {
//        HashMap<String,Object> map = new HashMap<String,Object>();
//
//        Integer formId = 1; //Integer.parseInt(request.getParameter("formId"));
//        map.put("formId", formId);
//        map.put("patientId", 2); // Integer.parseInt(request.getParameter("patientId")));
//        map.put("formName", ((FormService)Context.getService(FormService.class)).getForm(formId).getName());
//        map.put("entityFormDefDownloadUrlSuffix", "moduleServlet/xforms/xformDownload?target=xformentry&contentType=xml&");
//        map.put("formDataUploadUrlSuffix", "module/xforms/xformDataUpload.form");
//        map.put("afterSubmitUrlSuffix", "patientDashboard.form?");
//        map.put("afterCancelUrlSuffix", "patientDashboard.form?");
//
//        map.put(XformConstants.FORM_DESIGNER_KEY_DATE_SUBMIT_FORMAT, Context.getAdministrationService().getGlobalProperty(XformConstants.GLOBAL_PROP_KEY_DATE_SUBMIT_FORMAT, XformConstants.DEFAULT_DATE_SUBMIT_FORMAT));
//        map.put(XformConstants.FORM_DESIGNER_KEY_DATE_DISPLAY_FORMAT, Context.getAdministrationService().getGlobalProperty(XformConstants.GLOBAL_PROP_KEY_DATE_DISPLAY_FORMAT, XformConstants.DEFAULT_DATE_DISPLAY_FORMAT));
//        map.put(XformConstants.FORM_DESIGNER_KEY_DEFAULT_FONT_FAMILY, Context.getAdministrationService().getGlobalProperty(XformConstants.GLOBAL_PROP_KEY_DEFAULT_FONT_FAMILY, XformConstants.DEFAULT_FONT_FAMILY));
//        map.put(XformConstants.FORM_DESIGNER_KEY_DEFAULT_FONT_SIZE, Context.getAdministrationService().getGlobalProperty(XformConstants.GLOBAL_PROP_KEY_DEFAULT_FONT_SIZE, XformConstants.DEFAULT_FONT_SIZE));
//
//        String color = "#8FABC7";
//        String theme = Context.getAdministrationService().getGlobalProperty("default_theme", "legacy");
//        if("orange".equals(theme))
//            color = "#f48a52";
//        else if("purple".equals(theme))
//            color = "#8c87c5";
//        else if("green".equals(theme))
//            color = "#1aac9b";
//
//        map.put(XformConstants.FORM_DESIGNER_KEY_DEFAULT_GROUPBOX_HEADER_BG_COLOR, color);
//
//        map.put(XformConstants.FORM_DESIGNER_KEY_DATE_TIME_SUBMIT_FORMAT, Context.getAdministrationService().getGlobalProperty(XformConstants.GLOBAL_PROP_KEY_DATE_TIME_SUBMIT_FORMAT, XformConstants.DEFAULT_DATE_TIME_SUBMIT_FORMAT));
//        map.put(XformConstants.FORM_DESIGNER_KEY_DATE_TIME_DISPLAY_FORMAT, Context.getAdministrationService().getGlobalProperty(XformConstants.GLOBAL_PROP_KEY_DATE_TIME_DISPLAY_FORMAT, XformConstants.DEFAULT_DATE_TIME_DISPLAY_FORMAT));
//        map.put(XformConstants.FORM_DESIGNER_KEY_TIME_SUBMIT_FORMAT, Context.getAdministrationService().getGlobalProperty(XformConstants.GLOBAL_PROP_KEY_TIME_SUBMIT_FORMAT, XformConstants.DEFAULT_TIME_SUBMIT_FORMAT));
//        map.put(XformConstants.FORM_DESIGNER_KEY_TIME_DISPLAY_FORMAT, Context.getAdministrationService().getGlobalProperty(XformConstants.GLOBAL_PROP_KEY_TIME_DISPLAY_FORMAT, XformConstants.DEFAULT_TIME_DISPLAY_FORMAT));
//
//        map.put(XformConstants.FORM_DESIGNER_KEY_SHOW_SUBMIT_SUCCESS_MSG, Context.getAdministrationService().getGlobalProperty(XformConstants.GLOBAL_PROP_KEY_SHOW_SUBMIT_SUCCESS_MSG, XformConstants.DEFAULT_SHOW_SUBMIT_SUCCESS_MSG));
//
//        map.put(XformConstants.FORM_DESIGNER_KEY_LOCALE_KEY, Context.getAdministrationService().getGlobalProperty(XformConstants.GLOBAL_PROP_KEY_LOCALE, Context.getLocale().getLanguage()));
//        map.put(XformConstants.FORM_DESIGNER_KEY_DECIMAL_SEPARATORS, Context.getAdministrationService().getGlobalProperty(XformConstants.GLOBAL_PROP_KEY_DECIMAL_SEPARATORS, XformConstants.DEFAULT_DECIMAL_SEPARATORS));
////        map.put("usingJQuery", XformsUtil.usesJquery());
//        map.put("locations", Context.getLocationService().getAllLocations(false));
//        map.put("formatXml", "false");
////        map.put("useOpenmrsMessageTag", XformsUtil.isOnePointNineOneAndAbove());
//
//        return map;
//    }
//

    //Can't see current usage for this.
//    @Override
//    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object object, BindException exceptions) throws Exception {
//
//        // Captcha: https://developers.google.com/recaptcha/docs/java
//
//        String remoteAddr = request.getRemoteAddr();
//        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
//        reCaptcha.setPrivateKey("6LdAWuMSAAAAALxWgnM5yRj_tGVRQCk4lit8rLHb");
//
//        String challenge = request.getParameter("recaptcha_challenge_field");
//        String uresponse = request.getParameter("recaptcha_response_field");
//        ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);
//
//        if (reCaptchaResponse.isValid()) {
////            out.print("Answer was entered correctly!");
//        } else {
////            out.print("Answer is wrong");
//        }
//
//        return new ModelAndView(new RedirectView(getSuccessView()));
//
//    }

//    @Override
//    protected Object formBackingObject(HttpServletRequest request) throws Exception {
//
//        return "Not Yet";
//    }

    @Override
    protected void populateModel(HttpServletRequest request, Map<String, Object> model) {

        String globalPropertyFormId = Context.getAdministrationService().getGlobalProperty("patientnarratives.formid");
        String globalPropertyPatientId = Context.getAdministrationService().getGlobalProperty("patientnarratives.patientid");

        Integer formId = Integer.valueOf(globalPropertyFormId);
        Integer patientId = Integer.valueOf(globalPropertyPatientId);

        model.put("formId", formId);
        model.put("patientId", patientId);
        model.put("formName", ((FormService) Context.getService(FormService.class)).getForm(formId).getName());
        model.put("entityFormDefDownloadUrlSuffix", "moduleServlet/xforms/xformDownload?target=xformentry&contentType=xml&");
        model.put("formDataUploadUrlSuffix", "module/xforms/xformDataUpload.form");
        model.put("afterSubmitUrlSuffix", "patientDashboard.form?");
        model.put("afterCancelUrlSuffix", "patientDashboard.form?");

        model.put(XformConstants.FORM_DESIGNER_KEY_DATE_SUBMIT_FORMAT, Context.getAdministrationService().getGlobalProperty(XformConstants.GLOBAL_PROP_KEY_DATE_SUBMIT_FORMAT, XformConstants.DEFAULT_DATE_SUBMIT_FORMAT));
        model.put(XformConstants.FORM_DESIGNER_KEY_DATE_DISPLAY_FORMAT, Context.getAdministrationService().getGlobalProperty(XformConstants.GLOBAL_PROP_KEY_DATE_DISPLAY_FORMAT, XformConstants.DEFAULT_DATE_DISPLAY_FORMAT));
        model.put(XformConstants.FORM_DESIGNER_KEY_DEFAULT_FONT_FAMILY, Context.getAdministrationService().getGlobalProperty(XformConstants.GLOBAL_PROP_KEY_DEFAULT_FONT_FAMILY, XformConstants.DEFAULT_FONT_FAMILY));
        model.put(XformConstants.FORM_DESIGNER_KEY_DEFAULT_FONT_SIZE, Context.getAdministrationService().getGlobalProperty(XformConstants.GLOBAL_PROP_KEY_DEFAULT_FONT_SIZE, XformConstants.DEFAULT_FONT_SIZE));

        String color = "#8FABC7";
        String theme = Context.getAdministrationService().getGlobalProperty("default_theme", "legacy");
        if("orange".equals(theme))
            color = "#f48a52";
        else if("purple".equals(theme))
            color = "#8c87c5";
        else if("green".equals(theme))
            color = "#1aac9b";

        model.put(XformConstants.FORM_DESIGNER_KEY_DEFAULT_GROUPBOX_HEADER_BG_COLOR, color);

        model.put(XformConstants.FORM_DESIGNER_KEY_DATE_TIME_SUBMIT_FORMAT, Context.getAdministrationService().getGlobalProperty(XformConstants.GLOBAL_PROP_KEY_DATE_TIME_SUBMIT_FORMAT, XformConstants.DEFAULT_DATE_TIME_SUBMIT_FORMAT));
        model.put(XformConstants.FORM_DESIGNER_KEY_DATE_TIME_DISPLAY_FORMAT, Context.getAdministrationService().getGlobalProperty(XformConstants.GLOBAL_PROP_KEY_DATE_TIME_DISPLAY_FORMAT, XformConstants.DEFAULT_DATE_TIME_DISPLAY_FORMAT));
        model.put(XformConstants.FORM_DESIGNER_KEY_TIME_SUBMIT_FORMAT, Context.getAdministrationService().getGlobalProperty(XformConstants.GLOBAL_PROP_KEY_TIME_SUBMIT_FORMAT, XformConstants.DEFAULT_TIME_SUBMIT_FORMAT));
        model.put(XformConstants.FORM_DESIGNER_KEY_TIME_DISPLAY_FORMAT, Context.getAdministrationService().getGlobalProperty(XformConstants.GLOBAL_PROP_KEY_TIME_DISPLAY_FORMAT, XformConstants.DEFAULT_TIME_DISPLAY_FORMAT));

        model.put(XformConstants.FORM_DESIGNER_KEY_SHOW_SUBMIT_SUCCESS_MSG, Context.getAdministrationService().getGlobalProperty(XformConstants.GLOBAL_PROP_KEY_SHOW_SUBMIT_SUCCESS_MSG, XformConstants.DEFAULT_SHOW_SUBMIT_SUCCESS_MSG));

        model.put(XformConstants.FORM_DESIGNER_KEY_LOCALE_KEY, Context.getAdministrationService().getGlobalProperty(XformConstants.GLOBAL_PROP_KEY_LOCALE, Context.getLocale().getLanguage()));
        model.put(XformConstants.FORM_DESIGNER_KEY_DECIMAL_SEPARATORS, Context.getAdministrationService().getGlobalProperty(XformConstants.GLOBAL_PROP_KEY_DECIMAL_SEPARATORS, XformConstants.DEFAULT_DECIMAL_SEPARATORS));
//        map.put("usingJQuery", XformsUtil.usesJquery());
        model.put("locations", Context.getLocationService().getAllLocations(false));
        model.put("formatXml", "false");

//        map.put("useOpenmrsMessageTag", XformsUtil.isOnePointNineOneAndAbove());

    }
}

