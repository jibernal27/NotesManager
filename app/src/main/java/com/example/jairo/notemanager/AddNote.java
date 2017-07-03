package com.example.jairo.notemanager;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import logic.World;

/**
 * Activity to add or edit a Recipe
 */
public class AddNote extends AppCompatActivity {
    /**
     * Text view with the name of the recipe/note
     */
    private TextView textViewName;
    /**
     * Text view with the description/instruction of the recipe
     */
    private TextView textViewescription;
    /**
     * Button to add/edit a note
     */
    private Button buttonAddNote;
    /**
     * BUtton to delete a note
     */
    private Button buttonDeleteNote;
    /**
     * Instance of the world
     */
    private World world;
    /**
     * Number of the recipe in the list recipes, -1 if is a new recipe
     */
    private int s;

    /**
     * Initiates all attributes
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textViewName = (TextView) findViewById(R.id.editTextName);
        textViewescription = (TextView) findViewById(R.id.editTextDescription);
        buttonAddNote= (Button) findViewById(R.id.buttonAddNote);
        buttonDeleteNote=(Button) findViewById(R.id.buttonDeleteNote);
        world= World.getInstance();
        s = getIntent().getIntExtra("item",-1);
        if(s==-1)
        {
            addMode();
        }
        else
        {
            editMode();
        }


    }

    /**
     * Renders the view as edit mode
     */
    private void editMode()
    {
        textViewName.setText(world.getRecipe(s).getName());
        textViewescription.setText(world.getRecipe(s).getInstructions());
        buttonAddNote.setText(getText(R.string.label_button_form_editNote));
        buttonDeleteNote.setVisibility(View.VISIBLE);
        buttonDeleteNote.setEnabled(true);
        buttonDeleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteNote();
            }
        });
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editNote();
            }
        });

    }

    /**
     * render the view as add mode
     */
    private void addMode()
    {
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addNote();
            }
        });
    }

    /**
     * Checks of the values in the textviews are not empty.
     * @return True if vaild
     */
    private boolean checkData()
    {
        if(!textViewName.getText().toString().isEmpty())
        {
            if(!textViewescription.getText().toString().isEmpty())
            {
                return true;
            }
            else
            {
                textViewescription.setError( "Required field");
            }

        }
        else
        {
            textViewName.setError( "Required field");
        }
        return false;
    }

    /**
     * Gets the user input and calls the world to edit a note
     */
    private void editNote()
    {
        if(checkData())
        {
           if( world.editRecipe(s,textViewName.getText().toString(),textViewescription.getText().toString()))
            {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("MODIFIDED", true);
                setResult(MainActivity.RESULT_OK, resultIntent);
                finish();

            }
            else
            {

            }

        }
    }

    /**
     * Gets the user input and add a note using the world
     */
    private void addNote()
    {
        if (checkData())
        {
            if(world.addRecipe(textViewName.getText().toString(),textViewescription.getText().toString()))
            {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("ADDED", true);
                setResult(MainActivity.RESULT_OK, resultIntent);
                finish();

            }
            else
            {

            }
        }


    }

    /**
     * Deletes the actual note
     */
    private void deleteNote()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(
               this);
        alert.setTitle("Confirmation");
        alert.setMessage("Are you sure to delete the Note/Recipe");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                if(world.deleteRecipe(s))
                {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("DELETED", true);
                    setResult(MainActivity.RESULT_OK, resultIntent);
                    finish();

                }
                else
                {

                }


            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        alert.show();
    }

}
