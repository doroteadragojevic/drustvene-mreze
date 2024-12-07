package fer.hr.Projekt.services;

import fer.hr.Projekt.DAO.Movie;
import fer.hr.Projekt.repository.MovieRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MovieService {

    @Autowired
    YoutubeService youtubeService;

    @Autowired
    TMDBService tmdbService;

    @Autowired
    MovieRepository movieRepository;


    @PostConstruct
    public void populateDatabase() throws IOException {
        if (movieRepository.count() == 0) {
            List<String> categories = List.of("popular", "now_playing", "top_rated");
            for (String category : categories) {
                List<Movie> movies = tmdbService.fetchMovies(category);

                for (Movie movie : movies) {
                    if(!movieRepository.existsById(movie.getId())) {
                        String trailer = youtubeService.getTrailerForTitle(movie.getTitle());
                        movie.setTrailerUrl(trailer);
                        movieRepository.save(movie);
                    }
                }
            }
        }
    }

    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(String id) {
        return movieRepository.findById(id).get();
    }
}
