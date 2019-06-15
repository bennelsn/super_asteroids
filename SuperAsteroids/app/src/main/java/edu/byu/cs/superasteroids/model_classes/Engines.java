package edu.byu.cs.superasteroids.model_classes;

import android.graphics.PointF;

/**
 * Created by BenNelson on 10/29/16.
 * This class is used because it is an Engine class for my ship
 */
public class Engines extends Ship {

    private int baseSpeed;
    private int baseTurnRate;
    private String attachPoint;
    private String image;
    private int imageWidth;
    private int imageHeight;

    private PointF enAttachPoint;
    private PointF enCenter;
    private PointF enPosition;

    private int engineBoost = 0;

    public Engines(int baseSpeed, int baseTurnRate, String attachPoint, String image, int imageWidth, int imageHeight)
    {
        this.baseSpeed = baseSpeed;
        this.baseTurnRate = baseTurnRate;
        this.attachPoint = attachPoint;
        this.image = image;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }


    public int getEngineBoost() {
        return engineBoost;
    }

    public void setEngineBoost(int engineBoost) {
        this.engineBoost = engineBoost;
    }

    public PointF getEnPosition() {
        return enPosition;
    }

    public void setEnPosition(PointF enPosition) {
        this.enPosition = enPosition;
    }

    public PointF getEnCenter() {
        return enCenter;
    }

    public void setEnCenter()
    {
        enCenter = new PointF(imageWidth/2,imageHeight/2);
    }

    public PointF getEnAttachPoint() {
        return enAttachPoint;
    }

    public void setEnAttachPoint()
    {
        float x = getFloatFromXYinput(0, attachPoint);
        float y = getFloatFromXYinput(1, attachPoint);
        enAttachPoint = new PointF(x,y);
    }

    public int getBaseSpeed() {
        return baseSpeed;
    }

    public void setBaseSpeed(int baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    public int getBaseTurnRate() {
        return baseTurnRate;
    }

    public void setBaseTurnRate(int baseTurnRate) {
        this.baseTurnRate = baseTurnRate;
    }

    public String getAttachPoint() {
        return attachPoint;
    }

    public void setAttachPoint(String attachPoint) {
        this.attachPoint = attachPoint;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }
}
