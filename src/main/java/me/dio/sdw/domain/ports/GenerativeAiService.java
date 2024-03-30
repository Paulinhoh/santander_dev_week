package me.dio.sdw.domain.ports;

public interface GenerativeAiService {
    
    String generateContent(String objective, String context);
}