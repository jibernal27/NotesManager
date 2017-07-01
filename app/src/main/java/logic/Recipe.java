package logic;

import java.util.Date;

/**
 * Created by jairo on 30/06/2017.
 */

public class Recipe
{
    private double id;
    private String name;
    private String instructions;
    private Date created_at;
    private Date updated_at;

    public Recipe( String name, String instructions ) {
        this.id = 1;
        this.name = name;
        this.instructions=instructions;
    }

    public String getName() {
        return name;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        return "Id: "+id+" name: "+name;
    }
}

