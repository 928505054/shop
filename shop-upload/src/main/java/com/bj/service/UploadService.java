package com.bj.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

   String uploadImage(MultipartFile file);
}
