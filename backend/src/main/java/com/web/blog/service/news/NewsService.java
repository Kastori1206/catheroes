package com.web.blog.service.news;

import java.util.List;
import java.util.Optional;

import com.web.blog.model.news.News;

public interface NewsService {
	public Optional<List<News>> retrieveNews();
}
