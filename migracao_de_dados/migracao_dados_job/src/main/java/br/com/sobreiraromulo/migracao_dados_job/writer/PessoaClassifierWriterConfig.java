package br.com.sobreiraromulo.migracao_dados_job.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemWriterBuilder;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.sobreiraromulo.migracao_dados_job.dominio.Pessoa;

@Configuration
public class PessoaClassifierWriterConfig {
    
    @Bean
    public ClassifierCompositeItemWriter<Pessoa> pessoaClassifierWriter(
        JdbcBatchItemWriter<Pessoa> bancoPessoaWriter,
        FlatFileItemWriter<Pessoa> arquivoPesoasInvalidasWriter) {
            return new ClassifierCompositeItemWriterBuilder<Pessoa>()
                    .classifier(classifier(bancoPessoaWriter, arquivoPesoasInvalidasWriter))
                    .build();
        }

    private Classifier<Pessoa, ItemWriter<? super Pessoa>> classifier(JdbcBatchItemWriter<Pessoa> bancoPessoaWriter,
            FlatFileItemWriter<Pessoa> arquivoPesoasInvalidasWriter) {
       return new Classifier<Pessoa,ItemWriter<? super Pessoa>>() {

        @Override
        public ItemWriter<? super Pessoa> classify(Pessoa pessoa) {
           if(pessoa.isValida())
                return bancoPessoaWriter;
            else
                return arquivoPesoasInvalidasWriter;
        }
        
       };
    }
}
