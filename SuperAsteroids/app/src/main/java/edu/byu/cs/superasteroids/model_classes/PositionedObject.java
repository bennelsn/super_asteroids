package edu.byu.cs.superasteroids.model_classes;

/**
 * Created by BenNelson on 10/21/16.
 * This class extends VisualObject because it is a visual object, that also needs to be positioned
 */
public class PositionedObject extends VisualObject {

    private PositionedObject p_object;

    public void setPositionedObject(PositionedObject p)
    {
        p_object = p;
    }
    public PositionedObject getPositionedObject()
    {
        return p_object;
    }

}
