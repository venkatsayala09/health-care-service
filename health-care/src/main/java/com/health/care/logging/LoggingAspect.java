package com.health.care.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Aspect for logging execution of service and repository Spring components.
 */
@Aspect
@Component
@Slf4j
public class LoggingAspect {


    /**
     * Pointcut that matches all repositories, service and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("within(com.health.care..*)" +
            " || within(com.health.care.service..*)" +
            " || within(com.health.care.controller..*)"+
            " || within(com.health.care.repository..*)"
    )
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Advice that logs methods throwing exceptions.
     *
     * @param joinPoint join point for advice
     * @param e exception
     */
    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL");
    }

    /**
     * Advice that logs after completing execution of methods
     * @param joinPoint
     */
    @AfterReturning("applicationPackagePointcut()")
    public void logMethodAccessAfter(JoinPoint joinPoint) {
        log.info("***** Completed: " + joinPoint.getSignature().getName() + " *****");
    }

    /**
     * Advice that logs before starting execution of methods
     * @param joinPoint
     */
    @Before("applicationPackagePointcut()")
    public void logMethodAccessBefore(JoinPoint joinPoint) {
        log.info("***** Starting: " + joinPoint.getSignature().getName() + " *****");
    }

    /**
     * Advice that logs execution time of a method.
     *
     * @param joinPoint join point for advice
     * @return result
     * @throws Throwable throws IllegalArgumentException
     */
    @Around("applicationPackagePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - start;
            log.info(joinPoint.getSignature() + " executed in " + executionTime + "ms");
        return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            throw e;
        }
    }
}