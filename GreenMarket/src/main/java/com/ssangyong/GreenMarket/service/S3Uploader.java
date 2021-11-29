package com.ssangyong.GreenMarket.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {
	private final AmazonS3Client amazonS3Client;
	// 버킷 이름 동적 할당
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;
	
	// multipartFile을 file로 변경해주고 s3 업로드
	public String upload(MultipartFile multipartFile, String dirName) throws IOException {
		File uploadFile = convert(multipartFile)
				.orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));
		return upload(uploadFile, dirName);
	}

	private String upload(File uploadFile, String dirName) {
		String fileName = dirName + "/" + uploadFile.getName();
		String uploadImageUrl = putS3(uploadFile, fileName);
		removeNewFile(uploadFile); // 로컬 directory에 저장 된 파일 삭제
		return uploadImageUrl; // 업로드된 파일의 S3 url 주소 반환
	}

	private String putS3(File uploadFile, String fileName) {
		amazonS3Client.putObject(
				new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
		return amazonS3Client.getUrl(bucket, fileName).toString();
	}

	private void removeNewFile(File targetFile) {
		if (targetFile.delete()) {
			log.info("파일이 삭제되었습니다.");
		} else {
			log.info("파일이 삭제되지 못했습니다.");
		}
	}

	private Optional<File> convert(MultipartFile file) throws IOException {
		File convertFile = new File(file.getOriginalFilename());
		if (convertFile.createNewFile()) {
			try (FileOutputStream fos = new FileOutputStream(convertFile)) {
				fos.write(file.getBytes());
			}
			return Optional.of(convertFile);
		}
		return Optional.empty();
	}
}
