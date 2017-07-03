package logic;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jairo on 30/06/2017.
 */

public class World {
    /**
     * URL of the backend
     */
    public static final String URL="http://redo-receta.herokuapp.com";
    /**
     * Instance of the API
     */
    private API api;

    /**
     * Creates the instance with the provate constructor
     */

    private static final World ourInstance = new World();

    /**
     * SIngleton method to access an instance
     * @returns  The instance of the world
     */
    public static World getInstance() {
        return ourInstance;
    }

    /**
     * List of recepies
     */
    private List<Recipe> recipes;
    /**
     * Adapter of the listview
     */
    private ArrayAdapter<Recipe> listAdapter;

    /**
     * Creates the recepies list, configures retrofit to work
     */
    private World() {
        recipes= new ArrayList<Recipe>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(API.class);

    }

    /**
     * Sests the list adapter
     * @param list
     */
    public void sEtListAdapter(ArrayAdapter<Recipe> list)
    {
        listAdapter=list;
    }

    /**
     * Gets asynchronously the details of a recipe from the API
     * @param i The position of the recipe in the list recipes
     */
    private void getRecipeDetail(final int i,final boolean up)
    {

        Call<Recipe> call = api.getRecipe(recipes.get(i).getId());

        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if (response.isSuccessful()) {
                    recipes.set(i,response.body());
                    if(up)
                    {
                        listAdapter.clear();
                        listAdapter.addAll(recipes);
                    }
                } else {
                    // error response, no access to resource?
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("Error", t.getMessage());
            }
        });

    }

    /**
     * Gets asynchronously the list of recipes from the API, assings it to recepies and gets the
     * detail of each recepi
     */
    public void getRecipes()
    {
        Call<List<Recipe>> call = api.getRecipeList();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    recipes=response.body();
                    for (int i=0;i<recipes.size();i++)
                    {
                        getRecipeDetail(i,true);
                    }

                } else {
                    // error response, no access to resource?
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("Error", t.getMessage());
            }
        });
    }

    /**
     * Returns the local list of recepies
     * @return The list of recepies
     */
    public List<Recipe> getArrayRecipes()
    {

        return recipes;
    }

    /**
     * Gets a recipe from recepies
     * @param i THe position to return
     * @return THe i-esim recipe in recipes
     */
    public Recipe getRecipe(int i)
    {
        return recipes.get(i);
    }

    /**
     * Adds asynchronously a recipe to the backend
     * @param name The name of the recipe
     * @param description THe description of the recipe to add
     * @return true
     */

    public boolean addRecipe(String name, String description)
    {
        Call<Void> call =api.postRecipe(name,description);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse( Call<Void> call,  Response<Void> response) {
                if(response.isSuccessful())
                {
                    getRecipes();
                }
                else
                {

                }
            }

            @Override
            public void onFailure( Call<Void> call, Throwable t) {

            }
        });
        return true;
    }


    /**
     * Edits asynchronously a recipe
     * @param s THe position in recipes of the recipe
     * @param name The new name of the recipe
     * @param description The new description of the recipe
     * @return true
     */
    public boolean editRecipe(final int s, String name, String description) {
        if(s<0 ||s>recipes.size()-1)
        {
            return false;
        }

        Call<Void> call =api.editRecipe(recipes.get(s).getId(),name,description);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse( Call<Void> call,  Response<Void> response) {
                if(response.isSuccessful())
                {
                   getRecipeDetail(s,true);

                }
                else
                {

                }
            }

            @Override
            public void onFailure( Call<Void> call, Throwable t) {


            }
        });
        return true;
    }

    /**
     * Deletes a recipe asynchronously
     * @param s The position of the recipe to delete in the list recipes
     * @return true
     */

    public boolean deleteRecipe(int s) {
        if(s<0 ||s>recipes.size()-1)
        {
            return false;
        }
        Call<Void> call =api.deleteRecipe(recipes.get(s).getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse( Call<Void> call,  Response<Void> response) {
                if(response.isSuccessful())
                {
                    getRecipes();
                }
                else
                {

                }
            }

            @Override
            public void onFailure( Call<Void> call, Throwable t) {

            }
        });
        return true;
    }


}
