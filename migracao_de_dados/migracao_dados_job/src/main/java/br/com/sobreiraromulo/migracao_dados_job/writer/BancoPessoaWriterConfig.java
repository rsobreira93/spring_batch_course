package br.com.sobreiraromulo.migracao_dados_job.writer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

import javax.sql.DataSource;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.sobreiraromulo.migracao_dados_job.dominio.Pessoa;

@Configuration
public class BancoPessoaWriterConfig {
    
    @Bean
    public JdbcBatchItemWriter<Pessoa> bancoPessoaWriter(
        @Qualifier("appDataSource") DataSource dataSource
    ){
        return new JdbcBatchItemWriterBuilder<Pessoa>()
                .dataSource(dataSource)
                .sql("INSERT INTO pessoa(id, nome, email, data_nascimento, idade) VALUES(?, ?, ?, ?, ? )")
                .itemPreparedStatementSetter(itemPreparedStatementSetter())
                .build();
    }

    //It was necessary to create because we are working with dates
    private ItemPreparedStatementSetter<Pessoa> itemPreparedStatementSetter() {
       return new ItemPreparedStatementSetter<Pessoa>() {

        @Override
        public void setValues(Pessoa pessoa, PreparedStatement ps) throws SQLException {
            ps.setInt(1, pessoa.getId());
            ps.setString(2, pessoa.getNome());
            ps.setString(3, pessoa.getEmail());
            ps.setDate(4, new Date(pessoa.getDataNascimento().getTime()));
            ps.setInt(5, pessoa.getIdade());
        }
        
       };
    }
}
