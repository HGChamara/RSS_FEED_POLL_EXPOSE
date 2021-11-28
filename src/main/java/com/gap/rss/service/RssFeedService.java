package com.gap.rss.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gap.rss.model.RssFeed;
import com.gap.rss.repository.RssFeedRepository;

@Service
public class RssFeedService 
{
	@Autowired
	RssFeedRepository rssRepository;

	Logger log = LoggerFactory.getLogger(RssFeedService.class);
	
	public void saveRssFeed(RssFeed inputFeed) 
	{
		log.debug("saveRssFeed() : RssFeedService.class");
		
		Optional<RssFeed> feed = rssRepository.getByTitle(inputFeed.getTitle());
		
		if(feed.isPresent()) 
		{
			log.debug("Feed "+feed.get()+" is present" );
			
			RssFeed savedFeed = feed.get();
			savedFeed.setTitle(inputFeed.getTitle());
			savedFeed.setDescription(inputFeed.getDescription());
			savedFeed.setPublishedDate(inputFeed.getPublishedDate());
			savedFeed.setLink(inputFeed.getLink());
			savedFeed.setImage(inputFeed.getImage() );
			
			log.debug("updated feed : "+feed.get().getId());
		}
		else 
		{
			rssRepository.save(inputFeed);
		}
	}
}
