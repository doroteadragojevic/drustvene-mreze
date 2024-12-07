package fer.hr.Projekt.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class YoutubeService {
    @Autowired
    private Environment env;
    private static final String YOUTUBE_URL = "https://www.googleapis.com/youtube/v3/search";

    public String getTrailerForTitle(String title) throws IOException {
        String query = title + " trailer";
        String url = YOUTUBE_URL + "?q=" + query.replace(" ", "+") +
                "&part=snippet&type=video&maxResults=1&key=" + env.getProperty("youtube.api-key");

        RestTemplate restTemplate = new RestTemplate();
        try {
            String response = restTemplate.getForObject(url, String.class);
            return parseLink(response);  // Vrati odgovor kao JSON string
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String parseLink(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = objectMapper.readTree(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String videoId = rootNode.path("items").get(0).path("id").path("videoId").asText();
        return "https://www.youtube.com/watch?v=" + videoId;
    }
}
