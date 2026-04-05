package org.atguigu.study.config;

import com.alibaba.cloud.ai.memory.redis.JedisRedisChatMemoryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author HuangChunXin
 * @date 2026/4/5 23:54
 */
@Configuration
public class RedisMemoryConfig {

	@Value("${spring.data.redis.host}")
	private String host;

	@Value("${spring.data.redis.port}")
	private int port;

	@Bean
	public JedisRedisChatMemoryRepository jedisRedisChatMemoryRepository() {
		return JedisRedisChatMemoryRepository.builder()
				.host(host)
				.port(port)
				.build();
	}
}
