package com.example.demo.service;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;

@Service
public class SearchService {

    public int getSearchResultsCount(String query) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String searchUrl = "https://www.google.com/search?q=" + URLEncoder.encode(query, "UTF-8");
            HttpGet httpGet = new HttpGet(searchUrl);

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                // Process the response as needed
                // You can parse the response using Jsoup to extract information
                Document document = Jsoup.parse(response.getEntity().getContent(), "UTF-8", "");
                Elements results = document.select("div[id=result-stats]");
                // Extract the search results count from 'results' using Jsoup selectors

                // Return the extracted count
                return parseSearchResultsCount(results.text());
            }
        }
    }

    private int parseSearchResultsCount(String resultStats) {
        // Implement your parsing logic to extract the search results count
        // You might use regular expressions or other techniques to extract the count
        return 0; // Placeholder; replace with actual parsed count
    }
}
