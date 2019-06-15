package edu.byu.cs.superasteroids.importer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import edu.byu.cs.superasteroids.data_classes.DAO;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.model_classes.GameEngine;
import edu.byu.cs.superasteroids.model_classes.LevelObject;
import edu.byu.cs.superasteroids.model_classes.LevelAsteroid;


/**
 * Created by BenNelson on 10/18/16.
 * This class implements IGameDataImporter
 */
public class MyDataImportController implements IGameDataImporter
{

    //Get an instance of the DAO in order to access the databasep
    DAO dao = DAO.getInstance();

    /**
     * Imports the data from the .json file the given InputStreamReader is connected to. Imported data
     * should be stored in a SQLite database for use in the ship builder and the game.
     * @param dataInputReader The InputStreamReader connected to the .json file needing to be imported.
     * @return TRUE if the data was imported successfully, FALSE if the data was not imported due
     * to any error.
     */
    @Override
    public boolean importData(InputStreamReader dataInputReader) throws JSONException {



        //Make JSON Object with DATA in it.
        try
        {
            //BEFORE IMPORTING NEW DATA, we need to clear the database
            dao.reInitializeDB();

            String data_JSON = convertISRtoString(dataInputReader);

            JSONObject file = new JSONObject(data_JSON);

            JSONObject asteroidsGame = file.getJSONObject("asteroidsGame");
            JSONArray objects = asteroidsGame.getJSONArray("objects");
            importObjects(objects);

            JSONArray asteroids = asteroidsGame.getJSONArray("asteroids");
            importAsteroids(asteroids);

            JSONArray levels = asteroidsGame.getJSONArray("levels");
            importLevels(levels);

            JSONArray mainBodies = asteroidsGame.getJSONArray("mainBodies");
            importMainBodies(mainBodies);

            JSONArray cannons = asteroidsGame.getJSONArray("cannons");
            importCannons(cannons);

            JSONArray extraParts = asteroidsGame.getJSONArray("extraParts");
            importExtraParts(extraParts);

            JSONArray engines = asteroidsGame.getJSONArray("engines");
            importEngines(engines);

            JSONArray powerCores = asteroidsGame.getJSONArray("powerCores");
            importPowerCores(powerCores);



        }
        catch (JSONException e)
        {throw e;}


        //Populate
        GameEngine game_engine = GameEngine.getInstance();
        game_engine.populate();

        return true;
    }

