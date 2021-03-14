package org.softuni.lease1.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {
    String uploadOffer(MultipartFile multipartFile) throws IOException;
}
