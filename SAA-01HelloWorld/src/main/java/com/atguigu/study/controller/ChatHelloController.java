package com.atguigu.study.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author HuangChunXin
 * @date 2026/4/2 15:00
 */
@RestController
public class ChatHelloController {

	@Resource
	private ChatModel chatModel;

	@GetMapping(value = "/hello/doChat")
	public String doChat(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
		return chatModel.call(msg);
	}

	@GetMapping(value = "/hello/streamChat")
	public Flux<String> streamChat(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
		return chatModel.stream(msg);
	}
}
