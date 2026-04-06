package org.atguigu.study.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author HuangChunXin
 * @date 2026/4/6 19:57
 */
@RestController
public class RagController {

	@Resource(name = "geminiChatClient")
	private ChatClient geminiChatClient;

	@Resource
	private VectorStore vectorStore;

	@GetMapping("/rag")
	public Flux<String> rag(String msg) {
		String systemInfo = """
				你是一个运维工程师，按照给出的编码回答对应故障解释，否则回复找不到信息
				""";

		RetrievalAugmentationAdvisor advisor = RetrievalAugmentationAdvisor.builder()
				.documentRetriever(VectorStoreDocumentRetriever.builder()
						.vectorStore(vectorStore)
						.build())
				.build();

		return geminiChatClient.prompt()
				.system(systemInfo)
				.user(msg)
				.advisors(advisor)
				.stream()
				.content();
	}
}
