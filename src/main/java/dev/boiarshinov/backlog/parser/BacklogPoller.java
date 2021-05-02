package dev.boiarshinov.backlog.parser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public final class BacklogPoller {

    private final URI uri;
    private final HttpClient httpClient;

    public BacklogPoller(String url) {
        this.uri = URI.create(url);
        httpClient = HttpClient.newHttpClient();
    }

    public String poll() {
        final HttpRequest backlogRequest = HttpRequest.newBuilder()
            .uri(uri)
            .GET()
            .build();

        try {
            final HttpResponse<String> response = httpClient.send(backlogRequest, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error during send http request to backlog sources", e);
        }
    }
}
