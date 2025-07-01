package br.com.sobreiraromulo.migracao_dados_job.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
public class MigracaoDadosJobConfig {
    
    private final JobRepository jobRepository;

    public MigracaoDadosJobConfig(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }
    
    @Bean
    public Job migracaoDadosJob(
       JobRepository jobRepository,
       @Qualifier("migrarPessoaStep") Step migrarPessoaStep,
       @Qualifier("migrarDadosBancariosStep") Step migrarDadosBancariosStep){
        
      return new JobBuilder("migracaoDadosJob", jobRepository)
        .start(stepsParalelos(migrarPessoaStep, migrarDadosBancariosStep))
        .end()
        // .start(migrarPessoaStep)
        // .next(migrarDadosBancariosStep)
        .incrementer(new RunIdIncrementer())
        .build();
    }

    //I'm doing this because they are two parallel steps, the execution of one does not affect the execution of the other.
    private Flow stepsParalelos(Step migrarPessoaStep, Step migrarDadosBancariosStep) {
        Flow migrarDadosBancariosFlow = new FlowBuilder<Flow>("migrarDadosBancariosFlow")
            .start(migrarDadosBancariosStep)
            .build();
        
        Flow stepsParalelos = new FlowBuilder<Flow>("paralelosFlow")
            .start(migrarPessoaStep)
            .split(new SimpleAsyncTaskExecutor())
            .add(migrarDadosBancariosFlow)
            .build();

        return stepsParalelos;
    }
}
