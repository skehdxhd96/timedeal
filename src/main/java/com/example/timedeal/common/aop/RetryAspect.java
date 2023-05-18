package com.example.timedeal.common.aop;

import com.example.timedeal.common.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class RetryAspect {

    @Around("@annotation(retry)")
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {

        log.info("재고 감소로직 재시작 [retry] : {}, [args] : {}", joinPoint.getSignature(), retry);

        int maxRetry = retry.retryCount();
        Exception exceptionHolder = null;

        for(int retryCount = 1; retryCount <= maxRetry; retryCount++) {
            try {
                log.info("재시도 횟수 : {}", retryCount);
                return joinPoint.proceed();
            } catch(Exception e) {
                exceptionHolder = e;
            }
        }

        throw exceptionHolder;
    }
}
