package org.example.medialibrary.controller;

import org.example.medialibrary.configuration.AppPropertiesConfiguration;
import org.example.medialibrary.entity.AppUser;
import org.example.medialibrary.entity.Film;
import org.example.medialibrary.entity.Genre;
import org.example.medialibrary.entity.Pack;
import org.example.medialibrary.entity.dto.PackDto;
import org.example.medialibrary.service.AppUserAuthService;
import org.example.medialibrary.service.ImageService;
import org.example.medialibrary.service.PackService;
import org.example.medialibrary.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private RatingService ratingService;

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

        return "user-pack-list";
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

    @GetMapping("/{id}")
    public String getPublicPackById(@PathVariable Long id, Model model) {

        appUserAuthService.generatePublicHeaderData(model);

        Optional<Pack> pack = packService.getPackById(id);
        Optional<AppUser> userOptional = appUserAuthService.getAppUser();
        if (pack.isPresent() && userOptional.isPresent()) {
            AppUser user = userOptional.get();
            Pack packObj = pack.get();

            if (!packService.isPackOwnedByUser(packObj.getId(), user.getId())) {
                return "error/403";
            }

            model.addAttribute("userId", user.getId());
            model.addAttribute("userName", user.getUsername());
            model.addAttribute("galleryTitle", packObj.getName());
            model.addAttribute("description", packObj.getDescription());
            model.addAttribute("packId", packObj.getId());
            model.addAttribute("averageRating", ratingService.getFilmPackRating(packObj.getLike(), packObj.getDislike()));
            model.addAttribute("backUrl", "/user/pack");
            model.addAttribute("films", packService.getFilmsFromPack(packObj));

            return "user-pack-view";
        }

        return "error/404";
    }

    @GetMapping("/edit/{id}")
    public String editFilmPage(@PathVariable Long id, Model model) {

        Optional<AppUser> userOptional = appUserAuthService.getAppUser();
        if (userOptional.isPresent()) {
            AppUser user = userOptional.get();

            Optional<Pack> optionalPack = packService.getPackById(id);
            if (optionalPack.isEmpty()) {
                return "error/404";
            }
            Pack pack = optionalPack.get();

            if(!packService.isPackOwnedByUser(pack.getId(), user.getId())) {
                return "error/403";
            }

            appUserAuthService.generatePublicHeaderData(model);


            model.addAttribute("packId", pack.getId());
            model.addAttribute("oldTitle", pack.getName());
            model.addAttribute("oldDescription", pack.getDescription());
            model.addAttribute("oldAccessibleToAll", pack.isAccessibleToAll());
            model.addAttribute("oldAccessibleToFriends", pack.isAccessibleToFriends());
            return "edit-pack";
        }

        return "error/something-went-wrong";

    }


    @PostMapping("/edit/{id}")
    public String editFilm(@PathVariable Long id,
                           @RequestParam("newTitle") String title,
                           @RequestParam("newDescription") String description,
                           @RequestParam(value = "newAccessibleToAll", defaultValue = "false") boolean accessibleToAll,
                           @RequestParam(value = "newAccessibleToFriends", defaultValue = "false") boolean accessibleToFriends,
                           @RequestParam(value = "newImage", required = false) MultipartFile imageFile) {

        Optional<Pack> optionalPack = packService.getPackById(id);
        if (optionalPack.isPresent()) {
            Pack pack = optionalPack.get();
            pack.setName(title);
            pack.setDescription(description);
            pack.setAccessibleToAll(accessibleToAll);
            pack.setAccessibleToFriends(accessibleToFriends);

            if (!imageFile.isEmpty()) {
                try {
                    pack.setImage(imageFile.getBytes());
                } catch (IOException e) {
                    pack.setImage(new byte[0]);
                }
            }

            packService.updatePack(id, pack);
        }
        return "redirect:/user/pack/" + id;

    }
}
