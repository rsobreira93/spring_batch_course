package br.com.sobreiraromulo.fatura_cartao_credito.reader;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import br.com.sobreiraromulo.fatura_cartao_credito.dominio.CartaoCredito;
import br.com.sobreiraromulo.fatura_cartao_credito.dominio.Cliente;
import br.com.sobreiraromulo.fatura_cartao_credito.dominio.Transacao;

@Configuration
public class LerTransacoesReaderConfig {
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public JdbcCursorItemReader<Transacao> lerTransacoesReader(
        @Qualifier("appDataSource") DataSource dataSource
    ){
        return new JdbcCursorItemReaderBuilder()
                .name("lerTransacoesReader")
                .dataSource(dataSource) 
                .sql("select * from transacao join cartao_credito using (numero_cartao_credito) order by numero_cartao_credito")
                .rowMapper(rowMapperTransacao())
                .build();
    }

    private RowMapper<Transacao> rowMapperTransacao() {
       return new RowMapper<Transacao>() {

        @SuppressWarnings("null")
        @Override
        @Nullable
        public Transacao mapRow(ResultSet rs, int rowNum) throws SQLException {
            CartaoCredito cartaoCredito = new CartaoCredito();
            Cliente cliente = new Cliente();
            Transacao transacao = new Transacao();

            cartaoCredito.setNumeroCartaoCredito(rs.getInt("numero_cartao_credito"));
            
            cliente.setId(rs.getInt("cliente"));

            cartaoCredito.setCliente(cliente);

            transacao.setId(rs.getInt("id"));
            transacao.setCartaoCredito(cartaoCredito);
            transacao.setData(rs.getDate("data"));
            transacao.setValor(rs.getDouble("valor"));
            transacao.setDescricao(rs.getString("descricao"));

            return transacao;

        }
        
       };
    }
}
