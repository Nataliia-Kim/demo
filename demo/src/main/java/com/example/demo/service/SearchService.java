package com.example.demo.service;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Service
public class SearchService {
    private final CloseableHttpClient httpClient;
    private final Environment environment;

    @Autowired
    public SearchService(CloseableHttpClient httpClient, Environment environment) {
        this.httpClient = httpClient;
        this.environment = environment;
    }
    public int getSearchResultsCount(String query) throws IOException {

            String apiKey = environment.getProperty("api.key");
            String searchUrl = "https://api.example.com/search?q=" + URLEncoder.encode(query, "UTF-8") + "&key=" + apiKey;
            HttpGet httpGet = new HttpGet(searchUrl);

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                Document document = Jsoup.parse(response.getEntity().getContent(), "UTF-8", "");
                Elements results = document.select("div[id=result-stats]");
                return parseSearchResultsCount(results.text());
            }
        }

    private int parseSearchResultsCount(String resultStats) {

        Pattern pattern = Pattern.compile("About (\\d+(?:,\\d+)*) results");
        Matcher matcher = pattern.matcher(resultStats);

        if (matcher.find()) {
            String countString = matcher.group(1).replace(",", "");
            return Integer.parseInt(countString);
        } else {
            return 0;
        }
    }

}