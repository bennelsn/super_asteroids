package edu.byu.cs.superasteroids.model_classes;

import android.graphics.PointF;
import android.graphics.RectF;

import edu.byu.cs.superasteroids.core.GraphicsUtils;

/**
 * Created by BenNelson on 10/21/16.
 * Extends MovingObject because it is a moving object
 */
public class Projectile extends MovingObject {

    /*attackImage": "images/parts/laser2.png",
            "attackImageWidth": 105,
            "attackImageHeight": 344,
            "attackSound": "sounds/laser.mp3",
            "damage": 2
            */

    private String laserImage;
    private int laserWidth;
    private int laserHeight;
    private String laserSound;
    private int damage;
    private PointF projPosition = new PointF(0,0);
    private float projRotation;
    private RectF projectileBB;

    public Projectile(String laserImage, int laserWidth, int laserHeight, String laserSound, int damage)
    {
        this.laserImage = laserImage;
        this.laserWidth = laserWidth;
        this.laserHeight = laserHeight;
        this.laserSound = laserSound;
        this.damage = damage;
    }


    public int getDamage() {
        return damage;
    }

    public PointF getProjPosition() {
        return projPosition;
    }

    public void setProjPosition(PointF projPosition) {
        this.projPosition = projPosition;
    }

    public float getProjRotation() {
        return projRotation;
    }

    public void setProjRotation(float projRotation) {
        this.projRotation = projRotation;
    }

    public String getLaserImage() {
        return laserImage;
    }

    public void setLaserImage(String laserImage) {
        this.laserImage = laserImage;
    }

    public int getLaserWidth() {
        return laserWidth;
    }

    public void setLaserWidth(int laserWidth) {
        this.laserWidth = laserWidth;
    }

    public int getLaserHeight() {
        return laserHeight;
    }

    public void setLaserHeight(int laserHeight) {
        this.laserHeight = laserHeight;
    }

    public String getLaserSound() {
        return laserSound;
    }

    public void setLaserSound(String laserSound) {
        this.laserSound = laserSound;
    }

    public void initializeProjBoundingBox()
    {
        PointF imageWH = new PointF(this.laserWidth,this.laserHeight);
        imageWH = GraphicsUtils.scale(imageWH,(float).3);

        //set the bounds for the projectile
        float left = this.projPosition.x - (imageWH.x/2);
        float top = this.projPosition.y - (imageWH.y/2);
        float right = this.projPosition.x + (imageWH.x/2);
        float bottom = this.projPosition.y + (imageWH.y/2);

        this.projectileBB =  new RectF(left,top,right,bottom);
    }

    public void setProjectileBB(RectF projectileBB) {
        this.projectileBB = projectileBB;
    }

    public RectF getProjectileBB()
    {
        return projectileBB;
    }

}
