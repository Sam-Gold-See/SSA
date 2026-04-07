package com.atguigu.study.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author HuangChunXin
 * @date 2026/4/3 15:24
 */
@Configuration
public class SaaLLMConfig {

	private final String GEMINI_CHAT_MODEL = "gemini-3.0-flash-preview";

	@Value("${spring.ai.openai.api-key}")
	private String qiNiuApiKey;

	@Value("${spring.ai.openai.base-url}")
	private String baseUrl;

	@Bean("geminiChatModel")
	public ChatModel geminiChatModel() {
		return OpenAiChatModel.builder()
				.openAiApi(OpenAiApi.builder()
						.apiKey(qiNiuApiKey)
						.baseUrl(baseUrl)
						.build())
				.defaultOptions(OpenAiChatOptions.builder()
						.model(GEMINI_CHAT_MODEL)
						.build())
				.build();
	}

	@Bean("geminiChatClient")
	public ChatClient geminiChatClient(@Qualifier("geminiChatModel") ChatModel gemini, ToolCallbackProvider tools) {
		return ChatClient.builder(gemini)
				.defaultOptions(ChatOptions.builder()
						.model(GEMINI_CHAT_MODEL)
						.build())
				.defaultToolCallbacks(tools)
				.build();
	}
}
