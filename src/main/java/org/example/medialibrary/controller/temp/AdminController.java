package org.example.medialibrary.controller.temp;

import org.example.medialibrary.entity.Picture;
import org.example.medialibrary.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.net.URLConnection;
import java.util.Optional;

@Controller
@RequestMapping("/admin/image")
public class AdminController {

    @Autowired
    private PictureService pictureService;

    @GetMapping("/upload")
    public String showUploadForm() {
        return "upload-form"; // шаблон для формы загрузки
    }

    // Метод для загрузки нового изображения
    @PostMapping("/upload")
    public String uploadPicture(@RequestParam("file") MultipartFile file,
                                @RequestParam("name") String name, Model model) {
        try {
            pictureService.savePicture(file, name);
            model.addAttribute("message", "Image uploaded successfully!");
        } catch (Exception e) {
            model.addAttribute("message", "Image upload failed: " + e.getMessage());
        }
        return "upload-result"; // шаблон для показа результата загрузки
    }

    // Метод для отображения изображения по ID
    @GetMapping("/view/{id}")
    public String viewPictureById(@PathVariable Long id, Model model) {
        Optional<Picture> pictureOptional = pictureService.getPictureById(id);
        if (pictureOptional.isPresent()) {
            Picture picture = pictureOptional.get();
            String mimeType = getMimeType(picture.getData());
            String base64Image = pictureService.getBase64Image(picture);
            model.addAttribute("image", base64Image);
            model.addAttribute("mimeType", mimeType);
            model.addAttribute("name", picture.getName());
        } else {
            model.addAttribute("message", "Image not found");
        }
        return "view-picture"; // шаблон для отображения изображения
    }

    // Метод для отображения изображения по имени
    @GetMapping("/view/name/{name}")
    public String viewPictureByName(@PathVariable String name, Model model) {
        Optional<Picture> pictureOptional = pictureService.getPictureByName(name);
        if (pictureOptional.isPresent()) {
            Picture picture = pictureOptional.get();
            String mimeType = getMimeType(picture.getData());
            String base64Image = pictureService.getBase64Image(picture);
            model.addAttribute("image", base64Image);
            model.addAttribute("mimeType", mimeType);
            model.addAttribute("name", picture.getName());
        } else {
            model.addAttribute("message", "Image not found");
        }
        return "view-picture"; // шаблон для отображения изображения
    }

    // Метод для определения MIME-типа изображения
    private String getMimeType(byte[] data) {
        try {
            return URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(data));
        } catch (Exception e) {
            return "application/octet-stream";
        }
    }
}
