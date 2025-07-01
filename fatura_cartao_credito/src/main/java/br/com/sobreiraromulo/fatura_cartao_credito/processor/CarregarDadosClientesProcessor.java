package br.com.sobreiraromulo.fatura_cartao_credito.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.sobreiraromulo.fatura_cartao_credito.dominio.Cliente;
import br.com.sobreiraromulo.fatura_cartao_credito.dominio.FaturaCartaoCredito;

@Component
public class CarregarDadosClientesProcessor implements ItemProcessor<FaturaCartaoCredito, FaturaCartaoCredito>{

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    @Nullable
    public FaturaCartaoCredito process(@NonNull FaturaCartaoCredito faturaCartaoCredito) throws Exception {
        String uri = String.format("https://my-json-server.typicode.com/giuliana-bezerra/demo/profile/%d", faturaCartaoCredito.getCartaoCredito().getCliente().getId());
       
        ResponseEntity<Cliente> response = restTemplate.getForEntity(uri, Cliente.class);
       
        System.out.println(String.format("cliente  %s", response.getBody().getNome()));

        if(response.getStatusCode() != HttpStatus.OK)
            throw new ValidationException("Cliente não encontrado");

        faturaCartaoCredito.setCliente(response.getBody());

        return faturaCartaoCredito;
    }
    
}
