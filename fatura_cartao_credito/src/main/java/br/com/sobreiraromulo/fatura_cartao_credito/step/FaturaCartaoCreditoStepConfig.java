package br.com.sobreiraromulo.fatura_cartao_credito.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import br.com.sobreiraromulo.fatura_cartao_credito.dominio.FaturaCartaoCredito;
import br.com.sobreiraromulo.fatura_cartao_credito.dominio.Transacao;
import br.com.sobreiraromulo.fatura_cartao_credito.reader.FaturaCartaoCreditoReader;
import br.com.sobreiraromulo.fatura_cartao_credito.writer.TotalTransacoesFooterCallback;

@Configuration
public class FaturaCartaoCreditoStepConfig {

    private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;

	public FaturaCartaoCreditoStepConfig(JobRepository jobRepository,
		PlatformTransactionManager transactionManager) {
		this.jobRepository = jobRepository;
		this.transactionManager = transactionManager;
	}
    
    @Bean
    public Step faturaCartaoCreditoStep(
       ItemStreamReader<Transacao> lerTransacoesReader,
			ItemProcessor<FaturaCartaoCredito, FaturaCartaoCredito> carregarDadosClienteProcessor,
			ItemWriter<FaturaCartaoCredito> escreverFaturaCartaoCredito,
			TotalTransacoesFooterCallback listener){
            return new StepBuilder("faturaCartaoCreditoStep", jobRepository)
                    .<FaturaCartaoCredito, FaturaCartaoCredito>chunk(1, transactionManager)
                    .reader(new FaturaCartaoCreditoReader(lerTransacoesReader))
                    .processor(carregarDadosClienteProcessor)
                    .writer(escreverFaturaCartaoCredito)
                    .listener(listener)
                    .build();
    }
}
