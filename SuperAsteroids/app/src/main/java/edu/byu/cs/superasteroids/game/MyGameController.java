package edu.byu.cs.superasteroids.game;

import android.graphics.PointF;
import android.graphics.RectF;

import java.io.IOException;
import java.util.ArrayList;

import edu.byu.cs.superasteroids.base.IGameDelegate;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.model_classes.Asteroid;
import edu.byu.cs.superasteroids.model_classes.BackgroundObject;
import edu.byu.cs.superasteroids.model_classes.GameEngine;
import edu.byu.cs.superasteroids.model_classes.GrowingAsteroid;
import edu.byu.cs.superasteroids.model_classes.LevelAsteroid;
import edu.byu.cs.superasteroids.model_classes.Levels;
import edu.byu.cs.superasteroids.model_classes.Octaroid;
import edu.byu.cs.superasteroids.model_classes.RegularAsteroid;
import edu.byu.cs.superasteroids.model_classes.Ship;

/**
 * Created by BenNelson on 10/18/16.
 */
public class MyGameController implements IGameDelegate {

    /**
     * Updates the game delegate. The game engine will call this function 60 times a second
     * once it enters the game loop.
     * @param elapsedTime Time since the last update. For this game, elapsedTime is always
     *                    1/60th of second
     */
    @Override
    public void update(double elapsedTime)
    {

        GameEngine gameEngine = GameEngine.getInstance();

        gameEngine.update(elapsedTime);
    }

    /**
     * Loads content such as image and sounds files and other data into the game. The GameActivty will
     * call this once right before entering the game engine enters the game loop. The ShipBuilding
     * activity calls this function in its onCreate() function.
     * @param content An instance of the content manager. This should be used to load images and sound
     *                files.
     */
    @Override
    public void loadContent(ContentManager content) {

        //Well we have loaded in most of the stuff so I will come back to this later if necessary
        GameEngine gameEngine = GameEngine.getInstance();
        ViewPort viewPort = ViewPort.getInstance();

        gameEngine.load(content);


    }

    /**
     * Unloads content from the game. The GameActivity will call this function after the game engine
     * exits the game loop. The ShipBuildingActivity will call this function after the "Start Game"
     * button has been pressed.
     * @param content An instance of the content manager. This should be used to unload image and
     *                sound files.
     */
    @Override
    public void unloadContent(ContentManager content) {

    }

    /**
     * Draws the game delegate. This function will be 60 times a second.
     */
    @Override
    public void draw()
    {
        GameEngine gameEngine = GameEngine.getInstance();

        gameEngine.draw();
    }


}
