package edu.byu.cs.superasteroids.model_classes;

/**
 * Created by BenNelson on 10/21/16.
 * extends Asteroid because it is a type of asteroid
 */
public class GrowingAsteroid extends Asteroid {



    public GrowingAsteroid(int asteroidId, String name, String image, int imageWidth, int imageHeight, String type)
    {
        super(asteroidId, name, image, imageWidth, imageHeight, type);
    }
}
