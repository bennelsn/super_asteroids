package edu.byu.cs.superasteroids.model_classes;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import java.io.IOException;
import java.util.ArrayList;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.data_classes.DAO;
import edu.byu.cs.superasteroids.game.InputManager;
import edu.byu.cs.superasteroids.game.MyGameController;
import edu.byu.cs.superasteroids.game.ViewPort;
import edu.byu.cs.superasteroids.main_menu.MainActivity;


//LIST <> mainbody
//cannons
//engine
//levels
//ship ship


/**
 * Created by BenNelson on 10/21/16.
 * This class is a singleton because it should only be created once
 */
public class GameEngine {


    private ArrayList<BackgroundObject> backgroundObjects;
    private ArrayList<Asteroid> asteroids;
    private ArrayList<Asteroid> currentAsteroids;
    private ArrayList<Levels> levels;
    private ArrayList<MainBody> mainBodies;
    private ArrayList<Cannon> cannons;
    private ArrayList<ExtraPart> extraParts;
    private ArrayList<Engines> engines;
    private ArrayList<PowerCore> powerCores;
    private ArrayList<Projectile> currentProjectiles = new ArrayList<>();
    private ArrayList<Integer> asteroidsToRemove = new ArrayList<>();
    private ArrayList<Asteroid> asteroidsToAdd = new ArrayList<>();
    private ArrayList<Integer> listOfKeyNumbers = new ArrayList<>();
    private int currentLevelNumber = 0;
    private Levels currentLevel;
    private PointF currentShipPosition;
    private RectF currentShipBB;
    private int sound;
    private int firstCount = 100;
    private int secondCount = 100;
    private final static int reset = 100;




    private static GameEngine instance = null;

    protected GameEngine() {
        // Exists only to defeat instantiation.
    }
    public static GameEngine getInstance() {
        if(instance == null) {
            instance = new GameEngine();
        }
        return instance;
    }

    /**
     * This method will get lists of all the data in the Database and put those lists in the gameEngine to use for gameplay.
     */
    public void populate()
    {
        DAO dao = DAO.getInstance();

        //Get background Objects
        backgroundObjects = dao.getBackgroundObjects();

        //Get asteroids
        asteroids = dao.getAsteroids();

        //Get levels
        levels = dao.getLevels();

        //Get mainbodies
        mainBodies = dao.getMainBodies();

        //get cannons
        cannons = dao.getCannons();

        //get extraparts
        extraParts = dao.getExtraParts();

        //get engines
        engines = dao.getEngines();

        //get powercores
        powerCores = dao.getPowerCores();

    }

    public  void addToListOfKeyNumbers(int number)
    {
        listOfKeyNumbers.add(number);
    }

    public void addAsteroidToRemove(int index)
    {
        asteroidsToRemove.add(index);
    }

    public void addAsteroidToAdd(Asteroid a)
    {
        asteroidsToAdd.add(a);
    }

    public RectF getCurrentShipBB() {
        return currentShipBB;
    }

    public void setCurrentShipBB(RectF currentShipBB) {
        this.currentShipBB = currentShipBB;
    }

    public ArrayList<Asteroid> getCurrentAsteroids() {
        return currentAsteroids;
    }

    public void setCurrentAsteroids(ArrayList<Asteroid> currentAsteroids) {
        this.currentAsteroids = currentAsteroids;
    }

    public PointF getCurrentShipPosition() {
        return currentShipPosition;
    }

    public void setCurrentShipPosition(PointF currentShipPosition) {
        this.currentShipPosition = currentShipPosition;
    }

    public Levels getCurrentLevel()
    {
        return currentLevel;
    }

    public void setCurrentLevel(Levels level)
    {
        currentLevel = level;
    }

    public ArrayList<BackgroundObject> getBackgroundObjects() {
        return backgroundObjects;
    }

    public ArrayList<Asteroid> getAsteroids() {
        return asteroids;
    }

    public ArrayList<Levels> getLevels() {
        return levels;
    }

    public ArrayList<MainBody> getMainBodies() {
        return mainBodies;
    }

