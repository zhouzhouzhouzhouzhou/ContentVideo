package com.example.content.Entity;

/**
 * Created by 佳南 on 2017/9/23.
 */

public class EpisodeInfo {
    public String title;
    public String url;
    public String id;
    public String name;

    public EpisodeInfo(String title, String url, String id, String name) {
        this.title = title;
        this.url = url;
        this.id = id;
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
    public String getId() { return id;}
    public String getName() {return name;}
}
