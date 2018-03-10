package com.example.content.Entity;

import java.util.List;

/**
 * Created by 佳南 on 2017/9/22.*/

public class EpisodeJson {
    public String code;
    public List<Episode> videoEpisode;
    public EpisodeInfo video;
    public List<Episode> subList;

    public class Episode {
        public String id;
        public List<EpisodeUrl> url;
        public String title;
        public String coverUrl;
    }
    public class EpisodeInfo {
        public String videoId;
        public List<EpisodeUrl> url;
        public String name;
        public String info;
        public String title;
        public List<String> label;
    }
    public class EpisodeUrl {
        public String name;
        public String url;
        public String cdnUrl;
        public String size;
        public String durTime;
    }
}