    private String convertISRtoString(InputStreamReader dataInputReader)
    {
        StringBuilder sb = new StringBuilder();
        BufferedReader file = new BufferedReader(dataInputReader);

        try{
            String current_line = "";
            do{
                current_line = file.readLine();
                sb.append(current_line);

            }while (current_line!=null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        //When thats all done, return the string representation of the string builder object
        return sb.toString();
    }

    /**
     * This method will help import the objects
     * @param objects
     * @throws JSONException
     */
    public void importObjects(JSONArray objects) throws JSONException {

        for(int i =0; i < objects.length(); i++)
        {
            int objectId = (i+1);
            //ex: "1" because each path will be a new background object
            String objectPath = objects.getString(i);
            //ex: "images/planet1.png"

            dao.insertBackgroundObject(objectId,objectPath);
        }
    }

    /**
     * This method will help import the asteroids
     * @param asteroids
     * @throws JSONException
     */
    public void importAsteroids(JSONArray asteroids) throws JSONException
    {
        for(int i =0; i < asteroids.length(); i++)
        {
            JSONObject asteroid = asteroids.getJSONObject(i);
            int asteroidId = (i+1); //We are just numbering the asteroids here
            String name = asteroid.getString("name");
            String image_path = asteroid.getString("image");
            int width = asteroid.getInt("imageWidth");
            int height = asteroid.getInt("imageHeight");
            String type = asteroid.getString("type");

            //INSERT
            dao.insertAsteroid(asteroidId,name,image_path,width,height,type);
        }
    }

    /**
     * This method will help import the levels
     * @param levels
     * @throws JSONException
     */
    public void importLevels(JSONArray levels) throws JSONException
    {
        for(int i =0; i < levels.length(); i++)
        {
            int level_number = (i+1);
            //ONE LEVEL HAS:
            //Parameters
            //Level Objects
            //Level Asteroids

            //Parameters
            JSONObject level = levels.getJSONObject(i);
            int number = level.getInt("number");
            String title = level.getString("title");
            String hint = level.getString("hint");
            int width = level.getInt("width");
            int height = level.getInt("height");
            String music_path = level.getString("music");

            //Level Objects
            JSONArray levelObjects = level.getJSONArray("levelObjects");
            //We want to pass in the correct level number and tag it to each of the objects in the level
            ArrayList<LevelObject> allLevelObjects = getLevelObjects(levelObjects, level_number);

            //Level Asteroids
            JSONArray levelAsteroids = level.getJSONArray("levelAsteroids");
            //We want to pass in the correct level number and tag it to each of the asteroids in the level
            ArrayList<LevelAsteroid> allLevelAsteroids = getLevelAsteroids(levelAsteroids, level_number);

            dao.insertLevel(number,title,hint,width,height,music_path,allLevelObjects,allLevelAsteroids);



        }

    }

    /**
     *
     *
     * @param levelObjects
     * @return ArrayList<LevelObjec>
     * @throws JSONException
     */
    public ArrayList<LevelObject> getLevelObjects(JSONArray levelObjects, int level_number) throws JSONException
    {
        ArrayList<LevelObject> lo_list = new ArrayList<>();
        for(int i = 0; i< levelObjects.length(); i++)
        {
            JSONObject levelObject = levelObjects.getJSONObject(i);
            String position = levelObject.getString("position");
            int objectId = levelObject.getInt("objectId");
            String scale = levelObject.getString("scale");
            int linkedLevel = level_number;

            LevelObject lo = new LevelObject(position,objectId,scale,linkedLevel);
            lo_list.add(lo);
        }
        return lo_list;
    }

    /**
     * This method will help with the import by getting all the levelAsteroids for one level
     * @param levelAsteroids
     * @return
     * @throws JSONException
     */
    public ArrayList<LevelAsteroid> getLevelAsteroids(JSONArray levelAsteroids, int level_number) throws JSONException
    {
        ArrayList<LevelAsteroid> la_list = new ArrayList<>();
        for(int i = 0; i< levelAsteroids.length(); i++)
        {
            JSONObject levelAsteroid = levelAsteroids.getJSONObject(i);
            int number = levelAsteroid.getInt("number");
            int asteroidId = levelAsteroid.getInt("asteroidId");
            int linkedLevel = level_number;

            LevelAsteroid la = new LevelAsteroid(number,asteroidId,linkedLevel);
            la_list.add(la);

        }
        return la_list;
    }

    /**
     * This method will help import the mainbodies
     * @param mainBodies
     * @throws JSONException
     */
    public void importMainBodies(JSONArray mainBodies) throws JSONException
    {
        for(int i =0; i < mainBodies.length(); i++)
        {
            JSONObject mainBody = mainBodies.getJSONObject(i);
            String cannonAttach = mainBody.getString("cannonAttach");
            String engineAttach = mainBody.getString("engineAttach");
            String extraAttach = mainBody.getString("extraAttach");
            String image_path = mainBody.getString("image");
            int width = mainBody.getInt("imageWidth");
            int height = mainBody.getInt("imageHeight");

            //INSERT
            dao.insertMainBody(cannonAttach,engineAttach,extraAttach,image_path,width,height);
        }
    }

    /**
     * This method will help import the cannons
     * @param cannons
     * @throws JSONException
     */
    public void importCannons(JSONArray cannons) throws JSONException
    {
        for(int i =0; i< cannons.length(); i++)
        {
            JSONObject cannon = cannons.getJSONObject(i);
            String attachPoint = cannon.getString("attachPoint");
            String emitPoint = cannon.getString("emitPoint");
            String image_path = cannon.getString("image");
            int width = cannon.getInt("imageWidth");
            int height = cannon.getInt("imageHeight");
            String attachImage_path = cannon.getString("attackImage");
            int attachImage_width = cannon.getInt("attackImageWidth");
            int attachImage_height = cannon.getInt("attackImageHeight");
            String attachSound_path = cannon.getString("attackSound");
            int damage = cannon.getInt("damage");

            //INSERT
            dao.insertCannon(attachPoint,emitPoint,image_path,width,height,attachImage_path,attachImage_width,attachImage_height,attachSound_path,damage);
        }
    }

    /**
     * This method will help import the extraParts
     * @param extraParts
     * @throws JSONException
     */
    public void importExtraParts(JSONArray extraParts) throws JSONException
    {
        for(int i =0; i< extraParts.length(); i++)
        {
            JSONObject extraPart = extraParts.getJSONObject(i);
            String attachPoint = extraPart.getString("attachPoint");
            String image_path = extraPart.getString("image");
            int width = extraPart.getInt("imageWidth");
            int height = extraPart.getInt("imageHeight");

            //INSERT
            dao.insertExtraPart(attachPoint,image_path,width,height);
        }
    }

    /**
     * This method will help import the engines
     * @param engines
     * @throws JSONException
     */
    public void importEngines(JSONArray engines) throws JSONException
    {
        for(int i =0; i< engines.length(); i++)
        {
            JSONObject engine = engines.getJSONObject(i);
            int baseSpeed = engine.getInt("baseSpeed");
            int baseTurnRate = engine.getInt("baseTurnRate");
            String attachPoint = engine.getString("attachPoint");
            String image_path = engine.getString("image");
            int width = engine.getInt("imageWidth");
            int height = engine.getInt("imageHeight");

            //INSERT
            dao.insertEngine(baseSpeed,baseTurnRate,attachPoint,image_path,width,height);
        }
    }

    /**
     * This method will help import the powerCores
     * @param powerCores
     * @throws JSONException
     */
    public void importPowerCores(JSONArray powerCores) throws JSONException
    {
        for(int i =0; i< powerCores.length(); i++)
        {
            JSONObject powerCore = powerCores.getJSONObject(i);
            int cannonBoost = powerCore.getInt("cannonBoost");
            int engineBoost = powerCore.getInt("engineBoost");
            String image_path = powerCore.getString("image");

            //INSERT
            dao.insertPowerCore(cannonBoost,engineBoost,image_path);
        }
    }








}




//get all information out of level
                /*JSONObject level = levels.getJSONObject(i);
                int n = level.getInt("number");
                String t = level.getString("title");
                String h = level.getString("hint");
                Level l = new Level(n,t,h);
                dao.s.insert(l);
                */

                /*
                JSONArray level_object = levels.getJSONArray("LO");
                ArrayList<LO> los = new ArrayList<>();
                for...
                {
                    String postion = lo.getSTring("pos");

                    String[]  pos = pos_x.split(",");
                    int pos_x = Integer.parseInt(pos[0]);
                    int pos_y = Integer.parseInt(pos[1]);
                    level object = new lo(posx, posy, obj id, scale..);
                    los.add(lo);
                    //do same for levelasteroids
                    level L = new level(n,t,h,lobjects,lasteroids);

                } */