package org.openmrs.module.patientnarratives.dwr;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

public class DWRreCaptchaService {

    public boolean validateCaptcha(String challenge, String uresponse, String remoteip){

        // Captcha: https://developers.google.com/recaptcha/docs/java

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
