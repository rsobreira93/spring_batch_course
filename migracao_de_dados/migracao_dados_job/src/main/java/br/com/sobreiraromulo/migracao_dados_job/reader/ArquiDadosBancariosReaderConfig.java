package br.com.sobreiraromulo.migracao_dados_job.reader;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import br.com.sobreiraromulo.migracao_dados_job.dominio.DadosBancarios;

@Configuration
public class ArquiDadosBancariosReaderConfig {
    
      @Bean
    public FlatFileItemReader<DadosBancarios> arquivoDadosBancariosReader(){
        return new FlatFileItemReaderBuilder<DadosBancarios>()
                .name("arquivoDadosBancariosReader")
                .resource(new FileSystemResource("files/dados_bancarios.csv"))
                .delimited()
                .names("pessoaId", "agencia", "conta", "banco", "id")
                .addComment("--")//when have comments on file .csv this method ignore
                .targetType(DadosBancarios.class)
                // .fieldSetMapper(fieldSetMapper())
                .build();
    }

}
