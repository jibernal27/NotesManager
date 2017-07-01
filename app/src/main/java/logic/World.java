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
    private static final World ourInstance = new World();
    public static final String URL="http://redo-receta.herokuapp.com";
    public final String PERSISTENCE="data.txt";
    private API api;
    public static World getInstance() {
        return ourInstance;
    }
    private List<Recipe> recipes;
    private boolean loaded;
    private World() {
        recipes= new ArrayList<Recipe>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(API.class);

        for (int i=0;i<100;i++)
        {
            Recipe act=new Recipe(i+"",+i+"");
           // recipes.add(act);
        }
        loaded=false;
    }

    public List<Recipe> getArrayRecipes()
    {
        return recipes;
    }

    public Recipe getRecipe(int i)
    {
        return recipes.get(i);
    }


    public boolean addRecipe(String name, String description)
    {
        Recipe add= new Recipe(name,description);
        recipes.add(add);
        return true;
    }


    public boolean editRecipe(int s, String name, String description) {
        if(s<0 ||s>recipes.size()-1)
        {
            return false;
        }

        Recipe add= recipes.get(s);
        add.setName(name);
        add.setInstructions(description);
            return true;
    }

    public boolean deleteRecipe(int s) {
        if(s<0 ||s>recipes.size()-1)
        {
            return false;
        }
        recipes.remove(s);
        return true;
    }

    public void loadData(File file)
    {
        if(!loaded) {
            loaded=true;
            File f = new File(file, PERSISTENCE);
            BufferedReader br = null;
            FileReader fr = null;
            if (f.isFile()) {
                try {
                    fr = new FileReader(f);
                    br = new BufferedReader(fr);

                    String sCurrentLine = "";
                    String name = "";
                    String description = "";
                    int total = 0;
                    sCurrentLine = br.readLine();
                    if (sCurrentLine != null) {
                        total = Integer.parseInt(sCurrentLine);
                    }
                    for (int i = 0; i < total; i++) {
                        if ((name = br.readLine()) != null && (description = br.readLine()) != null) {
                            Recipe r = new Recipe(name, description);
                            recipes.add(r);
                        }

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {

                        br.close();
                        fr.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            } else {

            }

        }
    }

    public void persistData(File file)
    {
        try{
            File f=new File(file,PERSISTENCE);
            PrintWriter writer = new PrintWriter(f);
                writer.println(recipes.size());
                for (int i=0;i<recipes.size();i++)
                {
                    Recipe act=recipes.get(i);
                    writer.println(act.getName());
                    writer.println(act.getInstructions());
                }
            writer.close();
        } catch (IOException e) {
            // do something
        }
    }
}
