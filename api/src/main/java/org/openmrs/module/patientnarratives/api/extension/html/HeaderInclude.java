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

package org.openmrs.module.patientnarratives.api.extension.html;


import org.openmrs.module.web.extension.HeaderIncludeExt;

import java.util.ArrayList;
import java.util.List;

public class HeaderInclude extends HeaderIncludeExt {
    public List<String> getHeaderFiles() {
        List<String> myHeaderFiles = new ArrayList<String>();

//        myHeaderFiles.add("/scripts/jquery/jquery.min.js");
//        myHeaderFiles.add("/scripts/jquery-ui/js/jquery-ui.custom.min.js");

//        myHeaderFiles.add("/scripts/jquery/jquery.min.js");
//         myHeaderFiles.add("/moduleResources/patientnarratives/js/bootstrap.min.js");
//        myHeaderFiles.add("/scripts/jquery/jquery.min.js");

//        myHeaderFiles.add("/moduleResources/patientnarratives/css/styles.css");

        return myHeaderFiles;
    }
}

