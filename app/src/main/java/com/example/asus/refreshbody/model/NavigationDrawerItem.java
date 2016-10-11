package com.example.asus.refreshbody.model;

/**
 * Created by Asus on 7/3/2016.
 */
public class NavigationDrawerItem {
    String title;
    int image;

    public NavigationDrawerItem(){

    }

    public NavigationDrawerItem(int image, String title) {
        this.image = image;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
