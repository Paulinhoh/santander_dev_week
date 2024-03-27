package me.dio.sdw.application;

import me.dio.sdw.domain.model.Champion;
import me.dio.sdw.domain.ports.ChampionsRepository;

import java.util.List;

public record ListChampionsUseCase(ChampionsRepository repository) {

    public List<Champion> findAll() {
        return repository.findAll();
    }
}