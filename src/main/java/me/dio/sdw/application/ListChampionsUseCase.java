package me.dio.sdw.application;

import me.dio.sdw.domain.model.Champions;
import me.dio.sdw.domain.ports.ChampionsRepository;

import java.util.List;

public record ListChampionsUseCase(ChampionsRepository repository) {

    List<Champions> findAll() {
        return repository.findAll();
    }
}