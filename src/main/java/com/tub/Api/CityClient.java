package com.tub.Api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class CityClient {

    private final HttpClient client = HttpClient.newHttpClient();

    public City getCity(String cityName) {

        try {

            String encoded =
                    URLEncoder.encode(cityName, StandardCharsets.UTF_8);

            String url =
                    "https://geocoding-api.open-meteo.com/v1/search?name="
                            + encoded
                            + "&count=1";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject object = new JSONObject(response.body());

            if (!object.has("results")) {
                return null;
            }

            JSONArray results = object.getJSONArray("results");

            if (results.isEmpty()) {
                return null;
            }

            JSONObject city = results.getJSONObject(0);

            return new City(
                    city.getString("name"),
                    city.getString("country"),
                    city.getDouble("latitude"),
                    city.getDouble("longitude")
            );

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}