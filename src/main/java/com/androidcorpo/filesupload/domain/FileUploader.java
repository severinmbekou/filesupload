package com.androidcorpo.filesupload.domain;

import com.androidcorpo.filesupload.application.model.FileInfo;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Severin Mbekou <mbekou99@gmail.com>
 */
public interface FileUploader {
  FileInfo uploadFile(MultipartFile multipartFile) throws IOException;

  String deleteFile(String fileName) throws IOException;

  String updateFile(MultipartFile multipartFile) throws Exception;
}
