package edu.byu.cs.superasteroids.model_classes;

import android.graphics.PointF;

/**
 * This class is the main body of the ship
 * Created by BenNelson on 10/29/16.
 *
 */
public class MainBody extends Ship {

    private String cannonAttach; //MAKE THESE point Fs
    private String engineAttach; //MAKE THESE point Fs
    private String extraAttach; //MAKE THESE point Fs
    private String image;
    private int imageWidth;  // <----|
    private int imageHeight; // <----|-------these two become Point F center (img width/2, img height/2)

    private PointF mbCannonAttach;
    private PointF mbEngineAttach;
    private PointF mbExtraAttach;
    private PointF mbCenter;



    //current_location Point F

    public MainBody(String cannonAttach, String engineAttach, String extraAttach, String image, int imageWidth, int imageHeight)
    {
        this.cannonAttach = cannonAttach;
        this.engineAttach = engineAttach;
        this.extraAttach = extraAttach;
        this.image = image;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    public PointF getMbCannonAttach() {
        return mbCannonAttach;
    }

    public void setMbCannonAttach()
    {

        float x = getFloatFromXYinput(0, cannonAttach);
        float y = getFloatFromXYinput(1, cannonAttach);
        mbCannonAttach = new PointF(x,y);
    }

    public PointF getMbEngineAttach() {
        return mbEngineAttach;
    }

    public void setMbEngineAttach()
    {

        float x = getFloatFromXYinput(0, engineAttach);
        float y = getFloatFromXYinput(1, engineAttach);
        mbEngineAttach = new PointF(x,y);
    }

    public PointF getMbExtraAttach() {
        return mbExtraAttach;
    }

    public void setMbExtraAttach()
    {
        float x = getFloatFromXYinput(0, extraAttach);
        float y = getFloatFromXYinput(1, extraAttach);
        mbExtraAttach = new PointF(x,y);
    }

    public PointF getMbCenter()
    {return mbCenter;}
    public void setMbCenter()
    {
        mbCenter = new PointF(imageWidth/2,imageHeight/2);
    }
    //*********

    public String getCannonAttach() {
        return cannonAttach;
    }

    public void setCannonAttach(String cannonAttach) {
        this.cannonAttach = cannonAttach;
    }

    public String getEngineAttach() {
        return engineAttach;
    }

    public void setEngineAttach(String engineAttach) {
        this.engineAttach = engineAttach;
    }

    public String getExtraAttach() {
        return extraAttach;
    }

    public void setExtraAttach(String extraAttach) {
        this.extraAttach = extraAttach;
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
