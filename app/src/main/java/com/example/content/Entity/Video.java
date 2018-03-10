package com.example.content.Entity;

/**
 * Created by 佳南 on 2017/9/12.
 */

public class Video {
    private String name;
    private String thumbnail;
    private String title;
    private String videoId;

    public boolean isCheck;

    public String getName() {
        return name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public String getVideoId() {
        return videoId;
    }

    public Video(String name, String thumbnail, String title, String videoId) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.title = title;
        this.videoId = videoId;
    }
}
