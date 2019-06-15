package edu.byu.cs.superasteroids.model_classes;

/**
 * Created by BenNelson on 10/26/16.
 * This class makes up a Level Asteroid for a given Level
 */
public class LevelAsteroid
{
    private int number; //HOW MANY
    private int asteroidId; //WHICH ASTEROID
    private int linkedLevel; //WHICH LEVEL

    public LevelAsteroid(int number, int asteroidId, int linkedLevel)
    {
        this.number = number;
        this.asteroidId = asteroidId;
        this.linkedLevel = linkedLevel;
    }

    public int getNumber() {
        return number;
    }

    public int getAsteroidId() {
        return asteroidId;
    }

    public int getLinkedLevel() {return linkedLevel;}
}
