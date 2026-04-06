package org.atguigu.study.config;

import com.alibaba.cloud.ai.memory.redis.JedisRedisChatMemoryRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;

/**
 * @author HuangChunXin
 * @date 2026/4/3 15:24
 */
@Configuration
public class SaaLLMConfig {

	private final String GEMINI_CHAT_MODEL = "gemini-3.0-flash-preview";
	private final String GLM_CHAT_MODEL = "z-ai/glm-5";
	private final String GEMINI_IMAGE_MODEL = "gemini-3.1-flash-image-preview";

	@Value("${spring.ai.openai.api-key}")
	private String apiKey;

	@Value("${spring.ai.openai.base-url}")
	private String baseUrl;

	@Bean("geminiChatModel")
	public ChatModel geminiChatModel() {
		return OpenAiChatModel.builder()
				.openAiApi(OpenAiApi.builder()
						.apiKey(apiKey)
						.baseUrl(baseUrl)
						.build())
				.defaultOptions(OpenAiChatOptions.builder()
						.model(GEMINI_CHAT_MODEL)
						.build())
				.build();
	}

	@Bean("geminiChatClient")
	public ChatClient geminiChatClient(@Qualifier("geminiChatModel") ChatModel gemini,
	                                   JedisRedisChatMemoryRepository jedisRedisChatMemoryRepository) {
		MessageWindowChatMemory messageWindowChatMemory = MessageWindowChatMemory.builder()
				.chatMemoryRepository(jedisRedisChatMemoryRepository)
				.maxMessages(10)
				.build();
		return ChatClient.builder(gemini)
				.defaultOptions(ChatOptions.builder()
						.model(GEMINI_CHAT_MODEL)
						.build())
				.defaultAdvisors(MessageChatMemoryAdvisor.builder(messageWindowChatMemory)
						.build())
				.build();
	}

	@Bean("glmChatModel")
	public ChatModel glmChatModel() {
		return OpenAiChatModel.builder()
				.openAiApi(OpenAiApi.builder()
						.apiKey(apiKey)
						.baseUrl(baseUrl)
						.build())
				.defaultOptions(OpenAiChatOptions.builder()
						.model(GLM_CHAT_MODEL)
						.build())
				.build();
	}

	@Bean("glmChatClient")
	public ChatClient glmChatClient(@Qualifier("glmChatModel") ChatModel glm,
	                                JedisRedisChatMemoryRepository jedisRedisChatMemoryRepository) {
		MessageWindowChatMemory messageWindowChatMemory = MessageWindowChatMemory.builder()
				.chatMemoryRepository(jedisRedisChatMemoryRepository)
				.build();
		return ChatClient.builder(glm)
				.defaultOptions(ChatOptions.builder()
						.model(GLM_CHAT_MODEL)
						.build())
				.defaultAdvisors(MessageChatMemoryAdvisor.builder(messageWindowChatMemory)
						.build())
				.build();
	}

	@Bean("geminiImageModel")
	public ImageModel geminiImageModel() {
		return new OpenAiImageModel(
				OpenAiImageApi.builder()
						.apiKey(apiKey)
						.baseUrl(baseUrl)
						.build(),
				OpenAiImageOptions.builder()
						.model(GEMINI_IMAGE_MODEL)
						.build(),
				RetryTemplate.defaultInstance());
	}
}