    public ArrayList<Cannon> getCannons() {
        return cannons;
    }

    public ArrayList<ExtraPart> getExtraParts() {
        return extraParts;
    }

    public ArrayList<Engines> getEngines() {
        return engines;
    }

    public ArrayList<PowerCore> getPowerCores() {
        return powerCores;
    }

    public int getCurrentLevelNumber() {
        return currentLevelNumber;
    }

    public void setCurrentLevelNumber(int currentLevelNumber) {
        this.currentLevelNumber = currentLevelNumber;
    }

    public ArrayList<Projectile> getCurrentProjectiles() {
        return currentProjectiles;
    }

    public void setCurrentProjectiles(ArrayList<Projectile> currentProjectiles) {
        this.currentProjectiles = currentProjectiles;
    }

    public void draw()
    {
        drawStaticBG();

        drawBackgroundObjects();

        drawShip();

        drawProjectiles(); //IF ANY

        drawAsteroids();

        drawMiniMap();

        if(secondCount != 0)
        {
            drawTransitionTitle();
        }

    }

    private void drawMiniMap()
    {
        int green = 65380;
        int red = 16711680;
        int yellow = 16762880;
        float scale = (float).09;
        PointF br = new PointF(currentLevel.getWidth(), currentLevel.getHeight());
        br = GraphicsUtils.scale(br, scale);
        Rect border = new Rect(0,0,(int)br.x,(int)br.y);
        DrawingHelper.drawRectangle(border,yellow, 255);

        //Draw ship
        //PointF miniShip = new PointF(currentShipPosition.x,currentShipPosition.y);
        PointF miniShip = new PointF(currentShipPosition.x,currentShipPosition.y);
        //miniShip.x = miniShip.x + ViewPort.getInstance().getTopLeftCorner().x;
        //miniShip.y = miniShip.y + ViewPort.getInstance().getTopLeftCorner().y;
        miniShip = GraphicsUtils.scale(miniShip,scale);
        DrawingHelper.drawFilledCircle(miniShip, 6, green, 255);

        //Draw Asteroids
        for(Asteroid asteroid : currentAsteroids)
        {
            PointF pos = new PointF (asteroid.getPostion().x, asteroid.getPostion().y);
            //pos.x = pos.x - ViewPort.getInstance().getTopLeftCorner().x;
            //pos.y = pos.y - ViewPort.getInstance().getTopLeftCorner().y;

            pos = GraphicsUtils.scale(pos,scale);
            DrawingHelper.drawFilledCircle(pos,3, red, 255);

        }
    }

    public void drawStaticBG()
    {

        ContentManager contentManager = ContentManager.getInstance();
        ViewPort vp = ViewPort.getInstance();


        int sizeOfSpaceMap = 2048;
        float x = currentLevel.getWidth();
        float y = currentLevel.getHeight();

        PointF initial = new PointF(x/2,y/2); // Center of world coordinates

        x = x/sizeOfSpaceMap;
        y = y/sizeOfSpaceMap;

        initial = GraphicsUtils.subtract(initial, vp.getTopLeftCorner());

        DrawingHelper.drawImage(contentManager.loadImage("images/space.bmp"),initial.x,initial.y,0,x,y,255);

    }

    public void drawBackgroundObjects()
    {
        ContentManager contentManager = ContentManager.getInstance();
        ViewPort vp = ViewPort.getInstance();

        for(LevelObject lo : currentLevel.getLevelObjects())
        {

            int objectId = lo.getObjectId() - 1;
            String bgPath = backgroundObjects.get(objectId).getObjectPath();

            String bgObjPos = lo.getPosition();
            float x = Ship.myShip.getFloatFromXYinput(0,bgObjPos);
            float y = Ship.myShip.getFloatFromXYinput(1,bgObjPos);
            PointF position = new PointF(x,y);

            position = GraphicsUtils.subtract(position, vp.getTopLeftCorner());

            float scale = Float.parseFloat(lo.getScale());

            DrawingHelper.drawImage(contentManager.loadImage(bgPath), (position.x), (position.y), 0, scale, scale, 255);

        }


    }

