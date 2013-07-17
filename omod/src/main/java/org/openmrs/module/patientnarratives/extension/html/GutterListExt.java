package org.openmrs.module.patientnarratives.extension.html;

import org.openmrs.module.web.extension.LinkExt;

public class GutterListExt extends LinkExt {

    String urlg = "";
    String label = "";

    public String getLabel() {
        label = "Patient Narratives";
        return this.label;
    }

    public String getUrl() {
        urlg = "module/patientnarratives/patientNarrativesForm.form";
        return this.urlg;
    }

    /**
     * Returns the required privilege in order to see this section. Can be a
     * comma delimited list of privileges. If the default empty string is
     * returned, only an authenticated user is required
     *
     * @return Privilege string
     */
    public String getRequiredPrivilege() {
        return "Add Patient Narratives,Add Feedback";
    }

}


