package com.ssangyong.GreenMarket.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ssangyong.GreenMarket.model.ItemPhotoEntity;
import com.ssangyong.GreenMarket.service.ItemPhotoService;
import com.ssangyong.GreenMarket.service.ItemService;
import com.ssangyong.GreenMarket.service.S3Uploader;

import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
public class S3Controller {
	private final S3Uploader s3Uploader;
	
	@Autowired
	ItemPhotoService itemPhotoService;
	@Autowired
	ItemService itemService;
	
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
	
	@ResponseBody
	@PostMapping("/file-upload")
	public String fileUpload(@RequestParam("article_file") List<MultipartFile> multipartFile, @RequestParam("iId")Integer iId, HttpServletRequest request) {
		String strResult = "{ \"result\":\"FAIL\" }";
		String fileRoot = "item";
		try {
			// 파일이 있을때 탄다.
			if(multipartFile.size() > 0 && !multipartFile.get(0).getOriginalFilename().equals("")) {
				
				for(MultipartFile file:multipartFile) {
					String originalFileName = file.getOriginalFilename();	//오리지날 파일명
					String savedFileName = s3Uploader.upload(file, fileRoot);
					ItemPhotoEntity itemPhotoEntity= ItemPhotoEntity.builder()
							.ipOfilename(originalFileName)
							.ipFilename(savedFileName)
							.item(itemService.selectById(iId))
							.build();
					
					itemPhotoService.insertItemPhoto(itemPhotoEntity);
				}
				strResult = "{ \"result\":\"OK\" }";
			}
			// 파일 아무것도 첨부 안했을때 탄다.(게시판일때, 업로드 없이 글을 등록하는경우)
			else
				strResult = "{ \"result\":\"OK\" }";
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return strResult;
	}

}
