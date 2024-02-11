package com.androidcorpo.filesupload.application.controller;

import com.androidcorpo.filesupload.application.model.FileInfo;
import com.androidcorpo.filesupload.domain.FileUploader;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Severin Mbekou <mbekou99@gmail.com>
 */
@Slf4j
@Controller
@CrossOrigin(origins = "http://localhost:8081")
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

  @GetMapping("/files")
  public ResponseEntity<List<FileInfo>> getListFiles() {
    List<FileInfo> fileInfos = new ArrayList<>();
    return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
  }

  @DeleteMapping("/files/{filename}")
  public ResponseEntity<String> deleteFile(@PathVariable String filename) {
    String message = "";
    
    try {
      String status = fileUploader.deleteFile(filename);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(status);
    } catch (Exception e) {
      message = "Could not delete the file: " + filename + ". Error: " + e.getMessage();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }
  }
}
