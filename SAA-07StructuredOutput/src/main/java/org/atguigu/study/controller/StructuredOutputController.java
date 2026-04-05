package org.atguigu.study.controller;

import jakarta.annotation.Resource;
import org.atguigu.study.record.StudentRecord;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author HuangChunXin
 * @date 2026/4/5 20:59
 */
@RestController
public class StructuredOutputController {

	@Resource(name = "geminiChatClient")
	private ChatClient geminiChatClient;

	@GetMapping("/structuredOutput/chat")
	public StudentRecord chat(String name, String email) {
		return geminiChatClient.prompt()
				.user(promptUserSpec -> promptUserSpec.text("学号1001，我叫{name}，大学专业为计算机科学与技术，邮箱为{email}")
						.param("name", name)
						.param("email", email))
				.call()
				.entity(StudentRecord.class);
	}

	@GetMapping("/structuredOutput/chat2")
	public StudentRecord chat2(String name, String email) {
		String stringTemplate = """
				学号1001，我叫{name}，大学专业为软件工程，邮箱为{email}
				""";
		return geminiChatClient.prompt()
				.user(promptUserSpec -> promptUserSpec.text(stringTemplate)
						.params(Map.of("name", name, "email", email)))
				.call()
				.entity(StudentRecord.class);
	}
}
