package com.example.demo;

import com.example.demo.service.SearchService;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DemoApplicationTests {
	@Mock
	private CloseableHttpClient httpClient;
	@InjectMocks
	private SearchService searchService;

	@Test

	void getSearchResultsCount_ShouldReturnExpectedCount_WhenQueryIsProvided() throws IOException {

		MockitoAnnotations.openMocks(this);
		String query = "test query";
		String mockHtmlResponse = "<div id=\"result-stats\">Results: 123,456</div>";
		CloseableHttpResponse httpResponse = Mockito.mock(CloseableHttpResponse.class);
		Mockito.when(httpResponse.getEntity().getContent())
				.thenReturn(IOUtils.toInputStream(mockHtmlResponse, StandardCharsets.UTF_8));

		Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
		int result = searchService.getSearchResultsCount(query);

		assertEquals(123456, result, "Expected search results count does not match");
		}
	}
