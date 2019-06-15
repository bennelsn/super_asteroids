package edu.byu.cs.superasteroids.model_classes;

import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import java.io.IOException;
import java.util.ArrayList;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game.ViewPort;
import edu.byu.cs.superasteroids.ship_builder.MyShipBuildingController;

/**
 * Created by BenNelson on 10/21/16.
 * This class extends MovingObject because a ship is a moving object
 */
public class Ship extends MovingObject {

    public static Ship myShip = new Ship();

    private MainBody mainBody = null;
    private Cannon cannon = null;
    private Engines engine = null;
    private ExtraPart extraPart = null;
    private PowerCore powerCore = null;
    private int projIndex;

    private PointF currentScale = new PointF((float) .3, (float) .3);
    private PointF currentPosition = new PointF(550, 150);
    private double currentRotation = 0;
    private int full_opacity = 255;
    private boolean shipHit = false;
    private int count = 115;
    private final static int countFull = 115;
    private final static boolean fiveSecondsComplete = true;

    private PointF defaultCannonAttach = new PointF(190, 227);
    private PointF defaultEngineAttach = new PointF(102,392);
    private PointF defaultExtraAttach = new PointF(5,253);
    private PointF defaultCenter = new PointF(200/2,400/2);


    public void draw()
    {

        if(mainBody != null)
        {
           drawMainBody();
        }
        if(cannon != null)
        {
            drawCannon();
        }
        if(engine != null)
        {
            drawEngine();
        }
        if(extraPart != null)
        {
            drawExtraPart();
        }
        //if(powerCore != null)


    }


    /**
     * This method will check to make sure all parts of the ship have been assigned and are not null.
     * @return true if no part is equal to null, @return false if at least one part is equal to null.
     */
    public boolean isComplete()
    {
        if(mainBody == null || cannon == null || engine == null || extraPart == null || powerCore == null)
        {
            return false;
        }
        else
        {
            return true;

        }

    }

    public void drawMainBody()
    {
        String mb_path = mainBody.getImage();
        ContentManager contentManager = ContentManager.getInstance();

        DrawingHelper.drawImage(contentManager.loadImage(mb_path),currentPosition.x,currentPosition.y,(float) currentRotation,currentScale.x,currentScale.y,full_opacity);


    }

    public void drawCannon()
    {
        String cn_path = cannon.getImage();
        ContentManager contentManager = ContentManager.getInstance();

        PointF p1;
        if(mainBody == null)
        {
            p1 = GraphicsUtils.subtract(defaultCannonAttach,defaultCenter);
        }
        else
        {
            p1 = GraphicsUtils.subtract(mainBody.getMbCannonAttach(),mainBody.getMbCenter());
        }

        PointF p2 = GraphicsUtils.subtract(cannon.getCnCenter(), cannon.getCnAttachPoint());
        cannon.setCnPosition((GraphicsUtils.add(p1, p2)));

        //Set scale of the part
        cannon.setCnPosition(GraphicsUtils.scale(cannon.getCnPosition(),currentScale.x));

        //THERE ARE FUNCTIONS IN GRAPHICSUTIL FOR degrees to radians and vice versa
        PointF cnPositionRotated = GraphicsUtils.rotate(cannon.getCnPosition(), GraphicsUtils.degreesToRadians(currentRotation));

        //Where you draw the cannon
        cannon.setCnPosition(GraphicsUtils.add(cnPositionRotated, currentPosition));
        //Drawing cannon


        DrawingHelper.drawImage(contentManager.loadImage(cn_path), cannon.getCnPosition().x, cannon.getCnPosition().y,(float) currentRotation, currentScale.x, currentScale.y, full_opacity);
    }

