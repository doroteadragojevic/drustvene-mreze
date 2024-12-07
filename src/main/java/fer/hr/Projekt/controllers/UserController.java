package fer.hr.Projekt.controllers;

import fer.hr.Projekt.DAO.Movie;
import fer.hr.Projekt.services.MovieService;
import fer.hr.Projekt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/user/add/{id}")
    public RedirectView addFavorite(@PathVariable String id, @AuthenticationPrincipal OAuth2User principal){
        if(principal != null) {
            userService.addFavorite(id, principal.getAttribute("email"), principal.getName());
        }
        return new RedirectView("/");
    }

    @PostMapping("/user/remove/{id}")
    public RedirectView removeFavorite(@PathVariable String id, @AuthenticationPrincipal OAuth2User principal){
        if(principal != null) {
            userService.removeFavorite(id, principal.getAttribute("email"));
        }
        return new RedirectView("/favorites");
    }

    @GetMapping("/favorites")
    public String getFavorites( @AuthenticationPrincipal OAuth2User principal, Model model){
        if(principal != null) {
            model.addAttribute("auth", true);
            model.addAttribute("movies",userService.getFavorites(principal.getAttribute("email")));
            return "favorites";
        }
        return null;
    }
}
