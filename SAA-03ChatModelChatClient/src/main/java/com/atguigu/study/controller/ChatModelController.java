package com.atguigu.study.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HuangChunXin
 * @date 2026/4/3 11:36
 */
@RestController
public class ChatModelController {

	@Resource
	private ChatModel chatModel;

	private final ChatClient chatClient;

	public ChatModelController(ChatClient.Builder chatClientBuilder) {
		this.chatClient = chatClientBuilder.build();
	}

	@GetMapping("chatModel/modelDoChat")
	public String chatModelDoChat(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
		return chatModel.call(msg);
	}

	@GetMapping("chatModel/clientDoChat")
	public String chatClientDoChat(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
		return chatClient.prompt()
				.user(msg)
				.call()
				.content();
	}
}
