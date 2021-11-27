package com.gap.rss;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.gap.rss.exception.RssFeedErrorAdapter;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.servlet.GraphQLErrorHandler;

@SpringBootApplication
public class RssFeedServiceApplication 
{

	public static void main(String[] args) 
	{
		SpringApplication.run(RssFeedServiceApplication.class, args);
	}
	
	@Bean
	public GraphQLErrorHandler errorHandler() 
	{
		return new GraphQLErrorHandler()
		{
			@Override
			public List<GraphQLError> processErrors(List<GraphQLError> errors) 
			{
				List<GraphQLError> clientErrors = errors.stream()
						.filter(this::isClientError)
						.collect(Collectors.toList());

				List<GraphQLError> serverErrors = errors.stream()
						.filter(e -> !isClientError(e))
						.map(RssFeedErrorAdapter::new)
						.collect(Collectors.toList());

				List<GraphQLError> e = new ArrayList<>();
				e.addAll(clientErrors);
				e.addAll(serverErrors);
				return e;
			}

			protected boolean isClientError(GraphQLError error) 
			{
				return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
			}
		};
	}

}
