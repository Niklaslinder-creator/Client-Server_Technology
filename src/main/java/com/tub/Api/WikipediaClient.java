package com.tub.Api;

import org.json.JSONObject;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class WikipediaClient {

    private final HttpClient client = HttpClient.newHttpClient();

    public String getSummary(String city) {

        try {

            String encoded =
                    URLEncoder.encode(city, StandardCharsets.UTF_8);

            String url =
                    "https://en.wikipedia.org/api/rest_v1/page/summary/" + encoded;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("User-Agent", "Mozilla/5.0")
                    .header("Accept", "application/json")
                    .GET()
                    .build();
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                return "Keine Beschreibung gefunden.";
            }

            JSONObject object = new JSONObject(response.body());
            if (!object.has("extract")) {
                return "Keine Beschreibung verfügbar.";
            }

            return object.getString("extract");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Fehler beim Abrufen der Wikipedia-Daten.";
        }
    }
}
