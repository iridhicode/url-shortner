package com.shortenurl.shortenurl.controller;

import java.net.URL;
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
	public String generateURL(@RequestParam("longURL") String longURL) {

		String newUrl = shortenURL(longURL);
		if (null != newUrl) {
			UrlMappingEntity mappingEntity = new UrlMappingEntity();
			mappingEntity.setLongUrl(longURL);
			mappingEntity.setShortUrl(newUrl);
			shortenURLRepo.save(mappingEntity);
			return newUrl;
		}
		return "Invalid URL";

	}

	@GetMapping("/{shortUrl}")
	public ResponseEntity<Void> redirectToLongUrl(@PathVariable String shortUrl) {
		UrlMappingEntity url = shortenURLRepo.findByShortUrl(shortUrl);
		if (url != null) {
			return ResponseEntity.status(HttpStatus.FOUND).header("Location", url.getLongUrl()).build();
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
