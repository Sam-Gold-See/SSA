package com.atguigu.study.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author HuangChunXin
 * @date 2026/4/5 14:41
 */
@RestController
public class PromptController {

	@Resource(name = "geminiChatModel")
	private ChatModel geminiChatModel;

	@Resource(name = "glmChatModel")
	private ChatModel glmChatModel;

	@Resource(name = "geminiChatClient")
	private ChatClient geminiChatClient;

	@Resource(name = "glmChatClient")
	private ChatClient glmChatClient;

	@GetMapping("/prompt/chat")
	public Flux<String> chat(String question) {
		return geminiChatClient.prompt()
				// AI 能力边界
				.system("你是一个法律助手，只回答法律问题，关于其它问题只能回答：'我只能回答法律相关问题，其他无可奉告'")
				.user(question)
				.stream()
				.content();
	}

	@GetMapping("/prompt/chat2")
	public Flux<ChatResponse> chat2(String question) {
		SystemMessage systemMessage = new SystemMessage("你是一个讲故事的助手，每个故事控制在300字以内");
		UserMessage userMessage = new UserMessage(question);
		Prompt prompt = new Prompt(userMessage, systemMessage);
		return geminiChatModel.stream(prompt);
	}

	@GetMapping("/prompt/chat3")
	public Flux<String> chat3(String question) {
		SystemMessage systemMessage = new SystemMessage("你是一个讲故事的助手，每个故事控制在600字以内，并且以HTML格式返回");
		UserMessage userMessage = new UserMessage(question);
		Prompt prompt = new Prompt(userMessage, systemMessage);
		return geminiChatModel.stream(prompt)
				.map(chatResponse -> chatResponse.getResults().get(0).getOutput().getText());
	}

	@GetMapping("/prompt/chat4")
	public String chat4(String question) {
		return geminiChatClient.prompt()
				.user(question)
				.call()
				.chatResponse()
				.getResult()
				.getOutput()
				.getText();
	}

	@GetMapping("/prompt/chat5")
	public String chat5(String city) {
		String answer = geminiChatClient.prompt()
				.user(city + "未来3天天气情况如何")
				.call()
				.chatResponse()
				.getResult()
				.getOutput()
				.getText();

		ToolResponseMessage toolResponseMessage = ToolResponseMessage.builder()
				.responses(List.of(new ToolResponseMessage.ToolResponse("1", "获得天气", city)))
				.build();

		String toolResponse = toolResponseMessage.getText();

		return answer + toolResponse;
	}
}
