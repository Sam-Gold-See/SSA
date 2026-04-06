package org.atguigu.study.config;

import jakarta.annotation.Resource;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPooled;

/**
 * @author HuangChunXin
 * @date 2026/4/6 17:25
 */
@Configuration
public class RedisVectorStoreConfig {

	@Resource(name = "aLiEmbeddingModel")
	private EmbeddingModel aLiEmbeddingModel;

	@Value("${spring.ai.vectorstore.redis.initialize-schema}")
	private Boolean initializeSchema;

	@Value("${spring.ai.vectorstore.redis.index-name}")
	private String indexName;

	@Value("${spring.ai.vectorstore.redis.prefix}")
	private String prefix;

	@Value("${spring.data.redis.host}")
	private String host;

	@Value("${spring.data.redis.port}")
	private int port;

	@Bean
	public VectorStore redisVectorStore() {
		return RedisVectorStore.builder(new JedisPooled(new HostAndPort(host, port)), aLiEmbeddingModel)
				.initializeSchema(initializeSchema)
				.indexName(indexName)
				.prefix(prefix)
				.build();
	}
}
