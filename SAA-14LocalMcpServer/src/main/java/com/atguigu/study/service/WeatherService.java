package com.atguigu.study.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author HuangChunXin
 * @date 2026/4/7 16:08
 */
@Service
public class WeatherService {

	@Tool(description = "根据城市名称获取天气预报")
	public String getWeatherByCity(String city) {
		Map<String, String> map = Map.of(
				"北京","11111",
				"上海","22222",
				"深圳","33333"
		);
		return map.getOrDefault(city, "00000");
	}
}
