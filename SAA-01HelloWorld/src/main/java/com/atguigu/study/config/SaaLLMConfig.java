package com.atguigu.study.config;

import lombok.extern.java.Log;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author HuangChunXin
 * @date 2026/4/2 14:56
 */
@Configuration
@Log
public class SaaLLMConfig {

	@Value("${spring.ai.openai.api-key}")
	private String apiKey;

	@Value("${spring.ai.openai.base-url}")
	private String baseUrl;

	@Bean
	public OpenAiApi openAiApi() {
		return OpenAiApi.builder().apiKey(apiKey).baseUrl(baseUrl).build();
	}
}
