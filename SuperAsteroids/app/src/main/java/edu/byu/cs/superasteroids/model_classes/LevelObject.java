package edu.byu.cs.superasteroids.model_classes;

/**
 * Created by BenNelson on 10/26/16.
 * This class makes up the Level objects for a given level
 */
public class LevelObject {



    private String position;
    private int objectId;
    private String scale;
    private int linkedLevel;

    //CONSTRUCTOR
    public LevelObject(String position, int objectId, String scale, int linkedLevel)
    {
        this.position = position;
        this.objectId = objectId;
        this.scale = scale;
        this.linkedLevel = linkedLevel;
    }

    public String getScale() {
        return scale;
    }

    public String getPosition() {
        return position;
    }

    public int getObjectId() {
        return objectId;
    }

    public int getLinkedLevel() {return linkedLevel;}



}