    public void drawShip()
    {
        ViewPort vp = ViewPort.getInstance();
        PointF shipPosition = currentShipPosition;
        shipPosition = GraphicsUtils.subtract(shipPosition, vp.getTopLeftCorner());
        Ship.myShip.setCurrentPosition(shipPosition);

        Ship.myShip.draw();
    }

    public void drawProjectiles()
    {
        //Draw the projectile at the cannon emit point
        ContentManager contentManager = ContentManager.getInstance();
        ViewPort vp = ViewPort.getInstance();

        for(Projectile projectile : currentProjectiles) {

            Ship.myShip.drawProjectile(projectile);

        }

    }

    public void drawAsteroids()
    {
        for(Asteroid asteroid : currentAsteroids)
        {
            asteroid.draw(asteroid);
        }
    }

    private void drawTransitionTitle()
    {
        String title = currentLevel.getTitle();
        String hint = currentLevel.getHint();
        int white = 16777215;
        int size = 50;
        if(firstCount != 0)
        {
            DrawingHelper.drawCenteredText(title, size, white);
            firstCount--;
        }
        if(firstCount == 0 && secondCount != 0)
        {
            DrawingHelper.drawCenteredText(hint, size, white);
            secondCount--;
        }

    }

    public void update(double elapsedTime)
    {
        ViewPort viewPort = ViewPort.getInstance();

        PointF x = currentShipPosition;
        PointF y = Ship.myShip.getCurrentPosition();

        //Moves
        if(InputManager.movePoint != null)
        {
            moveShip(InputManager.movePoint, elapsedTime);

            //Update the view port according to a move
            viewPort.updateViewPort();
        }


        if(InputManager.firePressed)
        {
            //add a projectile
            if(currentProjectiles.size() < 10 && shipIsRecharged())
            {
                //Play sound
                ContentManager.getInstance().playSound(sound,1,1);

                Projectile p = makeProjectile(Ship.myShip.getProjIndex());
                currentProjectiles.add(p);
            }
        }

        updateProjectiles(elapsedTime);

        updateAsteroids(elapsedTime);

        checkIfShipIsHit();

        checkLevelStatus();


    }

    private void checkLevelStatus()
    {
        if(currentAsteroids.size() == 0)
        {
            loadNewLevel();
        }
    }

    private void loadNewLevel()
    {
        currentLevelNumber++;
        if(currentLevelNumber+1 > levels.size())
        {
            //We have won the game
            DrawingHelper.drawCenteredText("YOU WON!", 200, 255);
        }
        else{

            firstCount = reset;
            secondCount = reset;
            load(ContentManager.getInstance());
        }

    }

    private void moveShip(PointF move_point, Double elapsedTime)
    {
        PointF movePoint = move_point;
        movePoint = GraphicsUtils.add(movePoint, ViewPort.getInstance().getTopLeftCorner());
        PointF diff = GraphicsUtils.subtract(movePoint, currentShipPosition);
        float radians = (float) Math.atan2(diff.y, diff.x);
        Ship.myShip.setCurrentRotation(GraphicsUtils.radiansToDegrees(radians)+90);

        GraphicsUtils.MoveObjectResult x = GraphicsUtils.moveObject(currentShipPosition,currentShipBB,300,radians,elapsedTime);
        currentShipPosition = x.getNewObjPosition();
        currentShipBB = x.getNewObjBounds();
    }

    private boolean shipIsRecharged()
    {
        boolean safeToShoot = false;

        if(currentProjectiles.size() == 0)
        {
            return true;
        }
        //iterate through the projectiles
        for(Projectile p : currentProjectiles)
        {
            //if the position of each projectile is far enough away from the ship, we can fire again
            //float projPositionX = p.getProjPosition().x + ViewPort.getInstance().getTopLeftCorner().x; // OLD
            //float projPositionY = p.getProjPosition().y + ViewPort.getInstance().getTopLeftCorner().y; //OLD
            float projPositionX = p.getProjPosition().x;// NEW
            float projPositionY = p.getProjPosition().y;//NEW

            float differenceX = Math.abs(currentShipPosition.x -projPositionX);
            float differenceY = Math.abs(currentShipPosition.y -projPositionY);

            if(differenceX > 100 || differenceY > 100)
            {
                //then we are safe to shoot
                safeToShoot = true;
            }
            else
            {
                safeToShoot = false;
            }


        }


        return safeToShoot;
    }

