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
 */

public interface API {

    @GET("recipes.json")
    Call<List<Recipe>> getRecipeList();


    @GET("recipes/{id}.json")
    Call<Recipe> getRecipe(@Path("id") int id);

    @FormUrlEncoded
    @POST("recipes")
    Call<Void> postRecipe(@Field("recipe[name]") String name, @Field("recipe[instructions]") String description);

    @DELETE("recipes/{id}")
    Call<Void> deleteRecipe(@Path("id") int id);

    @FormUrlEncoded
    @PUT("recipes/{id}")
    Call<Void> editRecipe(@Path("id") int id,@Field("recipe[name]") String name, @Field("recipe[instructions]") String description);

}