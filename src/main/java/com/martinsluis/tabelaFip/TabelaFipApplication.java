package com.martinsluis.tabelaFip;

import com.martinsluis.tabelaFip.Principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TabelaFipApplication implements CommandLineRunner { //para o usuário interagir por meio do terminal

	public static void main(String[] args) {
		SpringApplication.run(TabelaFipApplication.class, args);
	}

	@Override
	public void run(String ... args) throws Exception{
		Principal principal = new Principal();
		principal.exibirMenu();
	}

}
