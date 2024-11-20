package org.example.medialibrary.controller;

import org.example.medialibrary.service.AppUserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class AppUserController {

    @Autowired
    private AppUserAuthService appUserAuthService;

    @GetMapping("/{id}")
    public String user(@PathVariable long id, Model model) {
        if(appUserAuthService.isUserPage(id)){
            appUserAuthService.generatePublicHeaderData(model);

            return "user";
        } else {
            return "error/403";
        }
    }




    @GetMapping("/pack/{id}")
    public String pack(@PathVariable long id) {
        return "pack-view";
    }

    @GetMapping("/pack/edit/{id}")
    public String packEdit(@PathVariable long id) {
        return "edit-pack";
    }


}
