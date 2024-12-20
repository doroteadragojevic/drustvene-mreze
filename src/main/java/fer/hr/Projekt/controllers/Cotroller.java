package fer.hr.Projekt.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Cotroller {

    @GetMapping("/hello")
    public String hello(Model model, Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
            model.addAttribute("username", oauthUser.getAttribute("name"));
        }
        return "hello";
    }
}
