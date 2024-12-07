package fer.hr.Projekt.controllers;

import fer.hr.Projekt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user/add/{id}")
    public void addFavorite(@PathVariable String id, @AuthenticationPrincipal OAuth2User principal){
        if(principal != null) {
            userService.addFavorite(id, principal.getAttribute("email"));
        }
    }

    @GetMapping("/user/remove/{id}")
    public void removeFavorite(@PathVariable String id, @AuthenticationPrincipal OAuth2User principal){
        if(principal != null) {
            userService.removeFavorite(id, principal.getAttribute("email"));
        }
    }
}
