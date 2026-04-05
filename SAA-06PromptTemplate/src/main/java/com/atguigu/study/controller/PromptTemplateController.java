package com.atguigu.study.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

/**
 * @author HuangChunXin
 * @date 2026/4/5 15:33
 */
@RestController
public class PromptTemplateController {

	@Resource(name = "geminiChatModel")
	private ChatModel geminiChatModel;

	@Resource(name = "glmChatModel")
	private ChatModel glmChatModel;

	@Resource(name = "geminiChatClient")
	private ChatClient geminiChatClient;

	@Resource(name = "glmChatClient")
	private ChatClient glmChatClient;

	@Value("classpath:/promptTemplate/template.txt")
	private org.springframework.core.io.Resource userTemplate;

	@GetMapping("/promptTemplate/chat")
	public Flux<String> chat(String topic, String outputFormat, String wordCount) {
		PromptTemplate promptTemplate = new PromptTemplate("""
				将一个关于{topic}的故事
				并以{outputFormat}格式输出，
				字数在{wordCount}左右
				""");

		Prompt prompt = promptTemplate.create(Map.of(
				"topic", topic,
				"outputFormat", outputFormat,
				"wordCount", wordCount
		));

		return geminiChatClient.prompt(prompt)
				.stream()
				.content();
	}

	@GetMapping("/promptTemplate/chat2")
	public Flux<String> chat2(String topic, String outputFormat) {
		PromptTemplate promptTemplate = new PromptTemplate(userTemplate);

		Prompt prompt = promptTemplate.create(Map.of(
				"topic", topic,
				"outputFormat", outputFormat
		));

		return geminiChatClient.prompt(prompt)
				.stream()
				.content();
	}

	@GetMapping("/promptTemplate/chat3")
	public Flux<String> chat3(String systemTopic, String userTopic) {

		SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate("你是{systemTopic}助手，以HTML格式输出结果，只回答{systemTopic}其他无可奉告");
		Message systemMessage = systemPromptTemplate.createMessage(Map.of("systemTopic", systemTopic));

		PromptTemplate userPromptTemplate = new PromptTemplate("解释一下{userTopic}");
		Message userMessage = userPromptTemplate.createMessage(Map.of("userTopic", userTopic));

		return geminiChatClient.prompt(Prompt.builder()
						.messages(List.of(systemMessage, userMessage))
						.build())
				.stream()
				.content();
	}

	@GetMapping("/promptTemplate/chat4")
	public String chat4(String question) {

		SystemMessage systemMessage = new SystemMessage("你是一个Java编程助手，拒绝回答非技术问题。");

		UserMessage userMessage = new UserMessage(question);

		Prompt prompt = new Prompt(List.of(systemMessage, userMessage));

		return geminiChatModel.call(prompt).getResult().getOutput().getText();
	}

	@GetMapping("/promptTemplate/chat5")
	public Flux<String> chat5(String question) {

		return geminiChatClient.prompt()
				.system("你是一个Java编程助手，拒绝回答非技术问题。")
				.user(question)
				.stream()
				.content();
	}
}