    private Projectile makeProjectile(int index)
    {

        String image = cannons.get(index).getAttackImage();
        int w = cannons.get(index).getAttackImageWidth();
        int h = cannons.get(index).getAttackImageHeight();
        String sound = cannons.get(index).getAttackSound();
        int damage = cannons.get(index).getDamage();

        Projectile p = new Projectile(image,w,h,sound,damage);

        PointF vc = Ship.myShip.getLaserEmitPoint(); // NEW
        PointF wc = GraphicsUtils.add(vc, ViewPort.getInstance().getTopLeftCorner()); // NEW

        p.setProjPosition(wc); //NEW

        //p.setProjPosition(Ship.myShip.getLaserEmitPoint()); // OLD

        p.setProjRotation((float)Ship.myShip.getCurrentRotation());

        p.initializeProjBoundingBox();


        return p;
    }

    private void updateProjectiles(double elapsedTime)
    {

        int index = 0;
        ArrayList<Integer> listOfOutOfBounds = new ArrayList<>();
        for(Projectile p : currentProjectiles) {
            //move it
            double radians = p.getProjRotation();
            radians = (GraphicsUtils.degreesToRadians(radians - 90));


            GraphicsUtils.MoveObjectResult x = GraphicsUtils.moveObject(p.getProjPosition(), p.getProjectileBB(), 600, radians, elapsedTime);
            p.setProjPosition(x.getNewObjPosition());
            p.setProjectileBB(x.getNewObjBounds());

            checkOutOfBounds(p, index, listOfOutOfBounds);
            //checkAsteroidHits(p, index, listOfOutOfBounds);




            index++;

        }

        removeProjectiles(listOfOutOfBounds);


    }

    private void checkOutOfBounds(Projectile p, int index, ArrayList<Integer> list) {
        float maxWidth = currentLevel.getWidth();
        float maxHeight = currentLevel.getHeight();

        float x = p.getProjPosition().x; //+ ViewPort.getInstance().getTopLeftCorner().x;OLD
        float y = p.getProjPosition().y; //+ ViewPort.getInstance().getTopLeftCorner().y;OLD

        //if x or y > 3000 or if x or y < 0, then delete
        if(x < 0 || y < 0 || x > maxWidth || y > maxHeight)
        {
            //record the index
            list.add(index);
        }
    }

    private void removeProjectiles(ArrayList<Integer> listOfOutOfBounds)
    {
        int numberOfProjectilesRemoved = 0;
        for(Integer i : listOfOutOfBounds)
        {

            int projectile = i - numberOfProjectilesRemoved;
            currentProjectiles.remove(projectile);

            numberOfProjectilesRemoved++;
        }

    }

    public PointF generateRandomPosition()
    {
        //Math.random() generates a number from 0 - .999
        double maxWidth = currentLevel.getWidth();
        double maxHeight = currentLevel.getHeight();
        double randomX = Math.random() * maxWidth;
        double randomY = Math.random() * maxHeight;

        PointF randomPosition = new PointF((float)randomX, (float) randomY);

        randomPosition = accountForShipSafeStart(randomPosition);

        return randomPosition;

    }

    private PointF accountForShipSafeStart(PointF randomPosition)
    {
        PointF positionRecalculated = new PointF(0,0);
        //Check to make sure we don't draw on our ship.
        float diffX = Math.abs(currentShipPosition.x - randomPosition.x);
        float diffY = Math.abs(currentShipPosition.y - randomPosition.y);

        if(diffX < 200 || diffY < 200)
        {
            positionRecalculated.x = randomPosition.x + (float)((Math.random() * 200)+500);
            positionRecalculated.y = randomPosition.y + (float)((Math.random() * 200)+500);
            return positionRecalculated;
        }
        else
        {
            return  randomPosition;
        }


    }

