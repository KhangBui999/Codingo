package com.example.codingo.Entities;

/**
 * Model class for in-game badges/achievements.
 * Used in the UserProfileFragment as a data model for badges.
 */
public class Badge {
    private String id;
    private String name;
    private String desc;
    private int imageId;
    private int position;

    /**
     * Constructor for the badge class
     * @param id is the online document ID from the Firebase server
     * @param name is the name of the badge/achievement
     * @param desc is a description of the badge/achievemnet
     * @param imageId is the R id of the badge image
     * @param position the position of the badge in the list
     */
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
