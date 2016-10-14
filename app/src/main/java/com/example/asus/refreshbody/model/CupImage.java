package com.example.asus.refreshbody.model;

/**
 * Created by Asus on 10/14/2016.
 */

public class CupImage {
    private int imgRes;
    private boolean isChoose;

    public CupImage(){}

    public CupImage(int imgRes, boolean isChoose) {
        this.imgRes = imgRes;
        this.isChoose = isChoose;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }
}
