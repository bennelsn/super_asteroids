package edu.byu.cs.superasteroids.ship_builder;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.base.IView;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.model_classes.Cannon;
import edu.byu.cs.superasteroids.model_classes.Engines;
import edu.byu.cs.superasteroids.model_classes.ExtraPart;
import edu.byu.cs.superasteroids.model_classes.GameEngine;
import edu.byu.cs.superasteroids.model_classes.MainBody;
import edu.byu.cs.superasteroids.model_classes.PowerCore;
import edu.byu.cs.superasteroids.model_classes.Ship;
import edu.byu.cs.superasteroids.ship_builder.IShipBuildingView.PartSelectionView;

import static edu.byu.cs.superasteroids.ship_builder.IShipBuildingView.PartSelectionView.*;


/**
 * Created by BenNelson on 10/18/16.
 * This class implements the IShipBuilderController
 */
 public class MyShipBuildingController implements IShipBuildingController
{
    private ShipBuildingActivity sba;
    private PartSelectionView state;

    public MyShipBuildingController(ShipBuildingActivity activity) { //<-- should be ShipBuilding activity

        //this.sba = sba;
        //state = mainbody (PARTSELECTION VIEW)
        this.sba = activity;
        this.state = MAIN_BODY;
    }

    @Override
    public void onViewLoaded(PartSelectionView partView) {

        state = partView;
        switch(state)
        {
            //  ( ...Powercore <->)extraPart <-> cannon <-> MainBody <-> engine <-> Powercore( <-> extraPart <-> cannon....)

            case MAIN_BODY:
                sba.setArrow(MAIN_BODY, IShipBuildingView.ViewDirection.RIGHT, true, "Engine");
                sba.setArrow(MAIN_BODY, IShipBuildingView.ViewDirection.LEFT, true, "Cannon");
                sba.setArrow(MAIN_BODY, IShipBuildingView.ViewDirection.UP, false, ""); // UP
                sba.setArrow(MAIN_BODY, IShipBuildingView.ViewDirection.DOWN, false, ""); // DOWN

                break;
            case CANNON:
                sba.setArrow(CANNON, IShipBuildingView.ViewDirection.RIGHT, true, "Main Body");
                sba.setArrow(CANNON, IShipBuildingView.ViewDirection.LEFT, true, "Extra Part");
                sba.setArrow(CANNON, IShipBuildingView.ViewDirection.UP, false, ""); // UP
                sba.setArrow(CANNON, IShipBuildingView.ViewDirection.DOWN, false, ""); // DOWN

                break;
            case EXTRA_PART:
                sba.setArrow(EXTRA_PART, IShipBuildingView.ViewDirection.RIGHT, true, "Cannon");
                sba.setArrow(EXTRA_PART, IShipBuildingView.ViewDirection.LEFT, true, "Power Core");
                sba.setArrow(EXTRA_PART, IShipBuildingView.ViewDirection.UP, false, ""); // UP
                sba.setArrow(EXTRA_PART, IShipBuildingView.ViewDirection.DOWN, false, ""); // DOWN

                break;
            case ENGINE:
                sba.setArrow(ENGINE, IShipBuildingView.ViewDirection.RIGHT, true, "Power Core");
                sba.setArrow(ENGINE, IShipBuildingView.ViewDirection.LEFT, true, "Main Body");
                sba.setArrow(ENGINE, IShipBuildingView.ViewDirection.UP, false, ""); // UP
                sba.setArrow(ENGINE, IShipBuildingView.ViewDirection.DOWN, false, ""); // DOWN

                break;
            case POWER_CORE:
                sba.setArrow(POWER_CORE, IShipBuildingView.ViewDirection.RIGHT, true, "Extra Part");
                sba.setArrow(POWER_CORE, IShipBuildingView.ViewDirection.LEFT, true, "Engine");
                sba.setArrow(POWER_CORE, IShipBuildingView.ViewDirection.UP, false, ""); // UP
                sba.setArrow(POWER_CORE, IShipBuildingView.ViewDirection.DOWN, false, ""); // DOWN

                break;
        }

    }

    @Override
    public void update(double elapsedTime) {

        if(Ship.myShip.isComplete())
        {
            sba.setStartGameButton(true);
        }

    }

    @Override
    public void loadContent(ContentManager content) {

        GameEngine gameEngine = GameEngine.getInstance();
        gameEngine.populate();

        //MAIN BODIES
        ArrayList<String> mainBodyImages = new ArrayList<>();

        for(MainBody mainBody: gameEngine.getMainBodies())
        {
            //Get each image in the main bodies
            mainBodyImages.add(mainBody.getImage());
        }

        sba.setPartViewImageList(MAIN_BODY,content.loadImages(mainBodyImages));

        //CANNONS
        ArrayList<String> cannonImages = new ArrayList<>();

        for(Cannon cannon: gameEngine.getCannons())
        {
            //Get each image in the main bodies
            cannonImages.add(cannon.getImage());
        }

        sba.setPartViewImageList(CANNON,content.loadImages(cannonImages));

        //ENGINES
        ArrayList<String> engineImages = new ArrayList<>();

        for(Engines engine: gameEngine.getEngines())
        {
            //Get each image in the main bodies
            engineImages.add(engine.getImage());
        }

        sba.setPartViewImageList(ENGINE,content.loadImages(engineImages));

        //EXTRAPARTS
        ArrayList<String> extraPartImages = new ArrayList<>();

        for(ExtraPart extraPart: gameEngine.getExtraParts())
        {
            //Get each image in the main bodies
            extraPartImages.add(extraPart.getImage());
        }

        sba.setPartViewImageList(EXTRA_PART,content.loadImages(extraPartImages));

        //POWERCORES
        ArrayList<String> powerCoreImages = new ArrayList<>();

        for(PowerCore powerCore: gameEngine.getPowerCores())
        {
            //Get each image in the main bodies
            powerCoreImages.add(powerCore.getImage());
        }

        sba.setPartViewImageList(POWER_CORE,content.loadImages(powerCoreImages));
    }

