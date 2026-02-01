package com.aniljangir.payments.api;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentJobController {

    private final JobLauncher jobLauncher;
    private final Job paymentProcessingJob;

    public PaymentJobController(JobLauncher jobLauncher, Job paymentProcessingJob) {
        this.jobLauncher = jobLauncher;
        this.paymentProcessingJob = paymentProcessingJob;
    }

    @PostMapping("/jobs/payment-process")
    public String runPaymentJob() throws Exception {

        JobParameters params = new JobParametersBuilder()
                .addLong("run.id", System.currentTimeMillis()) // ensures uniqueness
                .toJobParameters();

        JobExecution execution = jobLauncher.run(paymentProcessingJob, params);

        return "Payment Job started. ExecutionId=" + execution.getId();
    }
}
