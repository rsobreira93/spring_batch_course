package com.udemy.configuracaojob;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfiguracaoJobConfig {
	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job imprimeOlaJob() {
		return new JobBuilder("imprimeOlaJob", jobRepository)
				.start(imprimeOlaStep())
				.incrementer(new RunIdIncrementer())
				.build();
	}

	@Bean
	public Step imprimeOlaStep(@Value("#{jobParameters['nome']}") String nome) {
		return stepBuilderFactory.get("imprimeOlaStep").tasklet(imprimeOlaTasklet(nome)).build();
	}

	@Bean
	public Tasklet imprimeOlaTasklet(String nome) {
		return new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println(String.format("Olá, %s!", nome));
				return RepeatStatus.FINISHED;
			}
		};
	}
}
