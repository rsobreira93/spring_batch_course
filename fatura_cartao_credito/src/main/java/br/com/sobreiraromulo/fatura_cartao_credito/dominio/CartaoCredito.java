package br.com.sobreiraromulo.fatura_cartao_credito.dominio;

public class CartaoCredito {

    private int numeroCartaoCredito;
    private Cliente cliente;

    
    public int getNumeroCartaoCredito() {
        return numeroCartaoCredito;
    }
    public void setNumeroCartaoCredito(int numeroCartaoCredito) {
        this.numeroCartaoCredito = numeroCartaoCredito;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    
}
