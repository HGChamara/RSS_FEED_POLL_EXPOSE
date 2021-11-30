package com.gap.rss.reader;

import java.io.IOException;
import java.net.MalformedURLException;
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
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

/**
 * <h1>Read RSS feed in scheduled time continuously</h1>
 * The RssReader class consists of method that will poll the given rss feed address according to the scheduled time
 * 
 *@author Givantha Chamara
 *@version 1.0
 *@since 29-11-2021
 */

@Component
@EnableScheduling
public class RssReader 
{
	@Autowired
	RssFeedService rssFeedService;
	
	Logger log = LoggerFactory.getLogger(RssReader.class);
	
	/**
	 * This method will read the RSS feed from the given url and store feed data in the H2 database
	 * @exception MalformedURLException, IOException, IllegalArgumentException, FeedException
	 */
	@Scheduled(fixedRate = 300000) //TODO : fixedRate should be in the application.properties
	public void readRss() 
	{
		log.debug("readRss() : RssReader.class");
		String url = "http://feeds.nos.nl/nosjournaal?format=xml"; //TODO : This should be in the application.properties

		try (XmlReader reader = new XmlReader(new URL(url))) 
		{
			SyndFeed feed = new SyndFeedInput().build(reader);
			for (SyndEntry entry : feed.getEntries()) 
			{
				log.info("Reading Rss feed "+entry.getTitle());
				RssFeed rssFeed = createRssObj(entry);
				rssFeedService.saveRssFeed(rssFeed);
			}
		} 
		catch (MalformedURLException e) 
		{
			log.error(e.getMessage());
		} 
		catch (IOException e) 
		{
			log.error(e.getMessage());
		} 
		catch (IllegalArgumentException e) 
		{
			log.error(e.getMessage());
		} 
		catch (FeedException e) 
		{
			log.error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param entry This is the SyndEntry in the SyndFee object
	 * @return RssFeed object
	 */
	public RssFeed createRssObj(SyndEntry entry ) 
	{
		RssFeed rssFeed = new RssFeed();
        rssFeed.setTitle(entry.getTitle());
        rssFeed.setDescription(entry.getDescription().getValue());
        rssFeed.setPublishedDate(entry.getPublishedDate());
        rssFeed.setLink(entry.getUri());
        rssFeed.setImage(entry.getEnclosures().get(0).getUrl() != null 
        		? entry.getEnclosures().get(0).getUrl() : "" );
        return rssFeed;
	}
}
