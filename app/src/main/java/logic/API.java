package logic;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by jairo on 30/06/2017.
 */

public interface API {

    @GET("recipes.json")
    Call<List<Recipe>> getRecipeList();


}
