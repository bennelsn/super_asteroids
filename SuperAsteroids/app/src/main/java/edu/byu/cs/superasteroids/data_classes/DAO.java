package edu.byu.cs.superasteroids.data_classes;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.model_classes.Asteroid;
import edu.byu.cs.superasteroids.model_classes.Cannon;
import edu.byu.cs.superasteroids.model_classes.Engines;
import edu.byu.cs.superasteroids.model_classes.ExtraPart;
import edu.byu.cs.superasteroids.model_classes.GrowingAsteroid;
import edu.byu.cs.superasteroids.model_classes.LevelAsteroid;
import edu.byu.cs.superasteroids.model_classes.LevelObject;
import edu.byu.cs.superasteroids.data_classes.dbOpenHelper;
import edu.byu.cs.superasteroids.model_classes.BackgroundObject;
import edu.byu.cs.superasteroids.model_classes.Levels;
import edu.byu.cs.superasteroids.model_classes.MainBody;
import edu.byu.cs.superasteroids.model_classes.Octaroid;
import edu.byu.cs.superasteroids.model_classes.PowerCore;
import edu.byu.cs.superasteroids.model_classes.RegularAsteroid;


/**
 * Created by BenNelson on 10/21/16.
 * This class is a DAO. It has a bunch of getters and setters to add to the database and send queries to it.
 */
public class DAO {


    //PRIVATE DATA MEMBERS
    private SQLiteDatabase db;
    private String[] EMPTY_ARRAY_OF_STRINGS = new String[0];
    private static final String mySQL_DROP_bgObjects = "DROP TABLE IF EXISTS backgroundObjects";
    private static final String mySQL_DROP_asteroids = "DROP TABLE IF EXISTS asteroids";
    private static final String mySQL_DROP_levels = "DROP TABLE IF EXISTS levels";
    private static final String mySQL_DROP_levelObjects = "DROP TABLE IF EXISTS levelObjects";
    private static final String mySQL_DROP_levelAsteroids = "DROP TABLE IF EXISTS levelAsteroids";
    private static final String mySQL_DROP_mainBodies = "DROP TABLE IF EXISTS mainBodies";
    private static final String mySQL_DROP_cannons = "DROP TABLE IF EXISTS cannons";
    private static final String mySQL_DROP_extraParts = "DROP TABLE IF EXISTS extraParts";
    private static final String mySQL_DROP_engines = "DROP TABLE IF EXISTS engines";
    private static final String mySQL_DROP_powerCores = "DROP TABLE IF EXISTS powerCores";
    private static final String mySQL_CREATE_backgroundObjects =
            "CREATE TABLE backgroundObjects " +
                    "("+
                    "objectId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +    //<----This is will be the ID selected by the levelObject
                    "objectPath TEXT NOT NULL" +
                    ")";

