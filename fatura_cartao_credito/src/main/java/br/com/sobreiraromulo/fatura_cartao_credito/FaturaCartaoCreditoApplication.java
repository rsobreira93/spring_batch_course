package br.com.sobreiraromulo.fatura_cartao_credito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FaturaCartaoCreditoApplication {

	public static void main(String[] args) {
		// SpringApplication.run(FaturaCartaoCreditoApplication.class, args);

		ConfigurableApplicationContext context = SpringApplication.run(FaturaCartaoCreditoApplication.class, args);
        context.close();
	}

}
