package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Service
public class SearchService {
    private final RestTemplate restTemplate;

    public SearchService() {
        this.restTemplate = new RestTemplate();
    }
    public int getSearchResultsCount(String query)  {
        String url = "https://api.example.com/search?q=" + query;
        String responseBody = restTemplate.getForObject(url, String.class);
        return parseSearchResultsCount(responseBody);
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