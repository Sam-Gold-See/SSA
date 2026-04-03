package com.atguigu.study.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author HuangChunXin
 * @date 2026/4/3 15:32
 */
@RestController
public class StreamOutputController {

	@Resource(name = "geminiChatModel")
	private ChatModel geminiChatModel;

	@Resource(name = "glmChatModel")
	private ChatModel glmChatModel;

	@Resource(name = "geminiChatClient")
	private ChatClient geminiChatClient;

	@Resource(name = "glmChatClient")
	private ChatClient glmChatClient;

	@GetMapping(value = "/stream/chatFlux1")
	public Flux<String> chatFlux1(@RequestParam(name = "question", defaultValue = "你是谁") String question) {
		return geminiChatModel.stream(question);
	}

	@GetMapping(value = "/stream/chatFlux2")
	public Flux<String> chatFlux2(@RequestParam(name = "question", defaultValue = "你是谁") String question) {
		return glmChatModel.stream(question);
	}

	@GetMapping(value = "/stream/chatFlux3")
	public Flux<String> chatFlux3(@RequestParam(name = "question", defaultValue = "你是谁") String question) {
		return geminiChatClient.prompt()
				.user(question)
				.stream()
				.content();
	}

	@GetMapping(value = "/stream/chatFlux4")
	public Flux<String> chatFlux4(@RequestParam(name = "question", defaultValue = "你是谁") String question) {
		return glmChatClient.prompt()
				.user(question)
				.stream()
				.content();
	}
}
