package IE.P1;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;

public class Restaurant {
    private String name;
    private String description;
    private ArrayList<Food> menu;
    private Location location;



    public void addToMenu(Food food){
        menu.add(food);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Food> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<Food> menu) {
        this.menu = menu;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }



}
