package IE.P1;

import java.lang.*;

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

    public double calculateLocation(){
        float x = Math.abs(this.location.getX() - 0) * Math.abs(this.location.getX() - 0);
        float y = Math.abs(this.location.getY() - 0) * Math.abs(this.location.getY() - 0);
        double resault = Math.sqrt(x+y);
        return resault;

    }

    public double calculatePopularity(){
        float AVGFoodPopularity = 0;
        for (int i = 0; i < this.menu.size(); i++){
            AVGFoodPopularity += this.menu.get(i).getPopularity();
        }
        AVGFoodPopularity = AVGFoodPopularity/this.menu.size();
        double Popularity = AVGFoodPopularity/this.calculateLocation();
        return Popularity;

    }



}
