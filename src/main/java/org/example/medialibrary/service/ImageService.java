package org.example.medialibrary.service;

import org.example.medialibrary.entity.Picture;
import org.example.medialibrary.repository.PackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Optional;

@Component
public class ImageService {

    @Autowired
    private PictureService pictureService;

    public String getImageType(byte[] imageBytes){
        try {
            return URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(imageBytes));
        } catch (IOException e) {
            return "application/octet-stream";
        }
    }

    public byte[] getImageBytes(MultipartFile imageFile, String exceptionName){
        try {
            return imageFile.getBytes();
        } catch (Exception e) {
            Optional<Picture> pictureOptional = pictureService.getPictureByName(exceptionName);
            if(pictureOptional.isPresent()){
                return pictureOptional.get().getData();
            } else {
                return new byte[0];
            }
        }
    }

}
