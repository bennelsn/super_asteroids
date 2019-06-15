package edu.byu.cs.superasteroids.model_classes;

import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game.ViewPort;

/**
 * Created by BenNelson on 10/21/16.
 * extends MovingObject because an asteroid is a moving object
 */
public class Asteroid extends MovingObject {

    private int asteroidId;
    private String name;
    private String image;
    private int imageWidth;
    private int imageHeight;
    private String type;
    private PointF postion;
    private double rotation;
    private float asteroidScale;
    private RectF bb;
    private boolean isSplit;
    private float speed;
    private int keyNumber;

    public Asteroid(int asteroidId, String name, String image, int imageWidth, int imageHeight, String type)
    {
        this.asteroidId = asteroidId;
        this.name = name;
        this.image = image;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.type = type;
        this.postion = new PointF(0,0);
        this.rotation = (Math.random() * 360);
        this.asteroidScale = 1;
        this.bb = new RectF(0,0,0,0);
        this.isSplit = false;
        this.speed = 200;
        this.keyNumber = 0;
    }

    public int getKeyNumber() {
        return keyNumber;
    }

    public void setKeyNumber(int keyNumber) {
        this.keyNumber = keyNumber;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public PointF getPostion() {
        return postion;
    }

    public void setPostion(PointF postion) {
        this.postion = postion;
    }

    public int getAsteroidId() {
        return asteroidId;
    }

    public String getName() {
        return name;
    }

    public String getImage() { return image; }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public String getType() {
        return type;
    }

    public float getAsteroidScale() {
        return asteroidScale;
    }

    public void setAsteroidScale(float asteroidScale) {
        this.asteroidScale = asteroidScale;
    }

    public boolean isSplit() {
        return isSplit;
    }

    public void setSplit(boolean split) {
        isSplit = split;
    }

    public void draw(Asteroid asteroid)
    {
        String image = asteroid.getImage();
        ContentManager cm = ContentManager.getInstance();
        int id = cm.loadImage(image);
        float x = asteroid.getPostion().x - ViewPort.getInstance().getTopLeftCorner().x;
        float y = asteroid.getPostion().y - ViewPort.getInstance().getTopLeftCorner().y;


        DrawingHelper.drawImage(id, x, y,(float) asteroid.getRotation(), asteroid.getAsteroidScale(), asteroid.getAsteroidScale(), 255);

    }

    public void update(Asteroid asteroid, double elapsedTime)
    {
        //MOVING THE ASTEROID
        moveAsteroid(asteroid,elapsedTime);

        //MAKING SURE THE ASTEROID STAYS IN THE LEVEL
        calculateCollisions(asteroid);


    }

    public void moveAsteroid(Asteroid asteroid, double elapsedTime)
    {
        double rotation = asteroid.getRotation();
        rotation = (GraphicsUtils.degreesToRadians(rotation));

        GraphicsUtils.MoveObjectResult result = GraphicsUtils.moveObject(asteroid.getPostion(), asteroid.getAsteroidBB(), asteroid.getSpeed(), rotation, elapsedTime);
        asteroid.setPostion(result.getNewObjPosition());
        asteroid.setAsteroidBB(result.getNewObjBounds());

    }
    public void calculateCollisions(Asteroid asteroid)
    {
        double rotation = asteroid.getRotation();
        rotation = (GraphicsUtils.degreesToRadians(rotation));

        float worldWidth = GameEngine.getInstance().getCurrentLevel().getWidth();
        float worldHeight = GameEngine.getInstance().getCurrentLevel().getHeight();
        GraphicsUtils.RicochetObjectResult r = GraphicsUtils.ricochetObject(asteroid.getPostion(), asteroid.getAsteroidBB(), rotation, worldWidth, worldHeight);

        //check for collision with the world bounds
        asteroid.setPostion(r.getNewObjPosition());
        asteroid.setAsteroidBB(r.getNewObjBounds());
        asteroid.setRotation(GraphicsUtils.radiansToDegrees(r.getNewAngleRadians()));


        //check for collision with the ship
        GameEngine gameEngine = GameEngine.getInstance();
        if(r.getNewObjBounds().intersects(gameEngine.getCurrentShipBB(), asteroid.getAsteroidBB()))
        {
            //if the ship collides with an asteroid
            Ship.myShip.setShipHit(true);
            breakAsteroids(asteroid);

        }

        //check for each projectile
        ArrayList<Projectile> projectiles = GameEngine.getInstance().getCurrentProjectiles();
        int index = 0;
        ArrayList<Integer> list = new ArrayList<>();
        for(Projectile p : projectiles)
        {
            if(r.getNewObjBounds().intersects(p.getProjectileBB(), asteroid.getAsteroidBB()))
            {
                list.add(index);
                breakAsteroids(asteroid);
            }
            index++;
        }
        int numberOfProjectilesRemoved = 0;
        for(Integer i : list)
        {

            int x = i - numberOfProjectilesRemoved;
            projectiles.remove(x);

            numberOfProjectilesRemoved++;
        }

        GameEngine.getInstance().setCurrentProjectiles(projectiles);




    }

    private void breakAsteroids(Asteroid asteroid)
    {
        if (asteroid.getType().equals("regular"))
        {
            //REGULAR
            if(asteroid.isSplit())
            {
                //remove the asteroid from the list
                int key = asteroid.getKeyNumber();
                GameEngine.getInstance().addAsteroidToRemove(key);
            }
            else
            {
                asteroid.setSplit(true);
                //set the current regular asteroid to half the size.
                asteroid.setAsteroidScale(asteroid.getAsteroidScale()/2);

                //Set new bouding box
                asteroid.setAsteroidBB(initializeAsteroidBB(asteroid));
                //Copy the asteroid
                Asteroid copy = copyAsteroid(asteroid);
                //change rotation a bit
                asteroid.setRotation((Math.random() * 360));
                copy.setRotation((Math.random() * 360));

                GameEngine.getInstance().addAsteroidToAdd(copy);

            }
        }
        else if (asteroid.getType().equals("growing"))
        {
            if(asteroid.isSplit())
            {
                int key = asteroid.getKeyNumber();
                GameEngine.getInstance().addAsteroidToRemove(key);
            }
            else
            {
                asteroid.setSplit(true);
                //Copy the asteroid
                Asteroid copy = copyAsteroid(asteroid);
                //change rotation a bit
                asteroid.setRotation((Math.random() * 360));
                copy.setRotation((Math.random() * 360));

                GameEngine.getInstance().addAsteroidToAdd(copy);

            }
        }
        else // OCTEROID
        {
            if(asteroid.isSplit())
            {
                int key = asteroid.getKeyNumber();
                GameEngine.getInstance().addAsteroidToRemove(key);
            }
            else
            {
                asteroid.setSplit(true);

                asteroid.setAsteroidScale(getAsteroidScale()/8);

                //Copy the asteroid
                Asteroid copyOne = copyAsteroid(asteroid);
                Asteroid copyTwo = copyAsteroid(asteroid);
                Asteroid copyThree = copyAsteroid(asteroid);
                Asteroid copyFour = copyAsteroid(asteroid);
                Asteroid copyFive = copyAsteroid(asteroid);
                Asteroid copySix = copyAsteroid(asteroid);
                Asteroid copySeven = copyAsteroid(asteroid);

                //change rotation a bit
                asteroid.setRotation((Math.random() * 360));
                copyOne.setRotation((Math.random() * 360));
                copyTwo.setRotation((Math.random() * 360));
                copyThree.setRotation((Math.random() * 360));
                copyFour.setRotation((Math.random() * 360));
                copyFive.setRotation((Math.random() * 360));
                copySix.setRotation((Math.random() * 360));
                copySeven.setRotation((Math.random() * 360));


                GameEngine.getInstance().addAsteroidToAdd(copyOne);
                GameEngine.getInstance().addAsteroidToAdd(copyTwo);
                GameEngine.getInstance().addAsteroidToAdd(copyThree);
                GameEngine.getInstance().addAsteroidToAdd(copyFour);
                GameEngine.getInstance().addAsteroidToAdd(copyFive);
                GameEngine.getInstance().addAsteroidToAdd(copySix);
                GameEngine.getInstance().addAsteroidToAdd(copySeven);

            }
        }


    }

    private Asteroid copyAsteroid(Asteroid asteroid)
    {

        int asteroidId = asteroid.getAsteroidId();
        String name = asteroid.getName();
        String image = asteroid.getImage();
        int imageWidth = asteroid.getImageWidth();
        int imageHeight = asteroid.getImageHeight();
        String type = asteroid.getType();

        PointF postion = new PointF(0,0);
        postion = asteroid.getPostion();
        double rotation = asteroid.getRotation();
        float asteroidScale = asteroid.getAsteroidScale();
        RectF bb = new RectF(0,0,0,0);
        bb = asteroid.getAsteroidBB();
        boolean isSplit = asteroid.isSplit();
        float speed = (float)((Math.random() * 200) + 100);
        //Generate key
        int key = GameEngine.getInstance().generateKey();
        GameEngine.getInstance().addToListOfKeyNumbers(key);

        if(type.equals("regular"))
        {
            Asteroid r = new RegularAsteroid(asteroidId,name,image,imageWidth,imageHeight,type);
            r.setPostion(postion);
            r.setRotation(rotation);
            r.setAsteroidScale(asteroidScale);
            r.setAsteroidBB(bb);
            r.setSplit(isSplit);
            r.setSpeed(speed);
            r.setKeyNumber(key);

            return r;
        }
        else if (type.equals("growing"))
        {
            Asteroid g = new GrowingAsteroid(asteroidId,name,image,imageWidth,imageHeight,type);
            g.setPostion(postion);
            g.setRotation(rotation);
            g.setAsteroidScale(asteroidScale);
            g.setAsteroidBB(bb);
            g.setSplit(isSplit);
            g.setSpeed(speed);
            g.setKeyNumber(key);

            return g;
        }
        else
        {
            Asteroid o =  new Octaroid(asteroidId,name,image,imageWidth,imageHeight,type);
            o.setPostion(postion);
            o.setRotation(rotation);
            o.setAsteroidScale(asteroidScale);
            o.setAsteroidBB(bb);
            o.setSplit(isSplit);
            o.setSpeed(speed);
            o.setKeyNumber(key);

            return o;
        }
    }

    public RectF initializeAsteroidBB(Asteroid a)
    {
        PointF imageWH = new PointF(a.getImageWidth(),a.getImageHeight());
        imageWH = GraphicsUtils.scale(imageWH,a.getAsteroidScale());

        //set the bounds for the asteroid
        float left = a.getPostion().x - (imageWH.x/2);
        float top = a.getPostion().y - (imageWH.y/2);
        float right = a.getPostion().x + (imageWH.x/2);
        float bottom = a.getPostion().y + (imageWH.y/2);

        return new RectF(left,top,right,bottom);

    }

    public RectF getAsteroidBB() {
        return bb;
    }

    public void setAsteroidBB(RectF bb) {
        this.bb = bb;
    }
}
