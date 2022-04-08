package com.example.todayError.controller;

import com.example.todayError.service.AwsS3Service;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Controller
@RequestMapping("/s3")
public class AmazonS3Controller {

    private final AwsS3Service awsS3Service;

    /**
     * Amazon S3에 파일 업로드
     * @return 성공 시 200 Success와 함께 업로드 된 파일의 파일명 리스트 반환
     */


    @ApiOperation(value = "파일 업로드", notes = "Amazon S3 파일 업로드")
    @PostMapping("/file")
    public ResponseEntity<String> uploadFile(@ApiParam(value="파일들(여러 파일 업로드 가능)") @RequestPart(value = "file") MultipartFile multipartFile) {
        return awsS3Service.uploadFile(multipartFile);
    }

    /**
     * Amazon S3에 업로드 된 파일을 삭제
     * @return 성공 시 200 Success
     */
    @ApiOperation(value = "파일 삭제", notes = "S3 업로드된 파일 삭제")
    @DeleteMapping("/file")
    public ResponseEntity<String> deleteFile(@ApiParam(value="파일 하나 삭제", required = true) @RequestParam String fileName) {
        return new ResponseEntity<>(awsS3Service.deleteFile(fileName) , HttpStatus.OK);
    }
}
