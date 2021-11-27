package com.gap.rss.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
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
	
	public RssFeed findRssfeedById(Long id) 
	{
		return rssFeedRepository.findById(id).get();
	}

}
