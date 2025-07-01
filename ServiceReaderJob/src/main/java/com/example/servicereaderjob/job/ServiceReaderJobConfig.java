package com.example.servicereaderjob.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceReaderJobConfig {
  private final JobRepository jobRepository;

  public ServiceReaderJobConfig(JobRepository jobRepository) {
    this.jobRepository = jobRepository;
  }

  @Bean
  Job job(Step serviceReaderStep) {
    return new JobBuilder("serviceReaderJob", jobRepository)
        .start(serviceReaderStep)
        .incrementer(new RunIdIncrementer())
        .build();
  }
}