    public void updateAsteroids(double elapsedTime)
    {
        int indexA = 0;
        for(Asteroid asteroid : currentAsteroids)
        {
            asteroid.update(asteroid,elapsedTime);
        }

        removeAsteroids();

        addNewAsteroids();

        updateGrowingAsteroids();

    }

    private void updateGrowingAsteroids()
    {
        for(Asteroid asteroid : currentAsteroids)
        {
            if(asteroid.getType().equals("growing") && asteroid.isSplit() && asteroid.getAsteroidScale() <= 1.5)
            {
                float scale = asteroid.getAsteroidScale();
                scale += .01;
                if(scale > 1.5)
                {
                    scale = (float)1.5;
                }
                asteroid.setAsteroidScale(scale);

                //set new bounding box



            }
        }
    }

    private void addNewAsteroids()
    {
        for(Asteroid asteroid : asteroidsToAdd)
        {
            //add each one here to the current asteroids
            currentAsteroids.add(asteroid);
        }
        asteroidsToAdd.clear();
    }

    private void removeAsteroids()
    {
        int index = 0;
        ArrayList<Integer> indexes = new ArrayList<>();
        for(Integer key : asteroidsToRemove)
        {
            //go through all the asteroids and find the correct on to delete
            for(Asteroid asteroid : currentAsteroids)
            {
                if(asteroid.getKeyNumber() == key)
                {
                    //store the index number
                    indexes.add(index);

                }
                index++;
            }

            index = 0;
        }
        asteroidsToRemove.clear();

        int asteroidsAlreadyRemoved = 0;
        for(Integer i : indexes)
        {
            int ast = i - asteroidsAlreadyRemoved;
            currentAsteroids.remove(ast);

            asteroidsAlreadyRemoved++;
        }
    }

    public void checkIfShipIsHit()
    {

        //if the ship is hit
        if(Ship.myShip.isShipHit())
        {
            //Give it a random opacity
            Ship.myShip.setFull_opacity((int)(Math.random() * 200)+30);

            if(Ship.myShip.safeForFiveSeconds())
            {
                //if it has been 5 seconds, we can put the opacity back to normal
                Ship.myShip.setFull_opacity(255);
                Ship.myShip.setShipHit(false);
            }
        }
    }

    public int generateKey()
    {
        int initialKey = 0;
        for(Integer key : listOfKeyNumbers)
        {
            if(initialKey == key)
            {
                initialKey++;
            }
        }

        return initialKey;
    }
    public void setSound(int sound) {
        this.sound = sound;
    }

