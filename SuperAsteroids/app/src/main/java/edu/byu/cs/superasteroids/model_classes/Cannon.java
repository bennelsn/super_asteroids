package edu.byu.cs.superasteroids.model_classes;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.core.GraphicsUtils;

/**
 * Created by BenNelson on 10/29/16.
 * This class is used because it is an Cannon class for my ship
 */
public class Cannon extends Ship {


    private String attachPoint;
    private String emitPoint;
    private String image;
    private int imageWidth;
    private int imageHeight;
    private String attackImage;
    private int attackImageWidth;
    private int attackImageHeight;
    private String attackSound;
    private int damage;

    private PointF cnAttachPoint;
    private PointF cnEmitPoint;
    private PointF cnCenter;
    private PointF cnPosition;

    private int cannonBoost = 0;



    public Cannon(String attachPoint, String emitPoint, String image, int imageWidth, int imageHeight, String attackImage, int attackImageWidth, int attackImageHeight, String attackSound, int damage)
    {

        this.attachPoint = attachPoint;
        this.emitPoint = emitPoint;
        this.image = image;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.attackImage = attackImage;
        this.attackImageWidth = attackImageWidth;
        this.attackImageHeight = attackImageHeight;
        this.attackSound = attackSound;
        this.damage = damage;
    }

    public PointF getCnPosition() {
        return cnPosition;
    }

    public void setCnPosition(PointF cnPosition) {
        this.cnPosition = cnPosition;
    }

    public PointF getCnEmitPoint() {
        return cnEmitPoint;
    }

    public void setCnEmitPoint()
    {

        float x = getFloatFromXYinput(0, emitPoint);
        float y = getFloatFromXYinput(1, emitPoint);
        cnEmitPoint = new PointF(x,y);
    }

    public PointF getCnAttachPoint() {
        return cnAttachPoint;
    }

    public void setCnAttachPoint() {

        float x = getFloatFromXYinput(0, attachPoint);
        float y = getFloatFromXYinput(1, attachPoint);
        cnAttachPoint = new PointF(x,y);

    }




    public PointF getCnCenter()
    {
        return cnCenter;
    }

    public void setCnCenter()
    {
        cnCenter = new PointF(imageWidth/2,imageHeight/2);
    }

    public String getAttachPoint() {
        return attachPoint;
    }

    public void setAttachPoint(String attachPoint) {
        this.attachPoint = attachPoint;
    }

    public String getEmitPoint() {
        return emitPoint;
    }

    public void setEmitPoint(String emitPoint) {
        this.emitPoint = emitPoint;
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

    public String getAttackImage() {
        return attackImage;
    }

    public void setAttackImage(String attackImage) {
        this.attackImage = attackImage;
    }

    public int getAttackImageWidth() {
        return attackImageWidth;
    }

    public void setAttackImageWidth(int attackImageWidth) {
        this.attackImageWidth = attackImageWidth;
    }

    public int getAttackImageHeight() {
        return attackImageHeight;
    }

    public void setAttackImageHeight(int attackImageHeight) {
        this.attackImageHeight = attackImageHeight;
    }

    public String getAttackSound() {
        return attackSound;
    }

    public void setAttackSound(String attackSound) {
        this.attackSound = attackSound;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
