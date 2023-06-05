package net.kordain.shortener;

import net.kordian.shortener.url.Url;
import net.kordian.shortener.url.UrlController;
import net.kordian.shortener.url.UrlService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlControllerTest {

    @Mock
    private UrlService urlService;

    @InjectMocks
    private UrlController urlController;

    @Test
    void testCreateUrl() {
        Url url = new Url();
        Url createdUrl = new Url();

        when(urlService.createUrl(url)).thenReturn(createdUrl);

        ResponseEntity<Url> expectedResponse = ResponseEntity.ok(createdUrl);
        ResponseEntity<Url> actualResponse = urlController.createUrl(url);

        assertEquals(expectedResponse, actualResponse);
        verify(urlService, times(1)).createUrl(url);
    }

    @Test
    void testGetUrlByDestination_ExistingUrl() {
        String destination = "example.com";
        Url url = new Url();
        url.setCount(0);

        when(urlService.getUrlByDestination(destination)).thenReturn(url);
        doNothing().when(urlService).saveUrl(url);

        ResponseEntity<Url> expectedResponse = ResponseEntity.ok(url);
        ResponseEntity<Url> actualResponse = urlController.getUrlByDestination(destination);

        assertEquals(expectedResponse, actualResponse);
        assertEquals(1, url.getCount());
        verify(urlService, times(1)).getUrlByDestination(destination);
        verify(urlService, times(1)).saveUrl(url);
    }


    @Test
    void testGetUrlByDestination_NonExistingUrl() {
        String destination = "nonexistent.com";

        when(urlService.getUrlByDestination(destination)).thenReturn(null);

        ResponseEntity<Url> expectedResponse = ResponseEntity.notFound().build();
        ResponseEntity<Url> actualResponse = urlController.getUrlByDestination(destination);

        assertEquals(expectedResponse, actualResponse);
        verify(urlService, times(1)).getUrlByDestination(destination);
        verify(urlService, never()).saveUrl(any(Url.class));
    }
}
