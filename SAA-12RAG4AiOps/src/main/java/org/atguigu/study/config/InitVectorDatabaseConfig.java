package org.atguigu.study.config;

import cn.hutool.crypto.SecureUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.util.List;

import static org.springframework.ai.reader.TextReader.SOURCE_METADATA;

/**
 * @author HuangChunXin
 * @date 2026/4/6 19:52
 */
@Slf4j
@Configuration
public class InitVectorDatabaseConfig {

	@Resource
	private VectorStore vectorStore;

	@Resource
	private RedisTemplate<String, String> redisTemplate;

	@Value("classpath:ops.txt")
	private org.springframework.core.io.Resource opsFile;

	@PostConstruct
	public void init() {
		TextReader textReader = new TextReader(opsFile);
		textReader.setCharset(Charset.defaultCharset());

		List<Document> list = new TokenTextSplitter().transform(textReader.read());

		String sourceMetadata = (String) textReader.getCustomMetadata().get(SOURCE_METADATA);

		String textHash = SecureUtil.md5(sourceMetadata);
		String redisKey = "vector:" + textHash;

		Boolean returnFlag = redisTemplate.opsForValue().setIfAbsent(redisKey, "1");

		if (Boolean.TRUE.equals(returnFlag)) {
			vectorStore.add(list);
		} else {
			log.warn("向量初始化数据已加载，无需重复加载");
		}

		log.info("向量数据初始化成功");
	}
}
