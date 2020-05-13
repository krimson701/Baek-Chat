package com.krimson701.baekchat.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

/**
 * 비동기 처리에 관한 설정. Created by MinYoung on 2016. 7. 28..
 */
@Configuration
@EnableAsync
@Slf4j
//@EnableAspectJAutoProxy
//<!--<task:executor id="asyncExecutor" pool-size="100" queue-capacity="1000"  rejection-policy="ABORT" />-->
//<!--<task:scheduler id="asyncScheduler" pool-size="100" />-->
//<!--<task:annotation-driven executor="asyncExecutor" scheduler="asyncScheduler"/>-->
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(100);
        executor.setQueueCapacity(10);
        executor.setMaxPoolSize(1000);
        executor.setThreadNamePrefix("AsyncExecutor-");
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncUncaughtExceptionHandler() {
            @Override
            public void handleUncaughtException(Throwable ex, Method method, Object... params) {
                log.error("AsyncUncaughtExceptionHandler", ex);
                log.error("params {}", params);
            }
        };
    }
}