    public void drawProjectile(Projectile currentProjectile)
    {
        String projPath = currentProjectile.getLaserImage();
        ContentManager cm = ContentManager.getInstance();
        int pathID = cm.loadImage(projPath);
        //float x = currentProjectile.getProjPosition().x; // OLD
        //float y = currentProjectile.getProjPosition().y; // OLD
        float x = currentProjectile.getProjPosition().x - ViewPort.getInstance().getTopLeftCorner().x; // NEW
        float y = currentProjectile.getProjPosition().y - ViewPort.getInstance().getTopLeftCorner().y; // NEW
        double rotation = currentProjectile.getProjRotation();
        DrawingHelper.drawImage(pathID,x,y,(float) rotation, (float).3, (float) .3, 255);

    }


    public PointF getLaserEmitPoint(){

        //String path = p.getLaserImage();
        //ContentManager contentManager = ContentManager.getInstance();
        PointF p1 = GraphicsUtils.subtract(mainBody.getMbCannonAttach(), mainBody.getMbCenter());

        PointF p2 = GraphicsUtils.subtract(cannon.getCnEmitPoint(), cannon.getCnAttachPoint());
        PointF currentEmitPoint = GraphicsUtils.add(p1, p2);

        //Set scale of the part
        currentEmitPoint = GraphicsUtils.scale(currentEmitPoint, currentScale.x);

        //THERE ARE FUNCTIONS IN GRAPHICSUTIL FOR degrees to radians and vice versa
        PointF prPositionRotated = GraphicsUtils.rotate(currentEmitPoint, GraphicsUtils.degreesToRadians(currentRotation));

        //Where you draw the cannon
        currentEmitPoint = GraphicsUtils.add(prPositionRotated, currentPosition);
        return currentEmitPoint;
    }

    public void drawEngine()
    {
        String en_path = engine.getImage();
        ContentManager contentManager = ContentManager.getInstance();

        PointF p1;
        if (mainBody == null) {
            p1 = GraphicsUtils.subtract(defaultEngineAttach, defaultCenter);
        } else {
            p1 = GraphicsUtils.subtract(mainBody.getMbEngineAttach(), mainBody.getMbCenter());
        }

        PointF p2 = GraphicsUtils.subtract(engine.getEnCenter(), engine.getEnAttachPoint());
        engine.setEnPosition((GraphicsUtils.add(p1, p2)));

        //Set scale of the part
        engine.setEnPosition(GraphicsUtils.scale(engine.getEnPosition(), currentScale.x));

        //THERE ARE FUNCTIONS IN GRAPHICSUTIL FOR degrees to radians and vice versa
        PointF enPositionRotated = GraphicsUtils.rotate(engine.getEnPosition(), GraphicsUtils.degreesToRadians(currentRotation));

        //Where you draw the engine
        engine.setEnPosition(GraphicsUtils.add(enPositionRotated, currentPosition));
        //Drawing engine
        DrawingHelper.drawImage(contentManager.loadImage(en_path), engine.getEnPosition().x, engine.getEnPosition().y, (float) currentRotation, currentScale.x, currentScale.y, full_opacity);


    }

    public void drawExtraPart()
    {
        String ep_path = extraPart.getImage();
        ContentManager contentManager = ContentManager.getInstance();

        PointF p1;
        if (mainBody == null) {
            p1 = GraphicsUtils.subtract(defaultExtraAttach, defaultCenter);
        } else {
            p1 = GraphicsUtils.subtract(mainBody.getMbExtraAttach(), mainBody.getMbCenter());
        }

        PointF p2 = GraphicsUtils.subtract(extraPart.getEpCenter(), extraPart.getEpAttachPoint());
        extraPart.setEpPosition((GraphicsUtils.add(p1, p2)));

        //Set scale of the part
        extraPart.setEpPosition(GraphicsUtils.scale(extraPart.getEpPosition(), currentScale.x));

        //THERE ARE FUNCTIONS IN GRAPHICSUTIL FOR degrees to radians and vice versa
        PointF epPositionRotated = GraphicsUtils.rotate(extraPart.getEpPosition(), GraphicsUtils.degreesToRadians(currentRotation));

        //Where you draw the extrapart
        extraPart.setEpPosition(GraphicsUtils.add(epPositionRotated, currentPosition));
        //Drawing extrapart
        DrawingHelper.drawImage(contentManager.loadImage(ep_path), extraPart.getEpPosition().x, extraPart.getEpPosition().y, (float) currentRotation, currentScale.x, currentScale.y, full_opacity);

    }

