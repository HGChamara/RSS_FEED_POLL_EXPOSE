package com.gap.rss.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gap.rss.model.RssFeed;
import com.gap.rss.repository.RssFeedRepository;

@Service
public class RssFeedService 
{
	@Autowired
	RssFeedRepository rssRepository;

	public void saveRssFeed(RssFeed inputFeed) 
	{
		Optional<RssFeed> feed = rssRepository.getByTitle(inputFeed.getTitle());
		
		if(feed.isPresent()) 
		{
			RssFeed savedFeed = feed.get();
			System.out.println("Feed "+feed.get()+" is present" );
			savedFeed.setTitle(inputFeed.getTitle());
			savedFeed.setDescription(inputFeed.getDescription());
			savedFeed.setPublishedDate(inputFeed.getPublishedDate());
			savedFeed.setLink(inputFeed.getLink());
			savedFeed.setImage(inputFeed.getImage() );
			System.out.println("UPDATED feed : "+feed.get());
		}
		else 
		{
			rssRepository.save(inputFeed);
		}
	}
}
