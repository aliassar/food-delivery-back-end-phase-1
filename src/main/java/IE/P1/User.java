package IE.P1;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class User {

    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
        while (true){
            Scanner input = new Scanner(System.in);
            String myString = input.next();
            if(myString.equals("exit")){
                break;
            }
            else if (myString.equals("addRestaurant")){
                String Order = input.nextLine();
                System.out.println(Order);
                ObjectMapper mapper = new ObjectMapper();
                Restaurant restaurant = mapper.readValue(Order, Restaurant.class);
                ArrayList<Restaurant> restaurants = mapper.readValue(new File("src/main/resources/restaurants.json")
                        , new TypeReference<List<Restaurant>>(){});
                boolean Addable = true;
                for (int i = 0; i < restaurants.size(); i++){
                    if(restaurants.get(i).getName().equals(restaurant.getName())){
                        Addable = false;
                    }
                }
                if(Addable){
                    //System.out.println("hi");
                    restaurants.add(restaurant);
                    mapper.writeValue(new File("src/main/resources/restaurants.json"),restaurants);
                }

            }
            else if (myString.equals("addFood")){
                String Order = input.nextLine();
                System.out.println(Order);
                ObjectMapper mapper = new ObjectMapper();
                Food food = mapper.readValue(Order, Food.class);
                System.out.println(food.getRestaurantName());
                ArrayList<Restaurant> restaurants = mapper.readValue(new File("src/main/resources/restaurants.json")
                        , new TypeReference<List<Restaurant>>(){});
                boolean Addable = false;
                int index = -1;
                for (int i = 0; i < restaurants.size(); i++){
                    if(restaurants.get(i).getName().equals(food.getRestaurantName())){
                        Addable = true;
                        index = i;
                    }
                }
                if (!Addable){System.out.println("restaurant not found");}
                if (Addable){
                    for (int i = 0; i<restaurants.get(index).getMenu().size(); i++){
                        if (restaurants.get(index).getMenu().get(i).getName().equals(food.getName())){
                            Addable = false;
                        }
                    }
                }
                if (Addable){
                    restaurants.get(index).addToMenu(food);
                    mapper.writeValue(new File("src/main/resources/restaurants.json"),restaurants);

                }


            }
            else if (myString.equals("getRestaurants")){
                ObjectMapper mapper = new ObjectMapper();
                ArrayList<Restaurant> restaurants = mapper.readValue(new File("src/main/resources/restaurants.json")
                        , new TypeReference<List<Restaurant>>(){});
                for (int i = 0; i < restaurants.size(); i++){
                    System.out.println(restaurants.get(i).getName());
                }
            }
            else if (myString.equals("getRestaurant")){
                String Order = input.nextLine();
                //System.out.println(Order);
                ObjectMapper mapper = new ObjectMapper();
                Restaurant restaurant = mapper.readValue(Order, Restaurant.class);
                //System.out.println(restaurant.getName());
                ArrayList<Restaurant> restaurants = mapper.readValue(new File("src/main/resources/restaurants.json")
                        , new TypeReference<List<Restaurant>>(){});
                boolean getable = false;

                for (int i = 0; i < restaurants.size(); i++){
                    if (restaurants.get(i).getName().equals(restaurant.getName())){

                        getable = true;
                        String json = mapper.writeValueAsString(restaurants.get(i));
                        System.out.println(json);
                        break;
                    }
                }
                if(!getable){
                    System.out.println("restaurant not found");
                }
            }
            else if (myString.equals("getFood")){
                String Order = input.nextLine();
                //System.out.println(Order);
                ObjectMapper mapper = new ObjectMapper();
                Food food = mapper.readValue(Order, Food.class);
                //System.out.println(restaurant.getName());
                ArrayList<Restaurant> restaurants = mapper.readValue(new File("src/main/resources/restaurants.json")
                        , new TypeReference<List<Restaurant>>(){});
                boolean getable = false;
                int index = -1;
                for (int i = 0; i < restaurants.size(); i++){
                    if (restaurants.get(i).getName().equals(food.getRestaurantName())){
                        index = i;
                        getable = true;
                    }
                }
                if (getable){
                    for (int i = 0; i < restaurants.get(index).getMenu().size();i++){
                        if (restaurants.get(index).getMenu().get(i).getName().equals(food.getName())){
                            String json = mapper.writeValueAsString(restaurants.get(index).getMenu().get(i));
                            System.out.println(json);
                            break;
                        }
                    }
                }
                else if(!getable){ System.out.println("restaurant not found");}
            }
            else if (myString.equals("addToCart")){

            }


            else{
                System.out.println(myString);

            }
        }
    }
}
