package com.example.codingo.Model;

import java.util.ArrayList;

public class Content {
    private String topic;
    private String content;
    private String video;
    // Initialised variables with topic changing text for the title, content changing text underneath the video and video is for changing the url

    public Content (String topic, String content, String video){
        this.topic = topic;
        this.content=content;
        this.video=video;
    }
    public String getTopic() {
        return topic;
    }
    public void setTopic(String topic){
        this.topic=topic;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content){
        this.content=content;
    }
    public String getVideo() {
        return video;
    }
    public void setVideo(String video){
        this.video=video;
    }

    public static ArrayList<Content> getContents() {
    ArrayList<Content> content = new ArrayList<>();
    content.add(new Content("Introduction to Java","Hello","2dZiMBwX_5Q"));
    content.add(new Content("Loops","Hello","6djggrlkHY8"));
    content.add(new Content("Strings","Hello","zvA4fzyH1gs"));
    content.add(new Content("Data Structures","Hello","Xzk3XLveA00"));
    content.add(new Content("Object Oriented Programming","Hello","CWYv7xlKydw"));
    content.add(new Content("Advanced Java","Hello","Zn4fo0MQ"));

        return content;
    }
}
