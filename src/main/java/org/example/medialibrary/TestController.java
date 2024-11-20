package org.example.medialibrary;

import org.example.medialibrary.configuration.AppPropertiesConfiguration;
import org.example.medialibrary.service.FilmComplaintService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping()
public class TestController {

    @Autowired
    private FilmComplaintService filmComplaintService;

    @Autowired
    private AppPropertiesConfiguration appPropertiesConfiguration;

    @GetMapping("/t")
    public String index() {

        return "index";
    }

    @GetMapping("/performAction")
    public String showAction() {
        return "performAction";
    }

    @PostMapping("/performAction")
    @ResponseBody
    public void performAction() {
        // Выполняем любое необходимое действие
        System.out.println("Action performed!");

        filmComplaintService.filmComplaint(FilmComplaintService.FILM_COMPLAINT, 2L, 13L);


    }

    // Метод для отображения страницы `anotherPage`
    @GetMapping("/anotherPage")
    public String anotherPage() {
        return "index";  // Возвращаем страницу назначения
    }


}
