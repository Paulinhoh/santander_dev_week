package me.dio.sdw;

import me.dio.sdw.application.AskChampionUseCase;
import me.dio.sdw.application.ListChampionsUseCase;
import me.dio.sdw.domain.ports.ChampionsRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	public ListChampionsUseCase provideListChampionsUseCase(ChampionsRepository repository) {
		return new ListChampionsUseCase(repository); 
	}

	@Bean
	public AskChampionUseCase provideAskChampionsUseCase(ChampionsRepository repository) {
		return new AskChampionUseCase(repository); 
	}
}
