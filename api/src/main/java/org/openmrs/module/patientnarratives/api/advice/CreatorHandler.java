//package org.openmrs.module.patientnarratives.api.advice;
//
//import org.openmrs.Obs;
//import org.openmrs.User;
//import org.openmrs.annotation.Handler;
//import org.openmrs.api.context.Context;
//import org.openmrs.api.handler.SaveHandler;
//
//import java.util.Date;
//
//@Handler(supports = Obs.class)
//public class CreatorHandler implements SaveHandler<Obs> {
//
//    public void handle(Obs obs, User currentUser, Date currentDate, String reason) {
//
//        if (obs.getCreator() == null) {
//            User user = Context.getUserService().getUser(3);
//            obs.setCreator(user);
//        }
//    }
//}
