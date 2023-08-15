package com.example.demo.web;

import com.example.demo.service.SearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public ResponseEntity<String> getSearchStats(@RequestParam("query") String query) {
        try {
            String encodedQuery = URLEncoder.encode(query, "UTF-8");
            int searchResultsCount = searchService.getSearchResultsCount(encodedQuery);

            String response = "Search query: " + query + "\n"
                    + "Search results count: " + searchResultsCount;

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while fetching data: " + e.getMessage());
        }
    }
}
