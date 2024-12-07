package fer.hr.Projekt.DAO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class Movie {

    @Id
    String id;
    String tmdbId;
    String title;
    String overview;
    List<String> genres;
    String trailerUrl;
    String posterPath;
    String backdropPath;

    public Movie(String id, String tmdbId, String title, String overview, String trailerUrl, String posterPath, String backdropPath) {
        this.id = id;
        this.tmdbId = tmdbId;
        this.title = title;
        this.overview = overview;
        this.trailerUrl = trailerUrl;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.genres = new ArrayList<>();
    }
}
