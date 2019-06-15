package edu.byu.cs.superasteroids.model_classes;

/**
 * Created by BenNelson on 10/21/16.
 * extends asteroid because it is a type of asteroid
 */
public class RegularAsteroid extends Asteroid {


    public RegularAsteroid(int asteroidId, String name, String image, int imageWidth, int imageHeight, String type)
    {
        super(asteroidId, name, image, imageWidth, imageHeight, type);
    }
}
