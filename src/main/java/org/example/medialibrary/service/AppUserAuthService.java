package org.example.medialibrary.service;

import org.example.medialibrary.entity.AppUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Optional;

@Service
public class AppUserAuthService {

    public Optional<AppUser> getAppUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof AppUser user) {
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    public void generatePublicHeaderData(Model model){
        model.addAttribute("isHome", true);

        Optional<AppUser> userOptional = getAppUser();
        if (userOptional.isPresent()) {
            AppUser user = userOptional.get();
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("userName", user.getUsername());
            model.addAttribute("userId", user.getId());
        } else {
            model.addAttribute("isLoggedIn", false);
        }
    }

    public boolean isUserPage(Long id) {
        Optional<AppUser> userOptional = getAppUser();
        if (userOptional.isPresent()) {
            AppUser user = userOptional.get();
            return user.getId().equals(id);
        }
        return false;
    }
}
