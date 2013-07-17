package org.openmrs.module.patientnarratives.api.advice;

import java.lang.reflect.Method;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.springframework.aop.MethodBeforeAdvice;

public class HtmlFormParametersAroundAdvisor implements MethodBeforeAdvice {

    private Log log = LogFactory.getLog(this.getClass());

    private int count = 0;

    public void before(Method method, Object[] args, Object target) throws Throwable {

        log.debug("\n\n*********Before HtmlFormParametersAroundAdvisor ***********\n\n");
        if (method.getName().equals("saveEncounter")) {
                Encounter encounter = (Encounter) args[0];
                User user = Context.getUserService().getUser(2);
                encounter.setCreator(user);
                count++;
        }
        log.debug("\n\n*********After HtmlFormParametersAroundAdvisor ******" + count + "*****\n\n");
    }
}

//public class HtmlFormParametersAroundAdvisor extends StaticMethodMatcherPointcutAdvisor implements Advisor {
//
//    private static final long serialVersionUID = 3333L;
//
//    private Log log = LogFactory.getLog(this.getClass());
//
//    public boolean matches(Method method, Class targetClass) {
//        // only 'run' this advice on the save methods
//        if (method.getName().startsWith("save"))
//            return true;
//
//        return false;
//    }
//
//    @Override
//    public Advice getAdvice() {
//        log.debug("Getting new around advice");
//        return new PrintingAroundAdvice();
//    }
//
//    private class PrintingAroundAdvice implements MethodInterceptor {
//        public Object invoke(MethodInvocation invocation) throws Throwable {
//
//                        log.debug("\n\n*********Before PrintingAroundAdvice ***********\n\n");
//
//            Object object = null;
//            String methodName = invocation.getMethod().getName();
//
//            if (methodName.equals("saveEncounter")) {
//
//                Encounter encounter = (Encounter) invocation.getArguments()[0];
//
//                User user = Context.getUserService().getUser(2);
//                encounter.setCreator(user);
//                object = invocation.proceed();
//
//            }
//
//
////            log.debug("After " + invocation.getMethod().getName() + ".");
//
//            return object;
//        }
//    }
