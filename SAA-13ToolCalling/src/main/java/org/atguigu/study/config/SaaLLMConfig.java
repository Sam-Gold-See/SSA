package org.atguigu.study.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingModel;
import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingOptions;
import com.alibaba.cloud.ai.memory.redis.JedisRedisChatMemoryRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
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
	private final String GLM_CHAT_MODEL = "z-ai/glm-5";
	private final String ALI_EMBEDDING_MODEL = "text-embedding-v4";

	@Value("${spring.ai.openai.api-key}")
	private String qiNiuApiKey;

	@Value("${spring.ai.dashscope.api-key}")
	private String aLiApiKey;

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
						.apiKey(qiNiuApiKey)
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

	@Bean("aLiEmbeddingModel")
	public EmbeddingModel aLiEmbeddingModel() {
		return DashScopeEmbeddingModel.builder()
				.dashScopeApi(DashScopeApi.builder()
						.apiKey(aLiApiKey)
						.build())
				.defaultOptions(DashScopeEmbeddingOptions.builder()
						.model(ALI_EMBEDDING_MODEL)
						.build())
				.build();
	}
}
