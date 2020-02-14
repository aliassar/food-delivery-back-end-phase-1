package IE.P1;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RestaurantHandler {

    public static void GetAllRestaurants(Context context) throws IOException {

        ArrayList<Restaurant> Selectedrestaurants = new ArrayList<>();
        JavalinServer server = context.cookieStore("server");
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Restaurant> restaurants = mapper.readValue(new File("src/main/resources/restaurants.json")
                , new TypeReference<List<Restaurant>>() {
                });
        for (int i=0; i<restaurants.size(); i++){
            if (restaurants.get(i).calculateLocation()<=170){
                Selectedrestaurants.add(restaurants.get(i));

            }
        }
        String Responce ="<html>"
                + "<body><h1>This Is The Most Modern Version of The First Page!</h1></body>"
                + "</html>";
        //System.out.println(Selectedrestaurants.size());
        context.json(Selectedrestaurants);


    }

    public static void GetRestaurant(Context context) throws IOException {
        JavalinServer server = context.cookieStore("server");
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Restaurant> restaurants = mapper.readValue(new File("src/main/resources/restaurants.json")
                , new TypeReference<List<Restaurant>>() {
                });
        boolean getable = true;
        for (int i = 0; i< restaurants.size(); i++){
            //System.out.println(restaurants.get(i).getId());
            if (restaurants.get(i).getId().equals(context.pathParam("restaurant-id"))){
                //System.out.println(context.pathParam("restaurant-id"));
                if (restaurants.get(i).calculateLocation()<170){
                    context.json(restaurants.get(i));
                }
                else {
                    //System.out.println("hi");
                    context.status(403);
                    getable = false;

                }
            }
            else {
                if (getable){
                    context.status(404);
                }
            }
        }

    }
}
