package tr.bays.common.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
//import tr.bays.service.webService.util.EDevletSonuc;

/**
 * Aspect for logging execution of service and repository Spring components.
 * @author Ramesh Fadatare
 *
 */
@Aspect
@Component
@Slf4j
public class LoggingAspect {

//	private Log LOGGER = LogFactory.getLog(LoggingAspect.class);
	@Autowired

    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
        " || within(@org.springframework.stereotype.Service *)" +
        " || within(@tr.bays.BaysJsfController *)" +
        " || within(tr.bays.common.base.BaseCrudController)" +
        " || within(@org.springframework.stereotype.Controller *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("(within(tr.bays..*)" +
        " || within(tr.bays.service..*)" +
        " || within(tr.bays.common.base..*)" +
        " || within(tr.bays.controller.kurumsal..*)" +
        " || within(tr.bays.controller..*))" +
        " && !within(tr.bays.controller.sistem.SessionController)" +
        " && !within(tr.bays.controller.store.StoreController)"
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
    	log.info("Exception in "+joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName()+"() with cause = " +
            (e.getCause() != null ? e.getCause() : "NULL") + (e.getMessage() != null ? "\n" + e.getMessage() : ""));
    	
//    	String icerik = "Exception in "+joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName()+"() with cause = " +
//                (e.getCause() != null ? e.getCause() : "NULL") + " with argument[s] = "+Arrays.toString(joinPoint.getArgs());
    	
//    	StackTraceElement[] elements = e.getStackTrace();
//    	StackTraceElement[] five = new StackTraceElement[5];
//    	String hata = "";
//    	for(int i = 0; i < elements.length; i++) {
//    		hata = elements[i].toString();
//    		if(hata.startsWith("tr.bays.controller")) {
//				for(int j = 0; j < 5; j++) {
//					five[j] = elements[i+j];
//				}
//				break;
//			}
//    	}
//    	String fiveStr = Arrays.toString(five);
//    	icerik += "\n" + fiveStr;
//    	sunucuHataOzelLogService.ozelLogKaydet(icerik);
    }

    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param joinPoint join point for advice
     * @return result
     * @throws Throwable throws IllegalArgumentException
     */
    @Around("applicationPackagePointcut() && springBeanPointcut() && !execution(* set*(..)) && !execution(* get*(..)) && !execution(* isFormGoster*(..)) && !execution(* isUstPanel*(..)) && !execution(* isDark*(..)) && !execution(* logKaydet*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    	if(joinPoint.getSignature().getName().equals("getTheme")) {
    		Object result = joinPoint.proceed();
    		return result;
    	}
//    	LOGGER.info("EnterMYYYY: {}.{}() with argument[s] = {}" + joinPoint.getSignature().getDeclaringTypeName() + " :: "+
//                joinPoint.getSignature().getName()+"::"+ Arrays.toString(joinPoint.getArgs()));
    	log.info("Enter: "+joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName()+"() with argument[s] = "+Arrays.toString(joinPoint.getArgs())+"");
        try {
            Object result = joinPoint.proceed();
            log.info("Exit: "+joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName()+"() with result = "+result+"");
//            if(result instanceof EDevletSonuc)
//            	System.out.println("----------EDevletSonuc");
            return result;
        } catch (IllegalArgumentException e) {
        	log.info("Illegal argument: "+Arrays.toString(joinPoint.getArgs())+" in "+joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName()+"()");
            throw e;
        }
    }
}
