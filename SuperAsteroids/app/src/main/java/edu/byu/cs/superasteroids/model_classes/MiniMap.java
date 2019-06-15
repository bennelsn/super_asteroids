package edu.byu.cs.superasteroids.model_classes;

/**
 * Created by BenNelson on 10/21/16.
 * This class extends PositiondObject because it is a minimap that needs positioning
 */
public class MiniMap extends PositionedObject {

    private MiniMap mini_map;

    public void setMiniMap(MiniMap m)
    {
        mini_map = m;
    }
    public MiniMap getMiniMap()
    {
        return mini_map;
    }
}
