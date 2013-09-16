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
package org.openmrs.module.patientnarratives.dwr;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

/**
 *  This is a DWR (direct web remoting) class which is used to validate the Captcha.
 *  The data (String challenge, String uresponse, String remoteip) from the JSP view
 *  is been transferred to this class immediately before the form submission and
 *  it will return boolean whether the user writing and captcha combination is correct or not.
 */
public class DWRreCaptchaService {

    public boolean validateCaptcha(String challenge, String uresponse, String remoteip){

        // Captcha lib docs: https://developers.google.com/recaptcha/docs/java

        if (challenge != null){
            ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
            reCaptcha.setPrivateKey("6LdAWuMSAAAAALxWgnM5yRj_tGVRQCk4lit8rLHb");
            ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteip, challenge, uresponse);

            if (reCaptchaResponse.isValid()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
