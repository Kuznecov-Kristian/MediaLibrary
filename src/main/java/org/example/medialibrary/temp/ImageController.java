package org.example.medialibrary.temp;

import org.example.medialibrary.entity.Picture;
import org.example.medialibrary.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.io.IOException;
import java.net.URLConnection;

@Controller
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private PictureRepository pictureRepository;

    @GetMapping("/{id}")
    public String getImage(@PathVariable long id, Model model) throws IOException {
        // Получаем массив байт изображения (например, из базы данных или файла)
        // ваш код для загрузки изображения
        //byte[] imageBytes = Files.readAllBytes(Paths.get("src/main/resources/static/5287488729088387013.jpg"));

        Picture picture = pictureRepository.findById(id).orElse(null);
        byte[] imageBytes = picture.getData();



        // Определяем MIME-тип изображения
        String mimeType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(imageBytes));

        if (mimeType == null) {
            mimeType = "application/octet-stream"; // если MIME-тип не удалось определить, по умолчанию
        }

        // Кодируем изображение в Base64
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        // Передаём в модель MIME-тип и изображение в формате Base64
        model.addAttribute("image", base64Image);
        model.addAttribute("mimeType", mimeType);

        return "imagePage"; // имя вашего Thymeleaf-шаблона
    }


    // Метод для отображения страницы загрузки изображения
    @GetMapping("/upload")
    public String showUploadPage() {
        return "uploadPage"; // Имя Thymeleaf-шаблона для загрузки изображения
    }



    // Метод для загрузки изображения и получения его байтов
    @PostMapping("/upload")
    public String uploadImage(@RequestParam("imageFile") MultipartFile imageFile, Model model) {
        try {
            // Получаем массив байт из файла
            byte[] imageBytes = imageFile.getBytes();

            Picture picture = new Picture();
            picture.setData(imageBytes);
            pictureRepository.save(picture);

            // Кодируем байты в строку Base64 для отображения (по желанию)
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            String mimeType = imageFile.getContentType();

            // Добавляем изображение и MIME-тип в модель для отображения на странице
            model.addAttribute("image", base64Image);
            model.addAttribute("mimeType", mimeType);

        } catch (IOException e) {
            e.printStackTrace();
            // Добавляем сообщение об ошибке, если изображение не удалось загрузить
            model.addAttribute("errorMessage", "Failed to upload image. Please try again.");
        }

        return "imagePage"; // шаблон для отображения загруженного изображения
    }
}
