package com.fiap.pos_tech.agendamento_servicos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AgendamentoServicosApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgendamentoServicosApplication.class, args);
	}

}
