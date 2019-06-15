package edu.byu.cs.superasteroids.model_classes;

/**
 * Created by BenNelson on 10/21/16.
 * This class will extend PositionedObject, because it is one, but it is also moving.
 */
public class MovingObject extends PositionedObject {

    private MovingObject moving_object;

    public void setMovingObject(MovingObject m)
    {
        moving_object = m;
    }
    public MovingObject getMovingObject()
    {
        return moving_object;
    }

    /**
     * This method will update a moving object
     * @param m
     */
    public void update(MovingObject m){}


}
