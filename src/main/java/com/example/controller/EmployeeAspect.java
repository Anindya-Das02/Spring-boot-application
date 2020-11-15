package com.example.controller;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Aspect
@Component
public class EmployeeAspect {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeAspect.class);
	
	@Before("execution(public * com.example.controller.EmployeeController.*(..))")
	public void beforeLog(JoinPoint joinPoint) {
		LOGGER.info("calling method : EmployeeController." +  joinPoint.getSignature().getName());
		//System.out.println("calling method: EmployeeController." + joinPoint.getSignature().getName());
	}
	
	@AfterReturning("execution(public * com.example.controller.EmployeeController.*(..))")
	public void afterReturnLog(JoinPoint joinPoint) {
		LOGGER.info("successfully executed method: EmployeeController." + joinPoint.getSignature().getName());
		//System.out.println("successfully executed method: EmployeeController." + joinPoint.getSignature().getName());
	}
	
	@AfterThrowing("execution(public * com.example.controller.EmployeeController.*(..))")
	public void afterThrowingErrorLog(JoinPoint joinPoint) {
		LOGGER.info("exception occured while executing method: EmployeeController." + joinPoint.getSignature().getName());
		//System.out.println("exception occured while executing method: EmployeeController." + joinPoint.getSignature().getName());
	}
}
