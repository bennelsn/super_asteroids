package edu.byu.cs.superasteroids.model_classes;

/**
 * Created by BenNelson on 10/29/16.
 * This class is used because it is an PowerCore class for my ship
 */
public class PowerCore extends Ship{

    private int cannonBoost;
    private int engineBoost;
    private String image;

    public PowerCore(int cannonBoost, int engineBoost, String image)
    {
        this.cannonBoost = cannonBoost;
        this.engineBoost = engineBoost;
        this.image = image;
    }

    public int getCannonBoost() {
        return cannonBoost;
    }

    public void setCannonBoost(int cannonBoost) {
        this.cannonBoost = cannonBoost;
    }

    public int getEngineBoost() {
        return engineBoost;
    }

    public void setEngineBoost(int engineBoost) {
        this.engineBoost = engineBoost;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