    public MainBody getMainBody() {
        return mainBody;
    }

    public void setMainBody(MainBody mainBody) {
        this.mainBody = mainBody;
    }

    public Cannon getCannon() {
        return cannon;
    }

    public void setCannon(Cannon cannon) {
        this.cannon = cannon;
    }

    public Engines getEngine() {
        return engine;
    }

    public void setEngine(Engines engine) {
        this.engine = engine;
    }

    public ExtraPart getExtraPart() {
        return extraPart;
    }

    public void setExtraPart(ExtraPart extraPart) {
        this.extraPart = extraPart;
    }

    public PowerCore getPowerCore() {
        return powerCore;
    }

    public void setPowerCore(PowerCore powerCore) {
        this.powerCore = powerCore;
    }

    public PointF getCurrentScale() {
        return currentScale;
    }

    public void setCurrentScale(PointF currentScale) {
        this.currentScale = currentScale;
    }

    public PointF getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(PointF currentPostion) {
        this.currentPosition = currentPostion;
    }

    public int getFull_opacity() {
        return full_opacity;
    }

    public void setFull_opacity(int full_opacity) {
        this.full_opacity = full_opacity;
    }

    public double getCurrentRotation() {
        return currentRotation;
    }

    public void setCurrentRotation(double currentRotation) {
        this.currentRotation = currentRotation;
    }

    public boolean isShipHit() {
        return shipHit;
    }

    public void setShipHit(boolean shipHit) {
        this.shipHit = shipHit;
    }

    public float getFloatFromXYinput(int index, String to_parse)
    {
        String[] array = to_parse.split(",");

        return Float.parseFloat(array[index]);

    }

    public void loadPartsForQuickPlay()
    {

        mainBody = GameEngine.getInstance().getMainBodies().get(1);
        ContentManager.getInstance().loadImage(mainBody.getImage());

        cannon = GameEngine.getInstance().getCannons().get(1);
        ContentManager.getInstance().loadImage(cannon.getImage());

        extraPart = GameEngine.getInstance().getExtraParts().get(1);
        ContentManager.getInstance().loadImage(extraPart.getImage());

        engine = GameEngine.getInstance().getEngines().get(1);
        ContentManager.getInstance().loadImage(engine.getImage());

        powerCore = GameEngine.getInstance().getPowerCores().get(1);
        ContentManager.getInstance().loadImage(powerCore.getImage());

        //PROJECTILE ADD IN *************
        setProjectile(1);
        //ContentManager.getInstance().loadSound(projectile.getLaserSound());


        //**********************************

        mainBody.setMbCannonAttach();
        mainBody.setMbEngineAttach();
        mainBody.setMbExtraAttach();
        mainBody.setMbCenter();
        //Set proper parts (the POINT Fs)
        cannon.setCnAttachPoint();
        cannon.setCnEmitPoint();
        cannon.setCnCenter();

        //Set proper parts
        engine.setEnAttachPoint();
        engine.setEnCenter();
        //Set proper parts
        extraPart.setEpAttachPoint();
        extraPart.setEpCenter();

    }

    public void setProjectile(int index)
    {
        this.projIndex = index;
    }

    public int getProjIndex()
    {
        return projIndex;
    }

    public boolean safeForFiveSeconds()
    {
        count--;
        if(count == 0)
        {
            count = countFull;
            return fiveSecondsComplete;
        }
        else
        {
            return false;
        }

    }

    public boolean countIsFull()
    {
        if(count == countFull)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
