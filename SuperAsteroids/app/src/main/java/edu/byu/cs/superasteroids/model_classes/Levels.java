package edu.byu.cs.superasteroids.model_classes;

import java.util.ArrayList;

/**
 * Created by BenNelson on 10/31/16.
 */
public class Levels
{
    private int number;
    private String title;
    private String hint;
    private int width;
    private int height;
    private String music;
    private ArrayList<LevelAsteroid> levelAsteroids;
    private ArrayList<LevelObject> levelObjects;


    //CONSTRUCTOR
    public Levels(int number, String title, String hint, int width, int height, String music, ArrayList<LevelObject> levelObjects, ArrayList<LevelAsteroid> levelAsteroids)
    {
        this.number = number;
        this.title = title;
        this.hint = hint;
        this.width = width;
        this.height = height;
        this.music = music;
        this.levelObjects = levelObjects;
        this.levelAsteroids = levelAsteroids;

    }

    public ArrayList<LevelObject> getLevelObjects() {
        return levelObjects;
    }

    public void setLevelObjects(ArrayList<LevelObject> levelObjects) {
        this.levelObjects = levelObjects;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public ArrayList<LevelAsteroid> getLevelAsteroids() {
        return levelAsteroids;
    }

    public void setLevelAsteroids(ArrayList<LevelAsteroid> levelAsteroids) {
        this.levelAsteroids = levelAsteroids;
    }
}
