package com.atguigu.study.controller;

import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author HuangChunXin
 * @date 2026/4/3 10:51
 */
@Log
@RestController
public class OllamaController {

	@Resource(name = "ollamaChatModel")
	private ChatModel chatModel;

	@GetMapping(value = "/ollama/doChat")
	public String doChat(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
		String result = chatModel.call(msg);
		log.info(result);
		return result;
	}

	@GetMapping(value = "/ollama/streamChat")
	public Flux<String> streamChat(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
		return chatModel.stream(msg);
	}
}
