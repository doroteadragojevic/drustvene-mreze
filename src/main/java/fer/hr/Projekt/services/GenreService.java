package fer.hr.Projekt.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import fer.hr.Projekt.DAO.Movie;
import fer.hr.Projekt.DTO.GenreDTO;
import fer.hr.Projekt.DTO.MovieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class GenreService {

    @Autowired
    Environment env;

    private static String GENRES_URL = "https://api.themoviedb.org/3/genre";
    public Map<Integer, String> fetchGenres() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(GENRES_URL + "/movie/list?language=en")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + env.getProperty("tmdb.api-key"))
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return parseResponse(response);
    }

    private Map<Integer, String> parseResponse(Response response) {
        try {
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                String json = responseBody.string(); // JSON kao String
                ObjectMapper objectMapper = new ObjectMapper();

                // Parsiraj JSON u JsonNode
                JsonNode rootNode = objectMapper.readTree(json);
                JsonNode resultsNode = rootNode.get("genres"); // Dohvati "results" polje

                // Provjeri je li "results" polje lista objekata
                if (resultsNode != null && resultsNode.isArray()) {
                    // Pretvori svaki objekt iz liste u instancu klase Movie
                    Map<Integer, String> genres = new HashMap<>();
                    for (JsonNode genreNode : resultsNode) {
                        GenreDTO genre = objectMapper.treeToValue(genreNode, GenreDTO.class);
                        genres.put(genre.getId(), genre.getName());
                    }
                    return genres;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyMap();
    }

}
