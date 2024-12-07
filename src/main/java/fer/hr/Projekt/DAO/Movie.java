package fer.hr.Projekt.DAO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
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
}
