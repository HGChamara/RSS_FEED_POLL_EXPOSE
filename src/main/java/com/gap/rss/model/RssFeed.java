package com.gap.rss.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "rss_feed")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RssFeed
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String title;
	private Date publishedDate;
	@Column(length = 10000)
	private String description;
	private String link;
	private String image;
}
