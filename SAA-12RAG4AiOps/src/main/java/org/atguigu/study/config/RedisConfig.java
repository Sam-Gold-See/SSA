package org.atguigu.study.config;

import com.alibaba.cloud.ai.memory.redis.JedisRedisChatMemoryRepository;
import jakarta.annotation.Resource;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPooled;

/**
 * @author HuangChunXin
 * @date 2026/4/6 19:44
 */
@Configuration
public class RedisConfig {

	@Resource(name = "aLiEmbeddingModel")
	private EmbeddingModel aLiEmbeddingModel;

	@Value("${spring.data.redis.host}")
	private String host;

	@Value("${spring.data.redis.port}")
	private int port;

	@Value("${spring.ai.vectorstore.redis.initialize-schema}")
	private Boolean initializeSchema;

	@Value("${spring.ai.vectorstore.redis.index-name}")
	private String indexName;

	@Value("${spring.ai.vectorstore.redis.prefix}")
	private String prefix;

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

		redisTemplate.setConnectionFactory(redisConnectionFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

		redisTemplate.afterPropertiesSet();

		return redisTemplate;
	}

	@Bean
	public JedisRedisChatMemoryRepository jedisRedisChatMemoryRepository() {
		return JedisRedisChatMemoryRepository.builder()
				.host(host)
				.port(port)
				.build();
	}

	@Bean
	public VectorStore redisVectorStore() {
		return RedisVectorStore.builder(new JedisPooled(new HostAndPort(host, port)), aLiEmbeddingModel)
				.initializeSchema(initializeSchema)
				.indexName(indexName)
				.prefix(prefix)
				.build();
	}
}
