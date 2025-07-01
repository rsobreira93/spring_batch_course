package com.springbatch.processadorclassifier.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.Nullable;

import com.springbatch.processadorclassifier.dominio.Cliente;

public class ClienteProcessor implements ItemProcessor<Cliente, Cliente> {

    @SuppressWarnings("null")
    @Override
    @Nullable
    public Cliente process(Cliente item) throws Exception {
        System.out.println(String.format("\nAplicando regras de negócio no cliente %s", item.getEmail()));
        return item;
    }
    
}
