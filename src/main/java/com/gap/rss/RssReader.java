package com.gap.rss;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gap.rss.model.RssFeed;
import com.gap.rss.service.RssFeedService;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

@Component
@EnableScheduling
public class RssReader 
{
	@Autowired
	RssFeedService rssFeedService;
	
	@Scheduled(fixedRate = 50000)
	public void readRss() 
	{
		 try {
	            String url = "http://feeds.nos.nl/nosjournaal?format=xml";
	 
	            try (XmlReader reader = new XmlReader(new URL(url))) 
	            {
	                SyndFeed feed = new SyndFeedInput().build(reader);
	                System.out.println(feed.getTitle());
	                System.out.println("***********************************");
	                for (SyndEntry entry : feed.getEntries()) {
	                    System.out.println(entry.getTitle());
	                    //System.out.println(entry.getDescription().getValue());
	                    System.out.println(entry.getPublishedDate());
	                    System.out.println(entry.getUri());
	                    System.out.println(entry.getLink());
	                    System.out.println(entry.getEnclosures().get(0).getUrl());
	                    System.out.println("***********************************");
	                    
	                    RssFeed rssFeed = new RssFeed();
	                    rssFeed.setTitle(entry.getTitle());
	                    rssFeed.setDescription(entry.getDescription().getValue());
	                    rssFeed.setPublishedDate(entry.getPublishedDate());
	                    rssFeed.setLink(entry.getUri());
	                    rssFeed.setImage(entry.getEnclosures().get(0).getUrl() != null 
	                    		? entry.getEnclosures().get(0).getUrl() : "" );
	                    rssFeedService.saveRssFeed(rssFeed);
	                }
	                System.out.println("Done");
	            }
	        }  catch (Exception e) {
	            e.printStackTrace();
	        }
	}
}
