package fer.hr.Projekt.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class Cotroller {

    @GetMapping("/")
    public String home() {
        return "home"; // Ovo odgovara home.html u templates direktoriju
    }

    @GetMapping("/hello")
    public String hello(Model model, Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
            model.addAttribute("username", oauthUser.getAttribute("name"));
        }
        return "hello";
    }
}
