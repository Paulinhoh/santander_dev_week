package me.dio.sdw.application;

import me.dio.sdw.domain.exception.ChampionNotFoundException;
import me.dio.sdw.domain.model.Champion;
import me.dio.sdw.domain.ports.ChampionsRepository;


public record AskChampionUseCase(ChampionsRepository repository) {

    public String askChampion(Long championId, String question) {

        Champion champion = repository.findById(championId).orElseThrow(()-> new ChampionNotFoundException(championId));

        String championContext = champion.generateContextByQuestion(question);

        return championContext;
    }
}