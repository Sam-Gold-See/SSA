package com.atguigu.study.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author HuangChunXin
 * @date 2026/4/3 11:31
 */
@Configuration
public class SaaLLMConfig {

	@Value("${spring.ai.openai.api-key}")
	private String apiKey;

	@Value("${spring.ai.openai.base-url}")
	private String baseUrl;

	@Bean
	public OpenAiApi openAiApi() {
		return OpenAiApi.builder()
				.baseUrl(baseUrl)
				.apiKey(apiKey)
				.build();
	}

	@Bean
	public ChatClient chatClient(ChatModel chatModel) {
		return ChatClient.builder(chatModel).build();
	}
}
