package org.atguigu.study;

import org.springframework.ai.tool.annotation.Tool;

import java.time.LocalDateTime;

/**
 * @author HuangChunXin
 * @date 2026/4/6 22:39
 */
public class DateTimeTools {

	@Tool(description = "获取当前时间", returnDirect = false)
	public String getCurrentTime() {
		return LocalDateTime.now().toString();
	}
}
