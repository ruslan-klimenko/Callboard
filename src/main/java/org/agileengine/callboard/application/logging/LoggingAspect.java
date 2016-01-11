package org.agileengine.callboard.application.logging;

import org.agileengine.callboard.application.ApplicationData;
import org.agileengine.callboard.model.persistence.logging.ApplicationLog;
import org.agileengine.callboard.persistence.dao.logging.ApplicationLogDAOImpl;
import org.agileengine.callboard.application.exception.ApplicationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    private ApplicationLogDAOImpl applicationLogDAO;

    @Autowired
    public LoggingAspect(ApplicationLogDAOImpl applicationLogDAO) {
        this.applicationLogDAO = applicationLogDAO;
    }

    @Around("@annotation("+ ApplicationData.PACKAGE_LOGGING +".Loggable)")
    public Object applicationAroundAdvice(ProceedingJoinPoint proceedingJoinPoint){

        ApplicationLog.ApplicationLogBuilder applicationLogBuilder =
                new ApplicationLog.ApplicationLogBuilder();
        applicationLogBuilder.setOccurrence(proceedingJoinPoint.toString());

        try {
            Object result = proceedingJoinPoint.proceed();
            applicationLogDAO.create(applicationLogBuilder
                    .setCompleted(true)
                    .setResult(result == null ?
                            "Empty value" : result.toString())
                    .build());

            return result;
        } catch (Throwable e) {

            applicationLogDAO.create(applicationLogBuilder
                    .setCompleted(false)
                    .setResult(e.toString()).build());

            throw new ApplicationException(e.getMessage());
        }
    }
}
