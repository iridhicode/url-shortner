package com.shortenurl.shortenurl.repo;

import java.net.URL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shortenurl.shortenurl.entity.UrlMappingEntity;

@Repository
public interface ShortenURLRepo extends JpaRepository<UrlMappingEntity, Integer>{
	
	@Query(value = "select short_url from url_mapping where long_url=:longUrl",nativeQuery=true)
	String findByLongUrl(@Param("longUrl") String longUrl);
	
	@Query(value = "select * from url_mapping where short_url=:shortUrl",nativeQuery=true)
	UrlMappingEntity findByShortUrl(String shortUrl);

}
