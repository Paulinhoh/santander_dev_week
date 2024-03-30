package me.dio.sdw.adapters.out;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;

import feign.FeignException;
import feign.RequestInterceptor;
import me.dio.sdw.domain.ports.GenerativeAiService;

@ConditionalOnProperty(name = "generative-ai.provider", havingValue = "OPENAI", matchIfMissing = true)
@FeignClient(name="openAiChatApi", url="${openai.base-url}", configuration = OpenAiChatService.Config.class)
public interface OpenAiChatService extends GenerativeAiService {

    @PostMapping("/v1/chat/completions")
    OpenAiChatCompletionResp chatCompletion(OpenAiChatCompletionReq req);

    @Override
    default String generateContent(String objective, String context) {
        String model = "gpt-3.5-turbo";
        List<Message> messages = List.of(
            new Message("system", objective),
            new Message("user", context)
        );
        OpenAiChatCompletionReq req = new OpenAiChatCompletionReq(model, messages);

        try {
            OpenAiChatCompletionResp resp = chatCompletion(req);
            return resp.choices().getFirst().message().content();
        } catch (FeignException httpErrors) {
            return "Deu ruim! Erro de comunicação com a API da OpenAi.";
        } catch (Exception unexpectedError) {
            return "Deu mais ruim ainda! O retorno da API da OpenAi não contem os dados esperados.";
        }
    }

    public record OpenAiChatCompletionReq(String model, List<Message> messages) {}
    public record Message(String role, String content) {}

    public record OpenAiChatCompletionResp(List<Choice> choices) {}
    public record Choice(Message message) {}

    public class Config {
        public RequestInterceptor apiKeyRequestInterceptor(@Value("${openai.api-key}") String apiKey) {
            return requestTemplate -> requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(apiKey));
        }
    }
}