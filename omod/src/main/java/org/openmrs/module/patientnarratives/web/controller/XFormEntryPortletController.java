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
import org.openmrs.module.xforms.XformConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.FormService;
import org.openmrs.api.context.Context;
import org.openmrs.web.controller.PortletController;

/**
 * Custom Controller of the forms which are need to handle combined with Xforms module of the OpenMRS
 */
public class XFormEntryPortletController extends PortletController{
    protected final Log log = LogFactory.getLog(getClass());

    @Override
    protected void populateModel(HttpServletRequest request, Map<String, Object> model) {

        Context.addProxyPrivilege("View Users");
        Context.addProxyPrivilege("View Global Properties");
        Context.addProxyPrivilege("Purge HL7 Inbound Queue");

        String globalPropertyFormId = Context.getAdministrationService().getGlobalProperty("patientnarratives.formid");
        String globalPropertyPatientId = Context.getAdministrationService().getGlobalProperty("patientnarratives.patientid");

        Integer formId = Integer.valueOf(globalPropertyFormId);
        Integer patientId = Integer.valueOf(globalPropertyPatientId);

        model.put("formId", formId);
        model.put("patientId", patientId);
        model.put("formName", ((FormService) Context.getService(FormService.class)).getForm(formId).getName());
        model.put("entityFormDefDownloadUrlSuffix", "moduleServlet/xforms/xformDownload?target=xformentry&contentType=xml&");
        model.put("formDataUploadUrlSuffix", "module/xforms/xformDataUpload.form");
//        model.put("afterSubmitUrlSuffix", "module/patientnarratives/patientNarrativesForm.form?");
        model.put("afterCancelUrlSuffix", "module/patientnarratives/patientNarrativesForm.form?");

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

