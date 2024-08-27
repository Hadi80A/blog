package org.hammasir.blog.component;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
public class ExecutorConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        return Executors.newFixedThreadPool(10);
    }
}
