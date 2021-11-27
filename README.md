# RSS_FEED_POLL_EXPOSE
Poll rss feed and expose usin Graphql

Rss Feed polling from http://feeds.nos.nl/nosjournaal?format=xml and expose the feed items with 2 queries
allRssFeeds 
findRssfeedById(id:<id>) 

Java version : 1.8

h2 databse can be accessed from this url http://localhost:8080/h2-console/login.jsp
JDBC URL: jdbc:h2:mem:testdb
User Name: user
Password: pass
To check the database


Use http://localhost:8080/graphiql to start executing queries. For example:
{
	allRssFeeds {
	id
	title
	image
	publishedDate
	}

}

{
	findRssfeedById(id:1) {
	id
	title
	image
	publishedDate
	}

}

Custom error handling when no rss feeds found by given id
Sample output
{
  "data": {
    "findRssfeedById": null
  },
  "errors": [
    {
      "message": "Rss feed id not found",
      "path": [
        "findRssfeedById"
      ],
      "extensions": {
        "invalidRssFeedId": 55
      },
      "locations": [
        {
          "line": 2,
          "column": 3,
          "sourceName": null
        }
      ],
      "errorType": "DataFetchingException"
    }
  ]
}
