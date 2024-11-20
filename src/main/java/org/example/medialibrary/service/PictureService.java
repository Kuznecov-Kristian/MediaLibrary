package org.example.medialibrary.service;

import jakarta.transaction.Transactional;
import org.example.medialibrary.entity.Picture;
import org.example.medialibrary.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Service
@Transactional
public class PictureService {
    @Autowired
    private PictureRepository pictureRepository;

    // Метод для сохранения изображения
    public Picture savePicture(MultipartFile file, String name) throws IOException {
        Picture picture = new Picture();
        picture.setName(name);
        picture.setData(file.getBytes());
        return pictureRepository.save(picture);
    }

    // Метод для получения изображения по ID
    public Optional<Picture> getPictureById(Long id) {
        return pictureRepository.findById(id);
    }

    // Метод для получения изображения по имени
    public Optional<Picture> getPictureByName(String name) {
        return pictureRepository.findByName(name);
    }

    // Метод для получения Base64 представления изображения
    public String getBase64Image(Picture picture) {
        return Base64.getEncoder().encodeToString(picture.getData());
    }
}
