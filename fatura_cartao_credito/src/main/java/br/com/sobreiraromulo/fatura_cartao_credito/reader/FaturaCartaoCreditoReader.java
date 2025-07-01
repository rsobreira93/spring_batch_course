package br.com.sobreiraromulo.fatura_cartao_credito.reader;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

import br.com.sobreiraromulo.fatura_cartao_credito.dominio.FaturaCartaoCredito;
import br.com.sobreiraromulo.fatura_cartao_credito.dominio.Transacao;

@Configuration
public class FaturaCartaoCreditoReader implements ItemStreamReader<FaturaCartaoCredito>{

    private ItemStreamReader<Transacao> delegate;
    private Transacao transacaoAtual;

    public FaturaCartaoCreditoReader(ItemStreamReader<Transacao> delegate) {
        this.delegate = delegate;
    }

    @Override
    @Nullable
    public FaturaCartaoCredito read() throws Exception{
        if(transacaoAtual == null)
            transacaoAtual = delegate.read();
        
        FaturaCartaoCredito faturaCartaoCredito = null;
        Transacao transacao = transacaoAtual;
        transacaoAtual = null;

        if(transacao != null) {
            faturaCartaoCredito = new FaturaCartaoCredito();

            faturaCartaoCredito.setCartaoCredito(transacao.getCartaoCredito());
            faturaCartaoCredito.setCliente(transacao.getCartaoCredito().getCliente());
            faturaCartaoCredito.getTransacaos().add(transacao);

            while (isTransacaoRelacionada(transacao))
                faturaCartaoCredito.getTransacaos().add(transacao);
        }

        return faturaCartaoCredito;

    }

    private boolean isTransacaoRelacionada(Transacao transacao) throws Exception {
		return peek() != null && transacao.getCartaoCredito().getNumeroCartaoCredito() == transacaoAtual.getCartaoCredito().getNumeroCartaoCredito();
    }

    private Transacao peek() throws Exception {
       transacaoAtual = delegate.read();
       
       return transacaoAtual;
    }

    @SuppressWarnings("null")
    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        delegate.open(executionContext);
    }

    @Override
    public void update(@SuppressWarnings("null") ExecutionContext executionContext) throws ItemStreamException {
        delegate.update(executionContext);
    }

    @Override
    public void close() throws ItemStreamException {
        delegate.close();
    }
   
}
