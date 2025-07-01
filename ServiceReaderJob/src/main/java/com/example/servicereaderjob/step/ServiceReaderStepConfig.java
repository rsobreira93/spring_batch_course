package com.example.servicereaderjob.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.servicereaderjob.domain.User;

@Configuration
public class ServiceReaderStepConfig {
  private final JobRepository jobRepository;
  private final PlatformTransactionManager transactionManager;

  public ServiceReaderStepConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    this.jobRepository = jobRepository;
    this.transactionManager = transactionManager;
  }

  @Value("${chunkSize}")
  private int chunkSize;

  @Bean
  Step step(ItemReader<User> reader, ItemWriter<User> writer) {
    return new StepBuilder("serviceReaderStep", jobRepository)
        .<User, User>chunk(chunkSize, transactionManager)
        .reader(reader)
        .writer(writer)
        .build();
  }
}
