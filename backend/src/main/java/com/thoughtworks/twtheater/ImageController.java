package com.thoughtworks.twtheater;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Controller
public class ImageController {
    @GetMapping(value = "/poster/{id}")
    public ResponseEntity<byte[]> getPoster(@PathVariable String id) throws IOException {
        return getImage(String.format("/static/posters/%s.jpg", id));
    }

    @GetMapping(value = "/photo/{id}/{serial}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable String id, @PathVariable String serial) throws IOException {
        String imagePath = String.format("/static/photos/%s-%s.jpg", id, serial);
        return getImage(imagePath);
    }

    private static ResponseEntity<byte[]> getImage(String imagePath) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS));
        byte[] image = StreamUtils.copyToByteArray(
                new ClassPathResource(imagePath).getInputStream());
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }
}
