package me.dio.sdw.domain.ports;

import me.dio.sdw.domain.model.Champions;

import java.util.List;
import java.util.Optional;

public interface ChampionsRepository {
    
    List<Champions> findAll();

    Optional<Champions> findById(Long id);
}