package me.dio.sdw.adapters.out;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;

import feign.RequestInterceptor;
import me.dio.sdw.domain.ports.GenerativeAiService;

@FeignClient(name="openAiChatApi", url="${openai.base-url}")
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
        OpenAiChatCompletionResp resp = chatCompletion(req);

        return resp.choices().getFirst().message().content();
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