package net.kordian.shortener.url;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UrlService {
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public Url createUrl(Url url) {
        // Generate a unique short code for the destination URL
        String shortCode = generateShortCode();
        url.setDestination(shortCode);
        url.setCount(0); // Initialize count to 0
        return urlRepository.save(url);
    }

    public Url getUrlByDestination(String destination) {
        return urlRepository.findByDestination(destination);
    }

    private String generateShortCode() {
        //TODO: It needs to be handle differently...
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }

    public void saveUrl(Url url) {
        try {
            urlRepository.save(url);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
}