    private static final String mySQL_CREATE_asteroids =
            "CREATE TABLE asteroids " +
                    "("+
                    "asteroidId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +       //<----- REMEMBER this will be useful for the level to know what type of asteroid to create
                    "name TEXT NOT NULL," +
                    "image TEXT NOT NULL," +
                    "imageWidth INTEGER NOT NULL," +
                    "imageHeight INTEGER NOT NULL," +
                    "type TEXT NOT NULL" +
                    ")";
    private static final String mySQL_CREATE_levels =
            "CREATE TABLE levels " +
                    "("+
                    "number INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "title TEXT NOT NULL," +
                    "hint TEXT NOT NULL," +
                    "width INTEGER NOT NULL," +
                    "height INTEGER NOT NULL," +
                    "music TEXT NOT NULL"+
                    ")";
    private static final String mySQL_CREATE_levelObjects =
            "CREATE TABLE levelObjects " +
                    "("+
                    "position TEXT NOT NULL," +
                    "objectId INTEGER NOT NULL," +                          //<--- this states what background object to use.
                    "scale TEXT NOT NULL," +
                    "linkedLevel INTEGER NOT NULL," +                        //<--- REMEMBER it states what level it goes on
                    "FOREIGN KEY (linkedLevel) REFERENCES levels(number)" +
                    ")";
    private static final String mySQL_CREATE_levelAsteroids =
            "CREATE TABLE levelAsteroids "+
                    "(" +
                    "number INTEGER NOT NULL," +                             //<--- this states how many asteroids will be in the level
                    "asteroidId INTEGER NOT NULL," +                         //<--- this is what asteroid to use.
                    "linkedLevel INTEGER NOT NULL," +                        //<--- REMEMBER it states what level it goes on
                    "FOREIGN KEY (linkedLevel) REFERENCES levels(number)" +
                    ")";
    private static final String mySQL_CREATE_mainBodies =
            "CREATE TABLE mainBodies " +
                    "(" +
                    "cannonAttach TEXT NOT NULL," +
                    "engineAttach TEXT NOT NULL," +
                    "extraAttach TEXT NOT NULL," +
                    "image TEXT NOT NULL," +
                    "imageWidth INTEGER NOT NULL," +
                    "imageHeight INTEGER NOT NULL" +
                    ")";
    private static final String mySQL_CREATE_cannons =
            "CREATE TABLE cannons " +
                    "(" +
                    "attachPoint TEXT NOT NULL," +
                    "emitPoint TEXT NOT NULL," +
                    "image TEXT NOT NULL," +
                    "imageWidth INTEGER NOT NULL," +
                    "imageHeight INTEGER NOT NULL," +
                    "attackImage TEXT NOT NULL," +
                    "attackImageWidth INTEGER NOT NULL," +
                    "attackImageHeight INTEGER NOT NULL," +
                    "attackSound TEXT NOT NULL," +
                    "damage INTEGER NOT NULL" +
                    ")";
    private static final String mySQL_CREATE_extraParts =
            "CREATE TABLE extraParts " +
                    "(" +
                    "attachPoint TEXT NOT NULL," +
                    "image TEXT NOT NULL," +
                    "imageWidth INTEGER NOT NULL," +
                    "imageHeight INTEGER NOT NULL" +
                    ")";
    private static final String mySQL_CREATE_engines =
            "CREATE TABLE engines" +
                    "("+
                    "baseSpeed INTEGER NOT NULL,"+
                    "baseTurnRate INTEGER NOT NULL,"+
                    "attachPoint TEXT NOT NULL,"+
                    "image TEXT NOT NULL,"+
                    "imageWidth INTEGER NOT NULL,"+
                    "imageHeight INTEGER NOT NULL"+
                    ")";
    private static final String mySQL_CREATE_powerCores =
            "CREATE TABLE powerCores" +
                    "("+
                    "cannonBoost INTEGER NOT NULL,"+
                    "engineBoost INTEGER NOT NULL,"+
                    "image TEXT NOT NULL"+
                    ")";



    //GET INSTANCE CLASS (SINGLETON)
    private static DAO instance = null;
    protected DAO() {
        // Exists only to defeat instantiation.
    }
    public static DAO getInstance() {
        if(instance == null) {
            instance = new DAO();
        }
        return instance;
    }

    /**
     * This method will initialize the database because it is a "singleton" class, therefore the db could not be initialized during construction of the instance
     * @param database
     */
    public void initializeDatabase(SQLiteDatabase database)
    {
        db = database;
    }

    /**
     * This method will reintialize my database
     */
    public void reInitializeDB()
    {
        db.execSQL(mySQL_DROP_bgObjects);
        db.execSQL(mySQL_DROP_asteroids);
        db.execSQL(mySQL_DROP_levels);
        db.execSQL(mySQL_DROP_levelObjects);
        db.execSQL(mySQL_DROP_levelAsteroids);
        db.execSQL(mySQL_DROP_mainBodies);
        db.execSQL(mySQL_DROP_cannons);
        db.execSQL(mySQL_DROP_extraParts);
        db.execSQL(mySQL_DROP_engines);
        db.execSQL(mySQL_DROP_powerCores);

        db.execSQL(mySQL_CREATE_backgroundObjects);
        db.execSQL(mySQL_CREATE_asteroids);
        db.execSQL(mySQL_CREATE_levels);
        db.execSQL(mySQL_CREATE_levelObjects);
        db.execSQL(mySQL_CREATE_levelAsteroids);
        db.execSQL(mySQL_CREATE_mainBodies);
        db.execSQL(mySQL_CREATE_cannons);
        db.execSQL(mySQL_CREATE_extraParts);
        db.execSQL(mySQL_CREATE_engines);
        db.execSQL(mySQL_CREATE_powerCores);
    }

