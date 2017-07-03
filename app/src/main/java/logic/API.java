package logic;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by jairo on 30/06/2017.
 * Uses REtrofit as a wat to simplify the development
 */

public interface API {

    /**
     * Main consumer of the API
     * @return List of recipes
     */
    @GET("recipes.json")
    Call<List<Recipe>> getRecipeList();

    /**
     * DEtail of a recipie
     * @param id ID of the recipe to consult
     * @return Recipe with all is details
     */
    @GET("recipes/{id}.json")
    Call<Recipe> getRecipe(@Path("id") int id);

    /**
     * Add a recipie to the backend
     * @param name THe name of the recipe
     * @param description The instructions/description of the recepe
     */
    @FormUrlEncoded
    @POST("recipes")
    Call<Void> postRecipe(@Field("recipe[name]") String name, @Field("recipe[instructions]") String description);

    /**
     *  Deletes a recipe
     * @param id The id of th recipe to delete
     */
    @DELETE("recipes/{id}")
    Call<Void> deleteRecipe(@Path("id") int id);

    /**
     *  MOdifies an existing recipe
     * @param id ID of the recipe to edit
     * @param name Name given to the recipe
     * @param description Description given to the recipe
     */
    @FormUrlEncoded
    @PUT("recipes/{id}")
    Call<Void> editRecipe(@Path("id") int id,@Field("recipe[name]") String name, @Field("recipe[instructions]") String description);

}