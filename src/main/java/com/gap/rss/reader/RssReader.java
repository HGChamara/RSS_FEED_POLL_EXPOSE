package com.gap.rss.reader;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	Logger log = LoggerFactory.getLogger(RssReader.class);
	
	@Scheduled(fixedRate = 300000) //TODO : fixedRate should be in the application.properties
	public void readRss() 
	{
		log.debug("readRss() : RssReader.class");
		 try {
	            String url = "http://feeds.nos.nl/nosjournaal?format=xml"; //TODO : This should be in the application.properties
	 
	            try (XmlReader reader = new XmlReader(new URL(url))) 
	            {
	                SyndFeed feed = new SyndFeedInput().build(reader);
	                for (SyndEntry entry : feed.getEntries()) 
	                {
	                	log.info("Reading Rss feed "+entry.getTitle());
	                    
	                    RssFeed rssFeed = new RssFeed();
	                    rssFeed.setTitle(entry.getTitle());
	                    rssFeed.setDescription(entry.getDescription().getValue());
	                    rssFeed.setPublishedDate(entry.getPublishedDate());
	                    rssFeed.setLink(entry.getUri());
	                    rssFeed.setImage(entry.getEnclosures().get(0).getUrl() != null 
	                    		? entry.getEnclosures().get(0).getUrl() : "" );
	                    rssFeedService.saveRssFeed(rssFeed);
	                }
	            }
	        } 
		 catch (Exception e)
		 {
			 e.printStackTrace();
	     }
	}
}
