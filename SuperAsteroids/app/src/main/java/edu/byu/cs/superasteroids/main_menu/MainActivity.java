package edu.byu.cs.superasteroids.main_menu;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import edu.byu.cs.superasteroids.R;
import edu.byu.cs.superasteroids.base.ActionBarActivityView;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.data_classes.DAO;
import edu.byu.cs.superasteroids.data_classes.dbOpenHelper;
import edu.byu.cs.superasteroids.game.GameActivity;
import edu.byu.cs.superasteroids.importer.ImportActivity;
import edu.byu.cs.superasteroids.model_classes.GameEngine;
import edu.byu.cs.superasteroids.ship_builder.ShipBuildingActivity;

public class MainActivity extends ActionBarActivityView implements IMainMenuView {


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private static boolean dataImportAlreadyPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        //TODO: Set this activity's controller to an instance of your MainMenuController
        //TODO: Pass the MainMenuController's constructor a reference to its IMainMenuView (this)
        IMainMenuController controller =  new MyMainMenuController(this);
        setController(controller);


        //TODO: Initialize your database
        //Make an instance of my dbOpenHelper and give it a context where the db is created
        dbOpenHelper helper = new dbOpenHelper(getBaseContext());

        //Initialize my DATABASE
        SQLiteDatabase db = helper.getWritableDatabase();
        //helper.onCreate(db);

        DAO dao = DAO.getInstance();
        dao.initializeDatabase(db);

        ContentManager.getInstance().setResources(getResources());

        ContentManager.getInstance().setAssets(getAssets());
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    public void startGame(View v) {
        //if(!dataImportAlreadyPressed)
        //{
           // return;
        //}

        Intent intent = new Intent(this, ShipBuildingActivity.class);
        startActivity(intent);
    }

    public void quickPlay(View v) {
        //if(!dataImportAlreadyPressed)
        //{
            //return;
        //}
        if (getController() != null) {
            ((IMainMenuController) getController()).onQuickPlayPressed();
        }
    }

    public void startGame() {
        Intent intent = new Intent(this, GameActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        this.startActivity(intent);
    }

    public void importData(View v) {
        dataImportAlreadyPressed = true;

        Intent intent = new Intent(this, ImportActivity.class);
        startActivity(intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
