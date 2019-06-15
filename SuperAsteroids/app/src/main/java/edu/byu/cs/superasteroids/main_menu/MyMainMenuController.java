package edu.byu.cs.superasteroids.main_menu;

import edu.byu.cs.superasteroids.base.IController;
import edu.byu.cs.superasteroids.base.IView;
import edu.byu.cs.superasteroids.model_classes.Ship;
import edu.byu.cs.superasteroids.ship_builder.ShipBuildingActivity;

/**
 * Created by BenNelson on 10/18/16.
 * This class implements the IMainMenuView
 * It is a controller for the main menu
 */
public class MyMainMenuController implements IMainMenuController{

    private IMainMenuView mma;
    public MyMainMenuController(IMainMenuView activity) {
        this.mma = activity;

    }


    @Override
    public void onQuickPlayPressed()
    {
        Ship.myShip.loadPartsForQuickPlay();
        if(Ship.myShip.isComplete()) {
            mma.startGame();
        }
    }

    @Override
    public IView getView() {
        return null;
    }

    @Override
    public void setView(IView view) {

    }
}
