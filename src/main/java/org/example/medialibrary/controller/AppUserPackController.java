package org.example.medialibrary.controller;

import org.example.medialibrary.configuration.AppPropertiesConfiguration;
import org.example.medialibrary.entity.AppUser;
import org.example.medialibrary.entity.Pack;
import org.example.medialibrary.entity.dto.PackDto;
import org.example.medialibrary.service.AppUserAuthService;
import org.example.medialibrary.service.ImageService;
import org.example.medialibrary.service.PackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user/pack")
public class AppUserPackController {

    @Autowired
    private PackService packService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private AppUserAuthService appUserAuthService;

    @Autowired
    private AppPropertiesConfiguration appPropertiesConfiguration;

    @GetMapping()
    public String showPacks(Model model, @RequestParam(defaultValue = "5") int rows) {

        appUserAuthService.generatePublicHeaderData(model);
        model.addAttribute("packTitle", appPropertiesConfiguration.getUserPackFilmListTitle());
        model.addAttribute("isPackCreate", true);
        model.addAttribute("rows", rows);

        Optional<AppUser> userOptional = appUserAuthService.getAppUser();
        if (userOptional.isPresent()) {
            AppUser user = userOptional.get();

            List<PackDto> packDtoList = packService.getAllAppUserPacksDto(user);
            model.addAttribute("packs", packDtoList);
        } else {
            model.addAttribute("packs", new ArrayList<PackDto>());
        }

        return "pack-list";
    }

    @GetMapping("/create")
    public String showCreatePackPage(Model model) {
        appUserAuthService.generatePublicHeaderData(model);
        return "create-pack";
    }

    @PostMapping("/create")
    public String createPack(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam(value = "accessibleToAll", defaultValue = "false") boolean accessibleToAll,
            @RequestParam(value = "accessibleToFriends", defaultValue = "false") boolean accessibleToFriends,
            @RequestParam("image") MultipartFile imageFile,
            Model model) {

        appUserAuthService.generatePublicHeaderData(model);
        Pack pack = new Pack(name, description, accessibleToAll, accessibleToFriends);
        pack.setImage(imageService.getImageBytes(imageFile, appPropertiesConfiguration.getNoImage()));

        Optional<AppUser> userOptional = appUserAuthService.getAppUser();
        if (userOptional.isPresent()) {
            AppUser user = userOptional.get();
            pack.setUser(user);

            packService.createPack(pack);

            return "redirect:/user/pack";
        }
        return "redirect:/";
    }
}