    public void load(ContentManager content)
    {

        loadCurrentLevel();
        ViewPort.getInstance().setUpViewPort();
        loadCenterPositionForShip();
        loadLaserSound();
        loadShipBoundingBox();
        loadStaticBackground(content);
        loadBackgrounds(content);
        loadAsteroids(content);

    }
    private void loadCurrentLevel()
    {

        int levelNumber = currentLevelNumber;
        int numberOfLevels = levels.size();
        if(numberOfLevels != 0 && (levelNumber+1) <= numberOfLevels)
        {
            setCurrentLevel(levels.get(levelNumber));
        }

    }
    private void loadCenterPositionForShip()
    {
        Levels current_level = GameEngine.getInstance().getCurrentLevel();
        //find the center point of the level
        float levelCenterX = current_level.getWidth()/2;
        float levelCenterY = current_level.getHeight()/2;
        PointF levelCenter = new PointF(levelCenterX,levelCenterY);

        GameEngine.getInstance().setCurrentShipPosition(levelCenter);

    }
    private void loadLaserSound()
    {
        try {
            int sound = ContentManager.getInstance().loadSound("sounds/laser.mp3");
            GameEngine.getInstance().setSound(sound);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    private void loadShipBoundingBox()
    {
        //Get the ship center position
        GameEngine gameEngine = GameEngine.getInstance();


        PointF position = gameEngine.getCurrentShipPosition();

        //Get the width of the ship.
        PointF shipScale = Ship.myShip.getCurrentScale();
        float shipWidth = Ship.myShip.getMainBody().getImageWidth();
        float extraPartWidth = Ship.myShip.getExtraPart().getImageWidth();
        float cannonWidth = Ship.myShip.getCannon().getImageWidth();

        float width = (shipWidth + extraPartWidth + cannonWidth);

        //Get the height of the ship.
        float shipHeight = Ship.myShip.getMainBody().getImageHeight();
        float engineHeight = Ship.myShip.getEngine().getImageHeight();

        float height = (shipHeight + engineHeight);

        PointF widthAndHeight = new PointF(width,height);
        widthAndHeight = GraphicsUtils.scale(widthAndHeight,shipScale.x);


        float left = position.x - (widthAndHeight.x/2);
        float top = position.y - (widthAndHeight.y/2);
        float right = position.x + (widthAndHeight.x/2);
        float bottom = position.y + (widthAndHeight.y/2);

        gameEngine.setCurrentShipBB(new RectF(left, top, right, bottom));

    }
    private void loadStaticBackground(ContentManager content)
    {
        content.loadImage("images/space.bmp");
    }
    private void loadBackgrounds(ContentManager content)
    {
        //Load in a background image

        ArrayList<String> bgImages = new ArrayList<>();

        for(BackgroundObject bgObject: backgroundObjects)
        {
            //Get each image in the main bodies
            bgImages.add(bgObject.getObjectPath());
        }

        content.loadImages(bgImages);
    }
    private void loadAsteroids(ContentManager content)
    {
        //Load in an asteroid image
        GameEngine.getInstance().getAsteroids();
        ArrayList<String> asteroidsImages = new ArrayList<>();

        for(Asteroid asteroid: GameEngine.getInstance().getAsteroids())
        {
            //Get each image in the main bodies
            asteroidsImages.add(asteroid.getImage());
        }
        content.loadImages(asteroidsImages);

        ArrayList<LevelAsteroid> levelAsteroids = currentLevel.getLevelAsteroids();

        ArrayList<Asteroid> asteroids = new ArrayList<>();

        for(LevelAsteroid la : levelAsteroids)
        {
            int howManyAsteroidsToMake = la.getNumber();
            int whatTypeOfAsteroidToMake = la.getAsteroidId() - 1;

            Asteroid asteroid = GameEngine.getInstance().getAsteroids().get(whatTypeOfAsteroidToMake);

            int asteroidId = asteroid.getAsteroidId();
            String name = asteroid.getName();
            String image = asteroid.getImage();
            int width = asteroid.getImageWidth();
            int height = asteroid.getImageHeight();
            String type = asteroid.getType();


            for(int i = 0; i < howManyAsteroidsToMake; i++)
            {
                //make an asteroid a new asteroid depending on the type, and give it a random position as well
                if(type.equals("regular"))
                {
                    Asteroid r = new RegularAsteroid(asteroidId,name,image,width,height,type);
                    //Give them a random position
                    r.setPostion(generateRandomPosition());
                    r.setAsteroidBB(asteroid.initializeAsteroidBB(r));

                    //Generate key value
                    r.setKeyNumber(generateKey());
                    addToListOfKeyNumbers(r.getKeyNumber());

                    asteroids.add(r);
                }
                else if(type.equals("growing"))
                {
                    Asteroid g = new GrowingAsteroid(asteroidId,name,image,width,height,type);
                    //Give them a random position
                    g.setPostion(generateRandomPosition());
                    g.setAsteroidBB(asteroid.initializeAsteroidBB(g));

                    //Generate key value
                    g.setKeyNumber(generateKey());
                    addToListOfKeyNumbers(g.getKeyNumber());

                    asteroids.add(g);
                }
                else
                {
                    Asteroid o = new Octaroid(asteroidId,name,image,width,height,type);
                    //give them a random position
                    o.setPostion(generateRandomPosition());
                    o.setAsteroidBB(asteroid.initializeAsteroidBB(o));

                    //Generate key value
                    o.setKeyNumber(generateKey());
                    addToListOfKeyNumbers(o.getKeyNumber());

                    asteroids.add(o);
                }
            }

            setCurrentAsteroids(asteroids);

        }

    }

}