    @Override
    public void unloadContent(ContentManager content) {
        //empty
    }

    @Override
    public void draw() {

        Ship.myShip.draw();
    }

    @Override
    public void onSlideView(IShipBuildingView.ViewDirection direction) {

        //  ( ...Powercore <->)extraPart <-> cannon <-> MainBody <-> engine <-> Powercore( <-> extraPart <-> cannon....)

        switch(state)
        {
            case MAIN_BODY:
                switch (direction)
                {
                    case RIGHT:
                        state = CANNON;
                        sba.animateToView(state, IShipBuildingView.ViewDirection.LEFT);
                        break;
                    case LEFT:
                        state = ENGINE;
                        sba.animateToView(state, IShipBuildingView.ViewDirection.RIGHT);
                        break;
                    default: // for up and down
                        break;
                }
                break;
            case ENGINE:
                switch (direction)
                {
                    case RIGHT:
                        state = MAIN_BODY;
                        sba.animateToView(state, IShipBuildingView.ViewDirection.LEFT);
                        break;
                    case LEFT:
                        state = POWER_CORE;
                        sba.animateToView(state, IShipBuildingView.ViewDirection.RIGHT);
                        break;
                    default: // for up and down
                        break;
                }
                break;
            case POWER_CORE:
                switch (direction)
                {
                    case RIGHT:
                        state = ENGINE;
                        sba.animateToView(state, IShipBuildingView.ViewDirection.LEFT);
                        break;
                    case LEFT:
                        state = EXTRA_PART;
                        sba.animateToView(state, IShipBuildingView.ViewDirection.RIGHT);
                        break;
                    default: // for up and down
                        break;
                }
                break;
            case EXTRA_PART:
                switch (direction)
                {
                    case RIGHT:
                        state = POWER_CORE;
                        sba.animateToView(state, IShipBuildingView.ViewDirection.LEFT);
                        break;
                    case LEFT:
                        state = CANNON;
                        sba.animateToView(state, IShipBuildingView.ViewDirection.RIGHT);
                        break;
                    default: // for up and down
                        break;
                }
                break;
            case CANNON:
                switch (direction)
                {
                    case RIGHT:
                        state = EXTRA_PART;
                        sba.animateToView(state, IShipBuildingView.ViewDirection.LEFT);
                        break;
                    case LEFT:
                        state = MAIN_BODY;
                        sba.animateToView(state, IShipBuildingView.ViewDirection.RIGHT);
                        break;
                    default: // for up and down
                        break;
                }
                break;

        }

    }

    @Override
    public void onPartSelected(int index) {

        GameEngine gameEngine = GameEngine.getInstance();

        switch (state)
        {
            case MAIN_BODY:
                Ship.myShip.setMainBody(gameEngine.getMainBodies().get(index));

                //Set the proper parts (the POINT Fs)
                Ship.myShip.getMainBody().setMbCannonAttach();
                Ship.myShip.getMainBody().setMbEngineAttach();
                Ship.myShip.getMainBody().setMbExtraAttach();
                Ship.myShip.getMainBody().setMbCenter();

                break;
            case CANNON:
                Ship.myShip.setCannon(gameEngine.getCannons().get(index));

                //Set proper parts (the POINT Fs)
                Ship.myShip.getCannon().setCnAttachPoint();
                Ship.myShip.getCannon().setCnEmitPoint();
                Ship.myShip.getCannon().setCnCenter();

                Ship.myShip.setProjectile(index);


                break;
            case ENGINE:
                Ship.myShip.setEngine(gameEngine.getEngines().get(index));

                //Set proper parts
                Ship.myShip.getEngine().setEnAttachPoint();
                Ship.myShip.getEngine().setEnCenter();

                break;
            case POWER_CORE:
                Ship.myShip.setPowerCore(gameEngine.getPowerCores().get(index));


                break;
            case EXTRA_PART:
                Ship.myShip.setExtraPart(gameEngine.getExtraParts().get(index));

                //Set proper parts
                Ship.myShip.getExtraPart().setEpAttachPoint();
                Ship.myShip.getExtraPart().setEpCenter();
                break;


        }
    }

    @Override
    public void onStartGamePressed() {

        sba.startGame();
    }

    @Override
    public void onResume() {
//empty
    }

    @Override
    public IView getView() {
        return sba;
        //return shipbuiliding activity
    }

    @Override
    public void setView(IView view) {
//empty
    }
}
