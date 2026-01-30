package com.aniljangir.payments.batch;

import com.aniljangir.payments.domain.PaymentTransaction;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class PaymentJobConfig {

    private static final int CHUNK_SIZE = 100;

    @Bean
    public Job paymentProcessingJob(
            JobRepository jobRepository,
            Step paymentProcessingStep
    ) {
        return new JobBuilder("paymentProcessingJob", jobRepository)
                .start(paymentProcessingStep)
                .build();
    }

    @Bean
    public Step paymentProcessingStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            PaymentItemReader reader,
            PaymentItemProcessor processor,
            PaymentItemWriter writer
    ) {
        return new StepBuilder("paymentProcessingStep", jobRepository)
                .<PaymentTransaction, PaymentTransaction>chunk(CHUNK_SIZE, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .retry(Exception.class)
                .retryLimit(3)
                .skip(Exception.class)
                .skipLimit(5)
                .build();
    }
}
