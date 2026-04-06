package org.atguigu.study.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HuangChunXin
 * @date 2026/4/6 14:34
 */
@RestController
public class Text2ImageController {

	@Resource(name = "geminiImageModel")
	ImageModel geminiImageModel;

	@GetMapping("/t2i/image")
	public String image(@RequestParam(name = "prompt", defaultValue = "刺猬") String prompt) {
		return geminiImageModel.call(new ImagePrompt(prompt))
				.getResult()
				.getOutput()
				.getUrl();
	}
}
