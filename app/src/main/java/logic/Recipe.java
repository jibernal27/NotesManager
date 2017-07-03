package logic;

import java.util.Date;

/**
 * Created by jairo on 30/06/2017.
 */

public class Recipe
{
    /**
     * Id of the recipe
     */
    private int id;
    /**
     * name of the recipe
     */
    private String name;
    /**
     * Instructions/description of the recipe
     */
    private String instructions;
    /**
     * Date of creation of the recipe
     */
    private Date created_at;
    /**
     * DAte of modification of the recipe
     */
    private Date updated_at;

    /**
     * Constructor of the recipe
     * @param name Name of the recipe
     * @param instructions instructions/description of the recipe
     */

    public Recipe( String name, String instructions ) {
        this.name = name;
        this.instructions=instructions;
    }

    /**
     * Returns the id of the recipe
     * @return The id of the recipe
     */
    public int getId(){return id;}

    /**
     * Returns the name of the recipe
     * @return the name of the recipe
     */
    public String getName() {
        return name;
    }
    /**
     * Returns the instructions of the recipe
     * @return the instructions of the recipe
     */
    public String getInstructions() {
        return instructions;
    }


    /**
     * Provides the format to show in the ListView
     * @return
     */

    @Override
    public String toString() {
        return "Id: "+id+" name: "+name;
    }
}

