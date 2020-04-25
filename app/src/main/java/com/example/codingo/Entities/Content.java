/*
 * INFS3634 Group Assignment 2020 T1 - Team 31
 *
 * This is an Android mobile application that showcases the use of functional Android building blocks
 * and the implementation of other features such as Google Firebase and API calls. Submitted as part of
 * a group assignment for the course, INFS3634.
 *
 * Authors:
 * Shara Bakal, Khang Bui, Laurence Truong & Brian Vu
 *
 */

package com.example.codingo.Entities;

/**
 * Model class for Content.
 * Used in the VideoLearnFragment to retrieve videos, content and title text.
 */
public class Content {
    private String topic; //topic name
    private String content; //content body
    private String video; //video url

    /**
     * Content object constructor
     * @param topic is the topic name
     * @param content is the content body (please use ASCII value for spaces  when writing)
     * @param video is the url of the video
     */
    public Content (String topic, String content, String video){
        this.topic = topic;
        this.content = content;
        this.video = video;
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

}
