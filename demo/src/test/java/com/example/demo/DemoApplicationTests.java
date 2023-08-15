package com.example.demo;

import com.example.demo.service.SearchService;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.tomcat.util.http.fileupload.util.Closeable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
class DemoApplicationTests {
	@Mock
	private CloseableHttpClient httpClient;
	@InjectMocks
	private SearchService searchService;

	@Test

	void getSearchResultsCount_ShouldReturnExpectedCount_WhenQueryIsProvided() throws IOException {
		// Given
		String query = "test query";
		String mockHtmlResponse = "<div id=\"result-stats\">Results: 123,456</div>";
		// Mock HttpClient behavior

			CloseableHttpResponse httpResponse = Mockito.mock(CloseableHttpResponse.class);
			Mockito.when(httpResponse.getEntity().getContent()).thenReturn(IOUtils.toInputStream(mockHtmlResponse,
			Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
// When
			int result = searchService.getSearchResultsCount(query);
// Then
			assertEquals(123456, result, "Expected search results count does not match");
		}
	}
