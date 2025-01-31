package org.jpa.chap03.config;

import lombok.RequiredArgsConstructor;
import org.jpa.chap03.listener.HelloListener;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
@RequiredArgsConstructor
public class JobListenerConfig implements SchedulerFactoryBeanCustomizer {
    private final HelloListener helloListener;
    @Override
    public void customize(SchedulerFactoryBean schedulerFactoryBean) {
        schedulerFactoryBean.setGlobalJobListeners(helloListener);
    }
}
