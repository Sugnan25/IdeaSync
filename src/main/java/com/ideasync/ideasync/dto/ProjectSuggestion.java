package com.ideasync.ideasync.dto;

public class ProjectSuggestion {

    private String title;
    private String description;
    private String youtubeQuery;

    public ProjectSuggestion(String title, String description, String youtubeQuery) {
        this.title = title;
        this.description = description;
        this.youtubeQuery = youtubeQuery;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getYoutubeQuery() {
        return youtubeQuery;
    }
}
