package com.shortenurl.shortenurl.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shortenurl.shortenurl.entity.UrlMappingEntity;

@Repository
public interface ShortenURLRepo extends JpaRepository<UrlMappingEntity, Integer>{
	
	
	@Query(value = "select * from url_mapping where short_url=:shortUrl",nativeQuery=true)
	UrlMappingEntity findByShortUrl(String shortUrl);

}
