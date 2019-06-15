package edu.byu.cs.superasteroids.data_classes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by BenNelson on 10/21/16.
 * This class extends the SQliteOpenHelper in an effort to connect the DAO to the database
 */
public class dbOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "myGameDB.sqlite";
    private static final int DB_VERSION = 1;

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

    public dbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    /**
     * This method will create the SQlite database
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
