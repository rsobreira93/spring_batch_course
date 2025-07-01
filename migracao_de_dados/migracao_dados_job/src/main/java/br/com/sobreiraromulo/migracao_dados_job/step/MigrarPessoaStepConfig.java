package br.com.sobreiraromulo.migracao_dados_job.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import br.com.sobreiraromulo.migracao_dados_job.dominio.Pessoa;

@Configuration
public class MigrarPessoaStepConfig {
    
    @Bean
    public Step migrarPessoaStep(
        JobRepository jobRepository,
        PlatformTransactionManager transactionManager,
        ItemReader<Pessoa> arquivoPessoaReader,
        // ItemWriter<Pessoa> bancoPessoaWriter,
        ClassifierCompositeItemWriter<Pessoa> pessoaClassifierWriter,
        FlatFileItemWriter<Pessoa> arquivoPessoasInvalidasWriter) {
            
        return new StepBuilder("migrarPessoaStep", jobRepository)
            .<Pessoa, Pessoa>chunk(10000, transactionManager)
            .reader(arquivoPessoaReader)
            .writer(pessoaClassifierWriter)
            .stream(arquivoPessoasInvalidasWriter)
            .build();
    }
}
