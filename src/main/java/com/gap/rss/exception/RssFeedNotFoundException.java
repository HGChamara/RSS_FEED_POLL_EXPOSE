package com.gap.rss.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class RssFeedNotFoundException extends RuntimeException implements GraphQLError
{
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> extensions = new HashMap<String, Object>();

	public RssFeedNotFoundException(String message, Long invalidFeedId) 
	{
		super(message);
		extensions.put("invalidRssFeedId", invalidFeedId);
	}
	
	@Override
	public List<SourceLocation> getLocations() 
	{
		return null;
	}
	
	@Override
    public Map<String, Object> getExtensions() 
	{
        return extensions;
    }

    @Override
    public ErrorType getErrorType() 
    {
        return ErrorType.DataFetchingException;
    }

}
