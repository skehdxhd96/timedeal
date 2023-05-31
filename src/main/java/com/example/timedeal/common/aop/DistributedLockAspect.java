package com.example.timedeal.common.aop;

import com.example.timedeal.common.annotation.DistributedLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class DistributedLockAspect {

    private final RedissonClient redissonClient;

    @Around("@annotation(distributedLock)")
    public Object lock(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) throws Throwable{

        RLock lock = redissonClient.getLock(distributedLock.lockName());
        boolean isLocked = lock.tryLock(distributedLock.waitTime(), distributedLock.leaseTime(), distributedLock.unit());

        try {
            if(!isLocked) {
                throw new IllegalStateException("Lock 획득 실패");
            }

            log.info("thread : {} signature : {} 락 획득 성공", Thread.currentThread(), joinPoint.getSignature());
            return joinPoint.proceed();
        } finally {
            if(lock.isLocked() && lock.isHeldByCurrentThread()) lock.unlock();
            log.info("thread : {} signature : {} 락 반납 완료", Thread.currentThread(), joinPoint.getSignature());
        }
    }
}
