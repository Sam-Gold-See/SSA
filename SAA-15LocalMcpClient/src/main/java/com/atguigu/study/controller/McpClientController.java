package com.atguigu.study.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author HuangChunXin
 * @date 2026/4/7 16:39
 */
@RestController
public class McpClientController {

	@Resource(name = "geminiChatClient")
	private ChatClient geminiChatClient;

	@GetMapping("/mcpClient/chat")
	public Flux<String > chat(@RequestParam(name = "msg", defaultValue = "北京") String msg) {
		return geminiChatClient.prompt(msg)
				.stream()
				.content();
	}
}
