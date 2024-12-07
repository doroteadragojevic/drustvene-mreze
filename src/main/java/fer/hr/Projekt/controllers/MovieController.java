package fer.hr.Projekt.controllers;

import fer.hr.Projekt.DAO.Movie;
import fer.hr.Projekt.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class MovieController {

    @Autowired
    MovieService movieService;

    @RequestMapping("/")
    public String home(Model model, @AuthenticationPrincipal OAuth2User principal) {
        List<Movie> movies = movieService.getMovies();
        model.addAttribute("movies", movies);
        model.addAttribute("auth", false);
        if(principal != null) {
            model.addAttribute("auth", true);
        }
        return "hello";
    }

    @GetMapping("/movie/{id}")
    public String movie(Model model, @PathVariable String id){
        Movie movie = null;
        try {
            movie = movieService.getMovieById(id);
        } catch (NoSuchElementException e) {
        }
        model.addAttribute("movie", movie);
        return "movie";
    }
}
