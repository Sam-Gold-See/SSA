package org.atguigu.study.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author HuangChunXin
 * @date 2026/4/6 16:57
 */
@RestController
public class Embed2VectorController {

	@Resource(name = "aLiEmbeddingModel")
	private EmbeddingModel embeddingModel;

	@Resource
	private VectorStore vectorStore;

	@GetMapping("/text2Embed")
	public EmbeddingResponse text2Embed(String msg) {
		EmbeddingResponse embeddingResponse = embeddingModel.embedForResponse(List.of(msg));

		System.out.println(Arrays.toString(embeddingResponse.getResult().getOutput()));

		return embeddingResponse;
	}

	@GetMapping("/embed2Vector/add")
	public void add() {
		List<Document> documents = List.of(new Document("I study LLM"), new Document("I love Java"));
		vectorStore.add(documents);
	}

	@GetMapping("/embed2Vector/getAll")
	public List<Document> getAll(String msg) {
		List<Document> documents = vectorStore.similaritySearch(SearchRequest.builder()
				.query(msg)
				.topK(2)
				.build());

		System.out.println(documents);

		return documents;
	}
}
