package com.example.codingo.Entities;

public class Badge {
    private String id;
    private String name;
    private String desc;
    private int imageId;
    private int position;

    public Badge(String id, String name, String desc, int imageId, int position) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.imageId = imageId;
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
