package com.example.newsapp.model;
import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.List;

public class TopHeadlinesResponseModel {
    @SerializedName("status")
    String status;

    @SerializedName("totalResults")
    int totalResults;

    @SerializedName("articles")
    List<Articles> articles;


    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
    public int getTotalResults() {
        return totalResults;
    }

    public void setArticles(List<Articles> articles) {
        this.articles = articles;
    }
    public List<Articles> getArticles() {
        return articles;
    }
    public static class Source {

        @SerializedName("id")
        String id;

        @SerializedName("name")
        String name;


        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

    }

    public static class Articles {

        @SerializedName("source")
        Source source;

        @SerializedName("author")
        String author;

        @SerializedName("title")
        String title;

        @SerializedName("description")
        String description;

        @SerializedName("url")
        String url;

        @SerializedName("urlToImage")
        String urlToImage;

        @SerializedName("publishedAt")
        Date publishedAt;

        @SerializedName("content")
        String content;


        public void setSource(Source source) {
            this.source = source;
        }
        public Source getSource() {
            return source;
        }

        public void setAuthor(String author) {
            this.author = author;
        }
        public String getAuthor() {
            return author;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        public String getTitle() {
            return title;
        }

        public void setDescription(String description) {
            this.description = description;
        }
        public String getDescription() {
            return description;
        }

        public void setUrl(String url) {
            this.url = url;
        }
        public String getUrl() {
            return url;
        }

        public void setUrlToImage(String urlToImage) {
            this.urlToImage = urlToImage;
        }
        public String getUrlToImage() {
            return urlToImage;
        }

        public void setPublishedAt(Date publishedAt) {
            this.publishedAt = publishedAt;
        }
        public Date getPublishedAt() {
            return publishedAt;
        }

        public void setContent(String content) {
            this.content = content;
        }
        public String getContent() {
            return content;
        }

    }
}