    /**
     * This metheod will insert a background object into the database
     * @param objectId
     * @param objectPath
     */
    public void insertBackgroundObject(int objectId, String objectPath)
    {
        ContentValues values = new ContentValues();
        values.put("objectId", objectId);
        values.put("objectPath", objectPath);

        db.insert("backgroundObjects", null, values);
    }

    /**
     * This method will get a list of my background objects
     * @return ArrayList<BackgroundObject>
     */
    public ArrayList<BackgroundObject> getBackgroundObjects()
    {
        ArrayList<BackgroundObject> backgroundObjects = new ArrayList<>();

        Cursor my_cursor = db.rawQuery("SELECT * FROM backgroundObjects", EMPTY_ARRAY_OF_STRINGS);
        try {
            my_cursor.moveToFirst();

            while (!(my_cursor.isAfterLast()))
            {
                int objectId = my_cursor.getInt(0);
                String objectPath = my_cursor.getString(1);

                BackgroundObject bgObject = new BackgroundObject(objectId, objectPath);

                backgroundObjects.add(bgObject);
                my_cursor.moveToNext();
            }
        }
        finally
        {
            my_cursor.close();
        }

        return backgroundObjects;
    }

    /**
     * TThis metheod will insert a asteroid into the database
     * @param asteroidId
     * @param name
     * @param image_path
     * @param width
     * @param height
     * @param type
     */
    public void insertAsteroid(int asteroidId, String name, String image_path, int width, int height, String type)
    {
        ContentValues values = new ContentValues(); //Like a row

        values.put("asteroidId", asteroidId);
        values.put("name", name);
        values.put("image", image_path);
        values.put("imageWidth", width);
        values.put("imageHeight", height);
        values.put("type", type);


        //Make a table called "asteroids" and put in the values
        db.insert("asteroids", null, values);

        //IF THESE DON'T EXIST will a new one be created?
        //IF they do exist, will they be over-writed?

    }

    /**
     * This method will get all the asteroids from the database
     *
     * @return ArrayList<Asteroid>
     */
    public ArrayList<Asteroid> getAsteroids()
    {
        ArrayList<Asteroid> asteroids = new ArrayList<>();

        Cursor my_cursor = db.rawQuery("SELECT * FROM asteroids", EMPTY_ARRAY_OF_STRINGS);
        my_cursor.moveToFirst();

        try {
            while (!(my_cursor.isAfterLast())) {
                int asteroidId = my_cursor.getInt(0);
                String name = my_cursor.getString(1);
                String image = my_cursor.getString(2);
                int imageWidth = my_cursor.getInt(3);
                int imageHeight = my_cursor.getInt(4);
                String type = my_cursor.getString(5);

                Asteroid asteroid = null;

                if (type.equals("regular")) {
                    asteroid = new RegularAsteroid(asteroidId, name, image, imageWidth, imageHeight, type);
                } else if (type.equals("growing")) {
                    asteroid = new GrowingAsteroid(asteroidId, name, image, imageWidth, imageHeight, type);
                } else //type is an "octaroid"
                {
                    asteroid = new Octaroid(asteroidId, name, image, imageWidth, imageHeight, type);
                }

                asteroids.add(asteroid);
                my_cursor.moveToNext();
            }
        }
        finally {

            my_cursor.close();
        }
        return asteroids;
    }

    /**
     * This metheod will a level into the database
     * @param number
     * @param title
     * @param hint
     * @param width
     * @param height
     * @param music_path
     * @param allLevelObjects
     * @param allLevelAsteroids
     */
    public void insertLevel(int number, String title, String hint, int width, int height, String music_path, ArrayList<LevelObject> allLevelObjects, ArrayList<LevelAsteroid> allLevelAsteroids)
    {
        ContentValues values = new ContentValues(); //Like a row
        values.put("number", number);
        values.put("title", title);
        values.put("hint", hint);
        values.put("width", width);
        values.put("height", height);
        values.put("music", music_path);

        //Create the basis for the level
        db.insert("levels", null, values);

        //Now insert the levelObjects and the levelAsteroids to their tables in the SQLite Database

        insertLevelObjects(allLevelObjects);
        insertLevelAsteroids(allLevelAsteroids);
    }

