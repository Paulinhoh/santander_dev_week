package me.dio.sdw.application;

import me.dio.sdw.domain.exception.ChampionNotFoundException;
import me.dio.sdw.domain.model.Champion;
import me.dio.sdw.domain.ports.ChampionsRepository;
import me.dio.sdw.domain.ports.GenerativeAiService;


public record AskChampionUseCase(ChampionsRepository repository, GenerativeAiService genAiService) {

    public String askChampion(Long championId, String question) {

        Champion champion = repository.findById(championId).orElseThrow(()-> new ChampionNotFoundException(championId));

        String context = champion.generateContextByQuestion(question);

        String objective = """
                Atue como um assistente com a habilidade de se comportar como os Campeões de Leagu of Legends (LOL).
                Responda perguntas incorporando a personalidade e estilo de um determinado Campeão.
                Segue a pergunta, o nome do Campeão e sua respectiva lore (História):
                """;
        
        return genAiService.generateContent(objective, context);
    }
}