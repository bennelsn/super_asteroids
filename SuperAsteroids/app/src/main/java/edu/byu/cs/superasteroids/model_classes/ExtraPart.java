package edu.byu.cs.superasteroids.model_classes;

import android.graphics.PointF;

/**
 * Created by BenNelson on 10/29/16.
 * This class is used because it is an ExtraPart class for my ship
 */
public class ExtraPart extends Ship {

    private String attachPoint;
    private String image;
    private int imageWidth;
    private int imageHeight;

    private PointF EpCenter;
    private PointF EpAttachPoint;
    private PointF EpPosition;


    public ExtraPart(String attachPoint, String image, int imageWidth, int imageHeight)
    {
        this.attachPoint = attachPoint;
        this.image = image;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    public PointF getEpCenter() {
        return EpCenter;
    }

    public void setEpCenter() {
        EpCenter = new PointF(imageWidth/2,imageHeight/2);
    }

    public PointF getEpAttachPoint() {
        return EpAttachPoint;
    }

    public void setEpAttachPoint() {
        float x = getFloatFromXYinput(0, attachPoint);
        float y = getFloatFromXYinput(1, attachPoint);
        EpAttachPoint = new PointF(x,y);
    }

    public PointF getEpPosition() {
        return EpPosition;
    }

    public void setEpPosition(PointF epPosition) {
        EpPosition = epPosition;
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
