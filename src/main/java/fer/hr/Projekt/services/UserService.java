package fer.hr.Projekt.services;

import fer.hr.Projekt.DAO.Movie;
import fer.hr.Projekt.DAO.User;
import fer.hr.Projekt.repository.UserRepository;
import net.jcip.annotations.Immutable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MovieService movieService;

    public void addFavorite(String tmdbId, String email, String name) {
        User user = null;
        try {
            user = userRepository.findById(email).get();
        } catch (NoSuchElementException e) {
            user = userRepository.save(new User(email, name, new HashSet<>()));
        }
        Set<String> favoriteMovies = user.getFavoriteMovies();
        favoriteMovies.add(tmdbId);
        user.setFavoriteMovies(favoriteMovies);
        userRepository.save(user);
    }

    public void removeFavorite(String tmdbId, String email) {
        User user = userRepository.findById(email).orElseThrow(NoSuchElementException::new);
        Set<String> favoriteMovies = user.getFavoriteMovies();
        favoriteMovies.remove(tmdbId);
        user.setFavoriteMovies(favoriteMovies);
        userRepository.save(user);
    }

    public List<Movie> getFavorites(String email) {
        User user = userRepository.findById(email).orElseThrow(NoSuchElementException::new);
        return movieService.fetchMovies(user.getFavoriteMovies());
    }
}
