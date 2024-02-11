package com.androidcorpo.filesupload.infrastructure.service;

import com.androidcorpo.filesupload.application.model.FileInfo;
import com.androidcorpo.filesupload.domain.FileUploader;
import com.cloudinary.Cloudinary;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Severin Mbekou <mbekou99@gmail.com>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CloudinaryFileUploader implements FileUploader {
  private final Cloudinary cloudinary;
  @Override
  public FileInfo uploadFile(MultipartFile multipartFile) throws IOException {
    var publicId = UUID.randomUUID().toString();
    Map<String, String> uploaded =
        cloudinary.uploader().upload(multipartFile.getBytes(), Map.of("public_id", publicId));
    log.info("Uploaded the file successfully: " + publicId);
    return FileInfo.builder()
        .originalFilename(uploaded.get("url"))
        .url(uploaded.get("secure_url"))
        .format(uploaded.get("format"))
        .publicId(uploaded.get("public_id"))
        .status("uploaded")
        .build();
  }

  @Override
  public String deleteFile(String publicId) throws IOException {
    Map<String, String> map = cloudinary.uploader().destroy(publicId, null);
    return map.get("result");
  }

  @Override
  public String updateFile(MultipartFile multipartFile) throws Exception {
    return null;
  }
}
