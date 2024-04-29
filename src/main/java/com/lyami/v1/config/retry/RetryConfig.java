package com.lyami.v1.config.retry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class RetryConfig {

    private final String maxAttempts;

    private final String backoffInitialInterval;

    private final String backoffMaxInterval;

    private final String backoffIntervalMultiplier;

    public RetryConfig(@Value("${retry.maxattempts}") String maxAttempts, @Value("${retry.backoff.initialinterval}") String backoffInitialInterval,
                       @Value("${retry.backoff.maxinterval}") String backoffMaxInterval, @Value("${retry.backoff.intervalMultiplier}") String backoffIntervalMultiplier) {
        this.maxAttempts = maxAttempts;
        this.backoffInitialInterval = backoffInitialInterval;
        this.backoffMaxInterval = backoffMaxInterval;
        this.backoffIntervalMultiplier = backoffIntervalMultiplier;
    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(Integer.parseInt(maxAttempts));
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(Integer.parseInt(backoffInitialInterval)); // 1 second
        backOffPolicy.setMaxInterval(Integer.parseInt(backoffMaxInterval));   // 60 seconds
        backOffPolicy.setMultiplier(Double.parseDouble(maxAttempts));      // Delay doubles on each retry
        retryTemplate.setBackOffPolicy(backOffPolicy);
        return retryTemplate;
    }
}
