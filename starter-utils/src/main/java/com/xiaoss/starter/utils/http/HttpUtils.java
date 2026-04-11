package com.xiaoss.starter.utils.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public final class HttpUtils {

    private static final HttpClient CLIENT = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();

    private HttpUtils() {
    }

    public static String get(String url) {
        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).GET().timeout(Duration.ofSeconds(10)).build();
        try {
            return CLIENT.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("HTTP GET failed: " + url, ex);
        }
    }
}
