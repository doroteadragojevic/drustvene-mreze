package fer.hr.Projekt.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import fer.hr.Projekt.DAO.Movie;
import fer.hr.Projekt.DTO.MovieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class TMDBService {

    @Autowired
    GenreService genreService;

    private static final String TMDB_URL = "https://api.themoviedb.org/3/";



    public List<Movie> fetchMovies(String category) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(TMDB_URL + "movie/" + category)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3MDM4YjhlMjI1MTEzZWExNzllNDAxNzQ4NTRlNGVjYyIsIm5iZiI6MTY1MTkzODYyOS4zMiwic3ViIjoiNjI3Njk1NDVmOTBiMTkwMGE2MjA2OWI2Iiwic2NvcGVzIjpbImFwaV9yZWFkIl0sInZlcnNpb24iOjF9.7pO7H_pRroqtru_OvEkxq-WLkKdy37t_bDzYeeZ0WUY")
                .build();

        Response response = client.newCall(request).execute();
        return parseMovies(response);
    }

    private List<Movie> parseMovies(Response response){
        try {
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                String json = responseBody.string(); // JSON kao String
                ObjectMapper objectMapper = new ObjectMapper();

                // Parsiraj JSON u JsonNode
                JsonNode rootNode = objectMapper.readTree(json);
                JsonNode resultsNode = rootNode.get("results"); // Dohvati "results" polje

                // Provjeri je li "results" polje lista objekata
                if (resultsNode != null && resultsNode.isArray()) {
                    // Pretvori svaki objekt iz liste u instancu klase Movie
                    List<Movie> movies = new ArrayList<>();
                    for (JsonNode movieNode : resultsNode) {
                        MovieDTO movieDTO = objectMapper.treeToValue(movieNode, MovieDTO.class);
                        Movie movie = objectMapper.treeToValue(movieNode, MovieDTO.class).toDAO();
                        movie.setGenres(getGenres(movieDTO.getGenre_ids()));
                        movies.add(movie);
                    }
                    return movies;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private List<String> getGenres(List<Integer> genreIds) {
        Map<Integer, String> genres = genreService.fetchGenres();
        List<String> genreNames = new ArrayList<>();
        for(int genreId : genreIds) {
            genreNames.add(genres.get(genreId));
        }
        return genreNames;
    }
}
