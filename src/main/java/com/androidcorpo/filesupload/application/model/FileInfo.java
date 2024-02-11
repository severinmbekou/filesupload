package com.androidcorpo.filesupload.application.model;

import lombok.Builder;

/**
 * @author Severin Mbekou <mbekou99@gmail.com>
 */
@Builder
public record FileInfo(
    String publicId, String originalFilename, String url, String format, String status) {}
