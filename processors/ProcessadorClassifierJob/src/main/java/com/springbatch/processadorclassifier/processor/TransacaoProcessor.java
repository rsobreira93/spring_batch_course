package com.springbatch.processadorclassifier.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.Nullable;

import com.springbatch.processadorclassifier.dominio.Transacao;

public class TransacaoProcessor implements ItemProcessor<Transacao, Transacao> {

    @SuppressWarnings("null")
    @Override
    @Nullable
    public Transacao process(Transacao item) throws Exception {
        System.out.println(String.format("\nAplicando regras de negócio na transação %s", item.getId()));
        return item;
    }

}