    /**
     * This will get the list of all levels
     * @return ArrayList<Levels>
     */
    public ArrayList<Levels> getLevels() {
        ArrayList<Levels> levels = new ArrayList<>();
        int levelCounter = 1;

        Cursor my_cursor = db.rawQuery("SELECT * FROM levels", EMPTY_ARRAY_OF_STRINGS);
        my_cursor.moveToFirst();

        try {
            while (!(my_cursor.isAfterLast())) {
                int number = my_cursor.getInt(0);
                String title = my_cursor.getString(1);
                String hint = my_cursor.getString(2);
                int width = my_cursor.getInt(3);
                int height = my_cursor.getInt(4);
                String music = my_cursor.getString(5);

                //GET LO & LA lists
                ArrayList<LevelObject> levelObjects = getLevelObjects(levelCounter);
                ArrayList<LevelAsteroid> levelAsteroids = getLevelAsteroids(levelCounter);


                Levels level = new Levels(number, title, hint, width, height, music, levelObjects, levelAsteroids);

                levels.add(level);

                levelCounter++;
                my_cursor.moveToNext();

            }
        }
        finally {
            my_cursor.close();
        }

        return levels;
    }

    /**
     * This metheod will insert all the level objects to one level into the database
     * @param allLevelObjects
     */
    private void insertLevelObjects(ArrayList<LevelObject> allLevelObjects)
    {
        //Go through each element in the array list and get each object
        for(int i = 0; i < allLevelObjects.size(); i++)
        {
            LevelObject current_lo = allLevelObjects.get(i);
            String position = current_lo.getPosition();
            int objectId = current_lo.getObjectId();
            String scale = current_lo.getScale();
            int linkedLevel = current_lo.getLinkedLevel();

            ContentValues values = new ContentValues(); //Like a row
            values.put("position", position);
            values.put("objectId", objectId);
            values.put("scale", scale);
            values.put("linkedLevel", linkedLevel);

            db.insert("levelObjects", null, values);
        }
    }

    /**
     * This will get all the level objects
     * @return ArrayList<LevelObject>
     * @param levelCounter
     */
    public ArrayList<LevelObject> getLevelObjects(int levelCounter)
    {
        ArrayList<LevelObject> levelObjects = new ArrayList<>();

        Cursor my_cursor = db.rawQuery("SELECT * FROM levelObjects", EMPTY_ARRAY_OF_STRINGS);
        my_cursor.moveToFirst();
        try {
            while (!(my_cursor.isAfterLast())) {
                String position = my_cursor.getString(0);
                int objectId = my_cursor.getInt(1);
                String scale = my_cursor.getString(2);
                int linkedLevel = my_cursor.getInt(3);

                if(linkedLevel == levelCounter)
                {
                    LevelObject lo = new LevelObject(position, objectId, scale, linkedLevel);
                    levelObjects.add(lo);
                }

                my_cursor.moveToNext();
            }
        }
        finally {
            my_cursor.close();
        }

        return levelObjects;
    }

    /**
     * This metheod will insert all the level asteroids to one level into the database
     * @param allLevelAsteroids
     */
    private void insertLevelAsteroids(ArrayList<LevelAsteroid> allLevelAsteroids)
    {
        //Go through each element in the array list and get each object
        for(int i = 0; i < allLevelAsteroids.size(); i++)
        {
            LevelAsteroid current_la = allLevelAsteroids.get(i);
            int number = current_la.getNumber();
            int asteroidId = current_la.getAsteroidId();
            int linkedLevel = current_la.getLinkedLevel();

            ContentValues values = new ContentValues(); //Like a row
            values.put("number", number);
            values.put("asteroidId", asteroidId);
            values.put("linkedLevel", linkedLevel);

            db.insert("levelAsteroids", null, values);
        }
    }

