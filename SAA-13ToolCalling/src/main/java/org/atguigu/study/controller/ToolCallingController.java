package org.atguigu.study.controller;

import jakarta.annotation.Resource;
import org.atguigu.study.DateTimeTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HuangChunXin
 * @date 2026/4/6 22:39
 */
@RestController
public class ToolCallingController {

	@Resource(name = "geminiChatClient")
	private ChatClient geminiChatClient;

	@GetMapping("/toolCall/chat")
	public String chat(@RequestParam(name = "msg", defaultValue = "你是谁？现在是几点？") String msg) {
		return geminiChatClient.prompt()
				.user(msg)
				.options(ToolCallingChatOptions.builder()
						.toolCallbacks(ToolCallbacks.from(new DateTimeTools()))
						.build())
				.call()
				.content();
	}
}
