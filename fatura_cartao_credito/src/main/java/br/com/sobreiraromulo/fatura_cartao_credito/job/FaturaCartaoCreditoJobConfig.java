package br.com.sobreiraromulo.fatura_cartao_credito.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FaturaCartaoCreditoJobConfig {
    
    private final JobRepository jobRepository;

    public FaturaCartaoCreditoJobConfig(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Bean
    public Job faturaCartaoCreditoJob(JobRepository jobRepository,Step faturaCartaoCreditoStep){
        return new JobBuilder("faturaCartaoCreditoJob", jobRepository)
            .start(faturaCartaoCreditoStep)
            .incrementer(new RunIdIncrementer())
            .build();
    }
}
