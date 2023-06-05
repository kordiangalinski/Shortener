package net.kordian.shortener.url;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/urls")
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping
    public ResponseEntity<Url> createUrl(@RequestBody Url url) {
        Url createdUrl = urlService.createUrl(url);
        return ResponseEntity.ok(createdUrl);
    }

    @GetMapping("/{destination}")
    public ResponseEntity<Url> getUrlByDestination(@PathVariable String destination) {
        Url url = urlService.getUrlByDestination(destination);
        if (url != null) {
            url.setCount(url.getCount() + 1);
            urlService.saveUrl(url);

            return ResponseEntity.ok(url);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
