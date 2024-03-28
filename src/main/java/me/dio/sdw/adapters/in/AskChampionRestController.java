package me.dio.sdw.adapters.in;

import me.dio.sdw.application.AskChampionUseCase;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

@Tag(name = "Campeões", description = "Endpoints do dominio de Campeões do Lol")
@RestController
@RequestMapping("/champions")
public record AskChampionRestController(AskChampionUseCase useCase) {

    @CrossOrigin
    @PostMapping("/{championId}/ask")
    public AskChampionResponse askChampion(@PathVariable Long championId, @RequestBody AskChampionRequest resquest) {
        String answer = useCase.askChampion(championId, resquest.question());
        return new AskChampionResponse(answer);
    }

    public record AskChampionRequest(String question) {}
    public record AskChampionResponse(String answer) {}
}