package br.com.sobreiraromulo.migracao_dados_job.reader;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.validation.BindException;

import br.com.sobreiraromulo.migracao_dados_job.dominio.Pessoa;

@Configuration
public class ArquivoPessoaReaderConfig {
    
    @Bean
    public FlatFileItemReader<Pessoa> arquivoPessoaReader(){
        return new FlatFileItemReaderBuilder<Pessoa>()
                .name("arquivoPessoaReader")
                .resource(new FileSystemResource("files/pessoas.csv"))
                .delimited()
                .names("nome", "email", "dataNascimento", "idade", "id")
                .addComment("--")//when have comments on file .csv this method ignore
                // .targetType(Pessoa.class)
                .fieldSetMapper(fieldSetMapper())
                .build();
    }

    private FieldSetMapper<Pessoa> fieldSetMapper() {
       return new FieldSetMapper<Pessoa>() {

        @SuppressWarnings("null")
        @Override
        public Pessoa mapFieldSet(FieldSet fieldSet) throws BindException {
            Pessoa pessoa = new Pessoa();

            pessoa.setNome(fieldSet.readString("nome"));
            pessoa.setEmail(fieldSet.readString("email"));
            pessoa.setDataNascimento(fieldSet.readDate("dataNascimento", "yyyy-MM-dd HH:mm:ss"));
            pessoa.setIdade(fieldSet.readInt("idade"));
            pessoa.setId(fieldSet.readInt("id"));

            return pessoa;
        }
        
       };
    }
}
