package com.example.jairo.notemanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import logic.Recipe;
import logic.World;

/**
 * Control the mian activity of the application
 */
public class MainActivity extends AppCompatActivity
{
        /**
         * List in which all the recipes are shown
         */
    private ListView recipesList;
        /**
         * Instance of the world
         */
    private World world;
        /**
         * Array adapter of the LIstVIew
         */
    private ArrayAdapter<Recipe> arrayAdapter;
        /**
         * COde to get the result od the activity AddNote
         */
    public final int ACTIVITY_ADD=1;
        /**
         * SWipe refresh for the ListVIew
         */
    private SwipeRefreshLayout swipeRefreshLayout;
        /**
         * Coordinator layout to show Snackbar messages
         */
    private CoordinatorLayout coordinator;
        /**
         * FLoating button to add a recipe/note
         */
    private FloatingActionButton fab;

        /**
         * Initiates all the attributes and sets the actions
         * @param savedInstanceState
         */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        world= World.getInstance();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        recipesList= (ListView) findViewById(R.id.recipesList);
        setSupportActionBar(toolbar);
        coordinator= (CoordinatorLayout) findViewById(R.id.coordinator);
         fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lunchAddNote(-1);
            }
        });
         arrayAdapter = new ArrayAdapter<Recipe>(
                this,
                android.R.layout.simple_list_item_1,
                world.getArrayRecipes());
        world.sEtListAdapter(arrayAdapter);
        recipesList.setAdapter(arrayAdapter);
        recipesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                lunchAddNote(i);
            }
        });
        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                update();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        update();
    }

        /**
         * Lunches th activiy add note for an specif note or a new note.
         * @param pos
         */

    public void lunchAddNote(int pos)
    {
        Intent intent = new Intent(this, AddNote.class);
        if(pos!=-1)
        {
            intent.putExtra("item", pos);
        }
        startActivityForResult(intent,ACTIVITY_ADD);
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
         * Gets the result of activities lunched and provdes a message
         * indicating if the action was succesful
         */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(!connected())
        {
            return;
        }

        switch(requestCode) {
            case (ACTIVITY_ADD) : {
                if (resultCode == Activity.RESULT_OK) {
                    boolean returnValue = data.getBooleanExtra("ADDED",false);
                    boolean edited = data.getBooleanExtra("MODIFIDED",false);
                    boolean deleted = data.getBooleanExtra("DELETED",false);
                    if(returnValue)
                    {
                        arrayAdapter.notifyDataSetChanged();
                        Snackbar.make(coordinator, "Note added", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    if(edited)
                    {
                        arrayAdapter.notifyDataSetChanged();
                        Snackbar.make(coordinator, "Note updated", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    if(deleted)
                    {
                        arrayAdapter.notifyDataSetChanged();
                        Snackbar.make(coordinator, "Note deleted", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
                break;
            }
        }
    }

        /**
         * Checks if the phone is conected to internet, if not displays a snackbar to retry
         * @return
         */

    private boolean connected()
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean rta= activeNetworkInfo != null && activeNetworkInfo.isConnected();
    if(!rta)
    {
        Snackbar snackbar = Snackbar
                .make(coordinator, "No internet connection!", Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            update();
                    }
                });

// Changing message text color
        snackbar.setActionTextColor(Color.RED);

// Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }
    return rta;

    }

        /**
         * Gets all the recipes from the  API
         */
    private void update()
    {
        if(connected())
        {
            fab.setVisibility(View.VISIBLE);
            world.getRecipes();
        }
        else
        {
            fab.setVisibility(View.INVISIBLE);
        }

    }
}
