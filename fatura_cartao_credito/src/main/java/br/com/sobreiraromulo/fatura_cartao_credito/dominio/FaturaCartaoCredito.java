package br.com.sobreiraromulo.fatura_cartao_credito.dominio;

import java.util.ArrayList;
import java.util.List;

public class FaturaCartaoCredito {
    
    private Cliente cliente;
    private CartaoCredito cartaoCredito;
    private List<Transacao> transacaos = new ArrayList<>();
    
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public CartaoCredito getCartaoCredito() {
        return cartaoCredito;
    }
    public void setCartaoCredito(CartaoCredito cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
    }
    public List<Transacao> getTransacaos() {
        return transacaos;
    }
    public void setTransacaos(List<Transacao> transacaos) {
        this.transacaos = transacaos;
    }
    public Double getTotal() {
      return transacaos
            .stream()
            .mapToDouble(Transacao::getValor)
            .reduce(0.0, Double::sum);
    }
}
