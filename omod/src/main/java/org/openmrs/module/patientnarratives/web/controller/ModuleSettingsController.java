package org.openmrs.module.patientnarratives.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.GlobalProperty;
import org.openmrs.api.context.Context;
import org.openmrs.util.OpenmrsConstants;
import org.openmrs.web.WebConstants;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.SimpleFormController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * Author: harshadura
 * Date: 7/15/13
 * Time: 9:48 AM
 */
public class ModuleSettingsController extends SimpleFormController {
    protected final Log log = LogFactory.getLog(getClass());

    @Override
    protected String formBackingObject(HttpServletRequest request) throws Exception {

        Boolean alert = null;
        String formType = request.getParameter("formType");
        String formID = request.getParameter("formID");
        String patientID = request.getParameter("patientID");
        String encType = request.getParameter("encType");

//            Context.addProxyPrivilege(OpenmrsConstants.PRIV_MANAGE_IMPLEMENTATION_ID);
//            Context.getAdministrationService().setImplementationId(implId);
//            Context.removeProxyPrivilege(OpenmrsConstants.PRIV_MANAGE_IMPLEMENTATION_ID);
//            (OpenmrsConstants.PRIV_MANAGE_GLOBAL_PROPERTIES);

        if (StringUtils.hasLength(formType)) {
            GlobalProperty globalProperty = new GlobalProperty();
            globalProperty.setProperty("patientnarratives.formtype");
            globalProperty.setPropertyValue(formType);
            Context.getAdministrationService().setGlobalProperty(globalProperty);
            alert = true;
        }

        if (StringUtils.hasLength(formID)) {
            GlobalProperty globalProperty = new GlobalProperty();

            globalProperty.setProperty("patientnarratives.formid");
            globalProperty.setPropertyValue(formID);
            Context.getAdministrationService().setGlobalProperty(globalProperty);
            alert = true;
        }

        if (StringUtils.hasLength(patientID)) {
            GlobalProperty globalProperty = new GlobalProperty();

            globalProperty.setProperty("patientnarratives.patientid");
            globalProperty.setPropertyValue(patientID);
            Context.getAdministrationService().setGlobalProperty(globalProperty);
            alert = true;
        }

        if (StringUtils.hasLength(encType)) {
            GlobalProperty globalProperty = new GlobalProperty();

            globalProperty.setProperty("patientnarratives.enctype");
            globalProperty.setPropertyValue(encType);
            Context.getAdministrationService().setGlobalProperty(globalProperty);
            alert = true;
        }

        if ((alert != null) && (alert == true)) {
            request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "patientnarratives.module.settings.alert");
        }

        return "Not Yet";
    }

    @Override
    protected Map referenceData(HttpServletRequest req) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        String formType = Context.getAdministrationService().getGlobalProperty("patientnarratives.formtype");
        map.put("formType", formType);

        return map;
    }
}
