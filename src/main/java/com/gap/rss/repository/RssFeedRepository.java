package com.gap.rss.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gap.rss.model.RssFeed;

public interface RssFeedRepository extends JpaRepository<RssFeed, Long> {
	
	Optional<RssFeed> getByTitle(String title);

}
