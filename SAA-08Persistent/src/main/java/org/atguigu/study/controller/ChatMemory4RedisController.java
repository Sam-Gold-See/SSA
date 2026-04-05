package org.atguigu.study.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

/**
 * @author HuangChunXin
 * @date 2026/4/6 00:11
 */
@RestController
public class ChatMemory4RedisController {

	@Resource(name = "geminiChatClient")
	private ChatClient geminiChatClient;

	@GetMapping("/chatMemory/chat")
	public String chat(String msg, String userId) {
		return geminiChatClient.prompt(msg)
				.advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID, userId))
				.call()
				.content();
	}
}
