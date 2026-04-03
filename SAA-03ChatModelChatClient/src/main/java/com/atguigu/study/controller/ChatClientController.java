package com.atguigu.study.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HuangChunXin
 * @date 2026/4/3 12:21
 */
@RestController
public class ChatClientController {

	private final ChatClient openAiChatClient;

	public ChatClientController(ChatModel chatModel) {
		this.openAiChatClient = ChatClient.builder(chatModel).build();
	}

	@GetMapping("chatClient/doChat")
	public String doChat(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
		return openAiChatClient.prompt()
				.user(msg)
				.call()
				.content();
	}
}
