package com.springbatch.enviopromocoesemail.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvioPromocoesClientesJobConfig {
	private final JobRepository jobRepository;

	public EnvioPromocoesClientesJobConfig(JobRepository jobRepository) {
		this.jobRepository = jobRepository;
	}

	@Bean
	public Job envioEmailClientesJob(Step envioEmailClientesStep) {
		return new JobBuilder("envioEmailClientesJob", jobRepository)
				.start(envioEmailClientesStep)
				.incrementer(new RunIdIncrementer())
				.build();
	}
}
