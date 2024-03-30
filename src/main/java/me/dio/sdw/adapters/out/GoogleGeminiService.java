package me.dio.sdw.adapters.out;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import feign.FeignException;
import feign.RequestInterceptor;
import me.dio.sdw.domain.ports.GenerativeAiService;

@ConditionalOnProperty(name = "generative-ai.provider", havingValue = "GEMINI")
@FeignClient(name="geminiApi", url="${gemini.base-url}", configuration = GoogleGeminiService.Config.class)
public interface GoogleGeminiService extends GenerativeAiService {

    @PostMapping("/v1beta/models/gemini-pro:generateContent")
    GoogleGeminiResp textOnlyInput(GoogleGeminiReq req);

    @Override
    default String generateContent(String objective, String context) {
        String prompt = """
                %s
                %s
                """.formatted(objective, context);

        GoogleGeminiReq req = new GoogleGeminiReq(
            List.of(new Content(List.of(new Part(prompt))))
        );

        try {
            GoogleGeminiResp resp = textOnlyInput(req);
            return resp.candidates().getFirst().content().parts().getFirst().text();  
        } catch (FeignException httpErrors) {
            return "Deu ruim! Erro de comunicação com a API do Google Gemini.";
        } catch (Exception unexpectedError) {
            return "Deu mais ruim ainda! O retorno da API do Google Gemini não contem os dados esperados.";
        }
    }

    public record GoogleGeminiReq(List<Content> contents) {}
    public record Content(List<Part> parts) {}
    public record Part(String text) {}
    
    public record GoogleGeminiResp(List<Candidate> candidates) {}
    public record Candidate(Content content) {}

    public class Config {
        public RequestInterceptor apiKeyRequestInterceptor(@Value("${gemini.api-key}") String apiKey) {
            return requestTemplate -> requestTemplate.query("key", apiKey);
        }
    }
}