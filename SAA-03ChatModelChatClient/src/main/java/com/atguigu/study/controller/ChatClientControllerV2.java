package com.atguigu.study.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HuangChunXin
 * @date 2026/4/3 12:29
 */
@RestController
public class ChatClientControllerV2 {

	@Resource
	public ChatClient chatClient;

	@GetMapping("chatClientV2/doChat")
	public String doChat(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
		return chatClient.prompt()
				.user(msg)
				.call()
				.content();
	}
}