    public ArrayList<LevelAsteroid> getLevelAsteroids(int levelCounter)
    {
        ArrayList<LevelAsteroid> levelAsteroids = new ArrayList<>();

        Cursor my_cursor = db.rawQuery("SELECT * FROM levelAsteroids", EMPTY_ARRAY_OF_STRINGS);
        my_cursor.moveToFirst();

        try {
            while (!(my_cursor.isAfterLast())) {
                int number = my_cursor.getInt(0);
                int asteroidId = my_cursor.getInt(1);
                int linkedLevel = my_cursor.getInt(2);

                if(linkedLevel == levelCounter)
                {
                    LevelAsteroid la = new LevelAsteroid(number, asteroidId, linkedLevel);
                    levelAsteroids.add(la);
                }

                my_cursor.moveToNext();
            }
        }
        finally {
            my_cursor.close();
        }
        return levelAsteroids;
    }

        /**
         * This metheod will insert a main body into the database
         * @param cannonAttach
         * @param engineAttach
         * @param extraAttach
         * @param image_path
         * @param width
         * @param height
         */
    public void insertMainBody(String cannonAttach, String engineAttach, String extraAttach, String image_path, int width, int height)
    {
        ContentValues values = new ContentValues(); //Like a row

        values.put("cannonAttach", cannonAttach);
        values.put("engineAttach", engineAttach);
        values.put("extraAttach", extraAttach);
        values.put("image", image_path);
        values.put("imageWidth", width);
        values.put("imageHeight", height);

        //Make a table called "mainBodies" and put in the values
        db.insert("mainBodies", null, values);
    }

    public ArrayList<MainBody> getMainBodies()
    {
        ArrayList<MainBody> mainBodies = new ArrayList<>();

        Cursor my_cursor = db.rawQuery("SELECT * FROM mainBodies", EMPTY_ARRAY_OF_STRINGS);
        my_cursor.moveToFirst();

        try {
            while (!(my_cursor.isAfterLast())) {
                String cannonAttach = my_cursor.getString(0);
                String engineAttach = my_cursor.getString(1);
                String extraAttach = my_cursor.getString(2);
                String image = my_cursor.getString(3);
                int imageWidth = my_cursor.getInt(4);
                int imageHeight = my_cursor.getInt(5);

                MainBody mb = new MainBody(cannonAttach, engineAttach, extraAttach, image, imageWidth, imageHeight);

                mainBodies.add(mb);
                my_cursor.moveToNext();
            }
        }
        finally {
            my_cursor.close();
        }
        return mainBodies;
    }

    /**
     * This metheod will insert a cannon into the database
     * @param attachPoint
     * @param emitPoint
     * @param image_path
     * @param width
     * @param height
     * @param attackImage_path
     * @param attackImage_width
     * @param attackImage_height
     * @param attackSound_path
     * @param damage
     */
    public void insertCannon(String attachPoint, String emitPoint, String image_path, int width, int height, String attackImage_path, int attackImage_width, int attackImage_height, String attackSound_path, int damage)
    {
        ContentValues values = new ContentValues(); //Like a row

        values.put("attachPoint", attachPoint);
        values.put("emitPoint", emitPoint);
        values.put("image", image_path);
        values.put("imageWidth", width);
        values.put("imageHeight", height);
        values.put("attackImage", attackImage_path);
        values.put("attackImageWidth", attackImage_width);
        values.put("attackImageHeight", attackImage_height);
        values.put("attackSound", attackSound_path);
        values.put("damage", damage);

        db.insert("cannons", null, values);
    }

    public ArrayList<Cannon> getCannons()
    {
        ArrayList<Cannon> cannons = new ArrayList<>();

        Cursor my_cursor = db.rawQuery("SELECT * FROM cannons", EMPTY_ARRAY_OF_STRINGS);
        my_cursor.moveToFirst();

        try {
            while (!(my_cursor.isAfterLast())) {
                String attachPoint = my_cursor.getString(0);
                String emitPoint = my_cursor.getString(1);
                String image = my_cursor.getString(2);
                int imageWidth = my_cursor.getInt(3);
                int imageHeight = my_cursor.getInt(4);
                String attackImage = my_cursor.getString(5);
                int attackImageWidth = my_cursor.getInt(6);
                int attackImageHeight = my_cursor.getInt(7);
                String attackSound = my_cursor.getString(8);
                int damage = my_cursor.getInt(9);

                Cannon cannon = new Cannon(attachPoint, emitPoint, image, imageWidth, imageHeight, attackImage, attackImageWidth, attackImageHeight, attackSound, damage);

                cannons.add(cannon);
                my_cursor.moveToNext();

            }
        }
        finally{
          my_cursor.close();
        }

        return cannons;
    }

