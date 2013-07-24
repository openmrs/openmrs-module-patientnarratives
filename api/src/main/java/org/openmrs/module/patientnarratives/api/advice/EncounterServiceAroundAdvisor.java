package org.openmrs.module.patientnarratives.api.advice;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.EncounterProvider;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.springframework.aop.Advisor;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;

//public class EncounterServiceAroundAdvisor implements MethodBeforeAdvice {
//
//    private Log log = LogFactory.getLog(this.getClass());
//
//    private int count = 0;
//
//    public void before(Method method, Object[] args, Object target) throws Throwable {
//
//        log.info("\n\n*********Before EncounterServiceAroundAdvisor ***********\n\n");
//
//        if (method.getName().equals("saveEncounter")) {
//            Encounter encounter = (Encounter) args[0];
//            User user = Context.getUserService().getUser(1);
//            encounter.setCreator(user);
//            count++;
//        }
//
//        log.info("\n\n*********After EncounterServiceAroundAdvisor ******" + count + "*****\n\n");
//    }
//}

public class EncounterServiceAroundAdvisor extends StaticMethodMatcherPointcutAdvisor implements Advisor {

    private static final long serialVersionUID = 3333L;

    private Log log = LogFactory.getLog(this.getClass());

    public boolean matches(Method method, Class targetClass) {
        // only 'run' this advice on the save methods
        if (method.getName().startsWith("save"))
            return true;

        return false;
    }

    @Override
    public Advice getAdvice() {
        log.debug("Getting new around advice");
        return new EncounterServiceAroundAdvice();
    }

    private class EncounterServiceAroundAdvice implements MethodInterceptor {
        public Object invoke(MethodInvocation invocation) throws Throwable {

            log.info("\n\n*********Before EncounterServiceAroundAdvice ***********\n\n");

            Object object = null;
            String methodName = invocation.getMethod().getName();

            if (methodName.equals("saveEncounter")) {

                Encounter encounter = (Encounter) invocation.getArguments()[0];
                User user = Context.getUserService().getUser(3);
                encounter.setCreator(user);

                Set<EncounterProvider> encounterProviders = encounter.getEncounterProviders();
//                Set<EncounterProvider> newEncPs = new LinkedHashSet<EncounterProvider>();
                for(EncounterProvider encounterP : encounterProviders)  {
                    encounterP.setCreator(user);
//                    newEncPs.add(encounterP);
                }
//                encounter.setEncounterProviders(newEncPs);

                object = invocation.proceed();
            }

            log.info("\n\n*********After EncounterServiceAroundAdvice ***********\n\n");

            return object;
        }
    }
}
