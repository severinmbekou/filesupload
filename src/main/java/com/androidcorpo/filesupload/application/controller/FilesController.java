package com.androidcorpo.filesupload.application.controller;

import com.androidcorpo.filesupload.application.model.FileInfo;
import com.androidcorpo.filesupload.domain.FileUploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Severin Mbekou <mbekou99@gmail.com>
 */
@Slf4j
@Controller
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class FilesController {

  private final FileUploader fileUploader;

  @PostMapping("/upload")
  public ResponseEntity<FileInfo> uploadFile(@RequestParam("file") MultipartFile file) {
    try {
      FileInfo uploadedFile = fileUploader.uploadFile(file);
      return ResponseEntity.status(HttpStatus.OK).body(uploadedFile);
    } catch (Exception e) {
      log.error(
          "Could not upload the file: "
              + file.getOriginalFilename()
              + ". Error: "
              + e.getMessage());
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
          .body(FileInfo.builder().status("error").build());
    }
  }

  @DeleteMapping("/files/{filename}")
  public ResponseEntity<String> deleteFile(@PathVariable String filename) {
    try {
      String status = fileUploader.deleteFile(filename);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(status);
    } catch (Exception e) {
      var message = "Could not delete the file: " + filename + ". Error: " + e.getMessage();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }
  }
}