    /**
     * This metheod will insert an extra part into the database
     * @param attachPoint
     * @param image_path
     * @param width
     * @param height
     */
    public void insertExtraPart(String attachPoint, String image_path, int width, int height)
    {
        ContentValues values = new ContentValues(); //Like a row

        values.put("attachPoint", attachPoint);
        values.put("image", image_path);
        values.put("imageWidth", width);
        values.put("imageHeight", height);

        db.insert("extraParts" , null, values);
    }

    public ArrayList<ExtraPart> getExtraParts()
    {
        ArrayList<ExtraPart> extraParts = new ArrayList<>();

        Cursor my_cursor = db.rawQuery("SELECT * FROM extraParts", EMPTY_ARRAY_OF_STRINGS);
        my_cursor.moveToFirst();

        try {
            while (!(my_cursor.isAfterLast())) {
                String attachPoint = my_cursor.getString(0);
                String image = my_cursor.getString(1);
                int imageWidth = my_cursor.getInt(2);
                int imageHeight = my_cursor.getInt(3);

                ExtraPart ep = new ExtraPart(attachPoint, image, imageWidth, imageHeight);

                extraParts.add(ep);
                my_cursor.moveToNext();
            }
        }
        finally {
            my_cursor.close();
        }
        return extraParts;
    }

    /**
     * This metheod will insert an engine into the database
     * @param baseSpeed
     * @param baseTurnRate
     * @param attachPoint
     * @param image_path
     * @param width
     * @param height
     */
    public void insertEngine(int baseSpeed, int baseTurnRate, String attachPoint, String image_path, int width, int height)
    {
        ContentValues values = new ContentValues(); //Like a row

        values.put("baseSpeed", baseSpeed);
        values.put("baseTurnRate", baseTurnRate);
        values.put("attachPoint", attachPoint);
        values.put("image", image_path);
        values.put("imageWidth", width);
        values.put("imageHeight", height);

        db.insert("engines" , null, values);
    }

    public ArrayList<Engines> getEngines()
    {
        ArrayList<Engines> engines = new ArrayList<>();

        Cursor my_cursor = db.rawQuery("SELECT * FROM engines", EMPTY_ARRAY_OF_STRINGS);
        my_cursor.moveToFirst();

        try {
            while (!(my_cursor.isAfterLast())) {
                int baseSpeed = my_cursor.getInt(0);
                int baseTurnRate = my_cursor.getInt(1);
                String attachPoint = my_cursor.getString(2);
                String image = my_cursor.getString(3);
                int imageWidth = my_cursor.getInt(4);
                int imageHeight = my_cursor.getInt(5);

                Engines engine = new Engines(baseSpeed, baseTurnRate, attachPoint, image, imageWidth, imageHeight);

                engines.add(engine);
                my_cursor.moveToNext();
            }
        }
        finally {
            my_cursor.close();
        }
        return engines;
    }
    /**
     * This metheod will insert a power core into the database
     * @param cannonBoost
     * @param engineBoost
     * @param image_path
     */
    public void insertPowerCore(int cannonBoost, int engineBoost, String image_path)
    {
        ContentValues values = new ContentValues(); //Like a row
        values.put("cannonBoost", cannonBoost);
        values.put("engineBoost", engineBoost);
        values.put("image", image_path);

        db.insert("powerCores" , null, values);

    }
    public ArrayList<PowerCore> getPowerCores()
    {
        ArrayList<PowerCore> powerCores = new ArrayList<>();

        Cursor my_cursor = db.rawQuery("SELECT * FROM powerCores", EMPTY_ARRAY_OF_STRINGS);
        my_cursor.moveToFirst();

        try {
            while (!(my_cursor.isAfterLast())) {
                int cannonBoost = my_cursor.getInt(0);
                int engineBoost = my_cursor.getInt(1);
                String image = my_cursor.getString(2);

                PowerCore powerCore = new PowerCore(cannonBoost, engineBoost, image);

                powerCores.add(powerCore);
                my_cursor.moveToNext();
            }
        }
        finally {
            my_cursor.close();
        }
        return powerCores;
    }


}
