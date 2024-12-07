package fer.hr.Projekt.services;

import fer.hr.Projekt.DAO.User;
import fer.hr.Projekt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void addFavorite(String tmdbId, String email) {
        User user = userRepository.findById(email).orElseThrow(NoSuchElementException::new);
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

}
