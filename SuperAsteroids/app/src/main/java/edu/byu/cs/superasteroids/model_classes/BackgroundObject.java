package edu.byu.cs.superasteroids.model_classes;

/**
 * Created by BenNelson on 10/21/16.
 * This class extends PositiondObject because it is a bg that needs positioning
 */
public class BackgroundObject extends PositionedObject{

    private int objectId;
    private String objectPath;

    public BackgroundObject(int objectId, String objectPath)
    {
        this.objectId = objectId;
        this.objectPath = objectPath;
    }

    public int getObjectId() {
        return objectId;
    }

    public String getObjectPath() {
        return objectPath;
    }



}
