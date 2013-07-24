package org.openmrs.module.patientnarratives.api.advice;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;

import java.lang.reflect.Method;

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

public class ObsServiceAroundAdvisor extends StaticMethodMatcherPointcutAdvisor implements Advisor {

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
        return new ObsServiceAroundAdvice();
    }

    private class ObsServiceAroundAdvice implements MethodInterceptor {
        public Object invoke(MethodInvocation invocation) throws Throwable {

            log.info("\n\n*********Before ObsServiceAroundAdvice ***********\n\n");

            Object object = null;
            String methodName = invocation.getMethod().getName();

            if (methodName.equals("saveObs")) {
                Obs obs = (Obs) invocation.getArguments()[0];
                User user = Context.getUserService().getUser(1);
                obs.setCreator(user);
                object = invocation.proceed();
            }

            log.info("\n\n*********After ObsServiceAroundAdvice ***********\n\n");

            return object;
        }
    }
}
