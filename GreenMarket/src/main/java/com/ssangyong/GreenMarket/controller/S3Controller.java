package com.ssangyong.GreenMarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ssangyong.GreenMarket.service.S3Uploader;

import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class S3Controller {
	private final S3Uploader s3Uploader;
	
	/**
	 * S3에 업로드
	 * @param multipartFile
	 * @param path
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/upload")
	@ResponseBody
	public String upload(@RequestParam("data") MultipartFile multipartFile, @RequestParam("path") String path) throws IOException {
		return s3Uploader.upload(multipartFile, path);
	}

}
