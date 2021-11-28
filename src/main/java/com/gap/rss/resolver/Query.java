package com.gap.rss.resolver;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.gap.rss.exception.RssFeedNotFoundException;
import com.gap.rss.model.RssFeed;
import com.gap.rss.repository.RssFeedRepository;

@Component
public class Query implements GraphQLQueryResolver 
{
	@Autowired
	private RssFeedRepository rssFeedRepository;
	
	public Iterable<RssFeed> allRssFeeds()
	{
		return rssFeedRepository.findAll();
	}
	
	public RssFeed findRssfeedById(Long id) throws Exception 
	{
		Optional<RssFeed> rssFeedObj =  rssFeedRepository.findById(id);
		if(!rssFeedObj.isPresent()) 
		{
			throw new RssFeedNotFoundException("Rss feed id not found", id);
		}
		return rssFeedObj.get();
	}

}
