package com.example.jairo.notemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import logic.Recipe;
import logic.World;

public class MainActivity extends AppCompatActivity {
    private ListView recipesList;
    private World world;
    private ArrayAdapter<Recipe> arrayAdapter;
    public final int ACTIVITY_ADD=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        world= World.getInstance();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        recipesList= (ListView) findViewById(R.id.recipesList);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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
    }


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
    protected void onStop() {
    super.onStop();
        //world.persistData(getFilesDir());

    }

    @Override
    protected void onStart() {
        super.onStart();
        //world.loadData(getFilesDir());

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (ACTIVITY_ADD) : {
                if (resultCode == Activity.RESULT_OK) {
                    boolean returnValue = data.getBooleanExtra("ADDED",false);
                    boolean edited = data.getBooleanExtra("MODIFIDED",false);
                    boolean deleted = data.getBooleanExtra("DELETED",false);
                    if(returnValue)
                    {
                        arrayAdapter.notifyDataSetChanged();
                        Snackbar.make(getCurrentFocus(), "Note added", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    if(edited)
                    {
                        arrayAdapter.notifyDataSetChanged();
                        Snackbar.make(getCurrentFocus(), "Note updated", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    if(deleted)
                    {
                        arrayAdapter.notifyDataSetChanged();
                        Snackbar.make(getCurrentFocus(), "Note deleted", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
                break;
            }
        }
    }
}
