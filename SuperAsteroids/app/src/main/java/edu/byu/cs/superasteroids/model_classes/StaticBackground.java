package edu.byu.cs.superasteroids.model_classes;

/**
 * Created by BenNelson on 10/21/16.
 * This class is for a Static Background object.
 *
 */
public class StaticBackground extends VisualObject {

    private StaticBackground bg;

    public void setBG(StaticBackground staticBG)
    {
        bg = staticBG;
    }

    public StaticBackground getBG()
    {
        return bg;
    }


}
