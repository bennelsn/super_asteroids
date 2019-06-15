package edu.byu.cs.superasteroids.game;

import android.graphics.PointF;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.model_classes.GameEngine;
import edu.byu.cs.superasteroids.model_classes.Levels;
import edu.byu.cs.superasteroids.model_classes.Ship;

/**
 * my screen is 1196 x 670
 * or           1116 x 377
 * Created by BenNelson on 11/8/16.
 */
public class ViewPort
{

    private static ViewPort instance = null;

    protected ViewPort() {
        // Exists only to defeat instantiation.
    }
    public static ViewPort getInstance() {
        if(instance == null) {
            instance = new ViewPort();
        }
        return instance;
    }
    private boolean firstTimeW = true;
    private boolean firstTimeH = true;
    private float deviceScreenWidth = 1196;
    private float deviceScreenHeight = 670;


    private PointF topLeftCorner = new PointF(0,0);
    private PointF bottomRightCorner = new PointF(0,0);


    public void setUpViewPort()
    {
        Levels current_level = GameEngine.getInstance().getCurrentLevel();
        //find the center point of the level
        float levelCenterX = current_level.getWidth()/2;
        float levelCenterY = current_level.getHeight()/2;

        topLeftCorner.x = (levelCenterX) - (deviceScreenWidth/2);
        topLeftCorner.y = (levelCenterY) - (deviceScreenHeight/2);

        bottomRightCorner.x = (levelCenterX) + (deviceScreenWidth/2);
        bottomRightCorner.y = (levelCenterY) + (deviceScreenHeight/2);



        //Now the view port should be in the middle of the level.
        //For example, if the level is 3000x3000, the levelCenterX and Y should be (1500,1500)
        //The top left corner should be (902,1165)
        //the bottom right corner should be (2098,1835)
        //the top right corner should be (2098,1165)
        //the bottom left corner should be (902, 1835)
    }

    public void updateViewPort()
    {
        //TOP LEFT CORNER
        float maxWidth = GameEngine.getInstance().getCurrentLevel().getWidth();
        float maxHeight = GameEngine.getInstance().getCurrentLevel().getHeight();

        topLeftCorner.x = (GameEngine.getInstance().getCurrentShipPosition().x) - (deviceScreenWidth/2);
        //Check bounds
        if(topLeftCorner.x < 0) {topLeftCorner.x = 0;}

        topLeftCorner.y = (GameEngine.getInstance().getCurrentShipPosition().y) - (deviceScreenHeight/2);
        //Check bounds
        if(topLeftCorner.y < 0) {topLeftCorner.y = 0;}

        //BOTTOM RIGHT CORNER
        bottomRightCorner.x = GameEngine.getInstance().getCurrentShipPosition().x + (deviceScreenWidth/2);
        //check bounds
        if(bottomRightCorner.x > maxWidth)
        {topLeftCorner.x = maxWidth - deviceScreenWidth;}

        bottomRightCorner.y = GameEngine.getInstance().getCurrentShipPosition().y + (deviceScreenHeight/2);
        //Check Bounds
        if(bottomRightCorner.y > maxHeight)
        {topLeftCorner.y = maxHeight - deviceScreenHeight;}

    }



    public float getDeviceScreenWidth() {
        return deviceScreenWidth;
    }

    public void setDeviceScreenWidth(float deviceScreenWidth) {
        this.deviceScreenWidth = deviceScreenWidth;
    }

    public float getDeviceScreenHeight() {
        return deviceScreenHeight;
    }

    public void setDeviceScreenHeight(float deviceScreenHeight) {
        this.deviceScreenHeight = deviceScreenHeight;
    }

    public PointF getTopLeftCorner() {
        return topLeftCorner;
    }

    public void setTopLeftCorner(PointF topLeftCorner) {
        this.topLeftCorner = topLeftCorner;
    }

    public PointF getBottomRightCorner() {
        return bottomRightCorner;
    }

    public void setBottomRightCorner(PointF bottomRightCorner) {
        this.bottomRightCorner = bottomRightCorner;
    }

    public boolean isFirstTimeH() {
        return firstTimeH;
    }

    public void setFirstTimeH(boolean firstTimeH) {
        this.firstTimeH = firstTimeH;
    }

    public boolean isFirstTimeW() {
        return firstTimeW;
    }

    public void setFirstTimeW(boolean firstTimeW) {
        this.firstTimeW = firstTimeW;
    }



}
