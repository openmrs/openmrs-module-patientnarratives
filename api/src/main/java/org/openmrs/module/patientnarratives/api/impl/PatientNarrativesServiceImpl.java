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
package org.openmrs.module.patientnarratives.api.impl;

import org.openmrs.api.APIException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.patientnarratives.api.NarrativeComments;
import org.openmrs.module.patientnarratives.api.PatientNarrativesService;
import org.openmrs.module.patientnarratives.api.db.PatientNarrativesDAO;

import java.util.List;

/**
 * It is a default implementation of {@link PatientNarrativesService}.
 */
public class PatientNarrativesServiceImpl implements PatientNarrativesService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private PatientNarrativesDAO patientNarrativesDAO;

    public PatientNarrativesServiceImpl() {}

    public void setPatientNarrativesDAO(PatientNarrativesDAO patientNarrativesDAO) {
	    this.patientNarrativesDAO = patientNarrativesDAO;
    }

    public PatientNarrativesDAO getPatientNarrativesDAO() {
	    return patientNarrativesDAO;
    }

    public void saveNarrativesComment(NarrativeComments narrativeComments) throws APIException {
        getPatientNarrativesDAO().saveNarrativeComments(narrativeComments);
    }

    public List<NarrativeComments> getNarrativeComments(Integer encounterId) throws APIException {
        return getPatientNarrativesDAO().getNarrativeComments(encounterId);
    }
}