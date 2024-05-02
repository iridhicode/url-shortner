package com.shortenurl.shortenurl.controller;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shortenurl.shortenurl.entity.UrlMappingEntity;
import com.shortenurl.shortenurl.repo.ShortenURLRepo;

@Controller
public class ShortenURLController {
	

	@Autowired
	ShortenURLRepo shortenURLRepo;

	@PostMapping("/generateURL")
	public ResponseEntity<String> generateURL(@RequestParam("longurl") String longURL) {
		
		String sanitizedUrl = URLEncoder.encode(longURL, StandardCharsets.UTF_8);
		String newUrl = shortenURL(sanitizedUrl);
		System.out.println("Shortened URL: " + newUrl + " for the long URL :" + longURL );
		if (null != newUrl) {
			UrlMappingEntity mappingEntity = new UrlMappingEntity();
			mappingEntity.setLongUrl(sanitizedUrl);
			mappingEntity.setShortUrl(newUrl);
			shortenURLRepo.save(mappingEntity);
			return   ResponseEntity.ok("Short URL: " + newUrl);
		}
		return ResponseEntity.notFound().build();

	}

	@GetMapping("/{shortUrl}")
	public ResponseEntity<Void> redirectToLongUrl(@PathVariable String shortUrl) {
	    UrlMappingEntity url = shortenURLRepo.findByShortUrl(shortUrl);
	    if (url != null) {
	        String longUrl = url.getLongUrl();
	        if (!longUrl.startsWith("http://") && !longUrl.startsWith("https://")) {
	            longUrl = "http://" + longUrl;
	        }
	        return ResponseEntity.status(HttpStatus.FOUND)
	                .location(URI.create(longUrl))
	                .build();
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}

	public static String shortenURL(String url) {
		StringBuilder str = new StringBuilder();
		Random random = new Random();

		final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		final int SHORT_URL_LENGTH = 6;

		if (null != url) {
			for (int i = 0; i < SHORT_URL_LENGTH; i++) {
				int index = random.nextInt(CHARACTERS.length());
				str.append(CHARACTERS.charAt(index));
			}
			return str.toString();
		} else {
			return null;
		}
	}
}
