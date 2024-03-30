package me.dio.sdw.adapters.in;

import me.dio.sdw.application.ListChampionsUseCase;
import me.dio.sdw.domain.model.Champion;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Campeões", description = "Endpoints do dominio de Campeões do Lol")
@RestController
@RequestMapping("/champions")
public record ListChampionsRestController(ListChampionsUseCase useCase) {

    @GetMapping
    public List<Champion> findAllChampions() {
        return useCase.findAll();
    }
}