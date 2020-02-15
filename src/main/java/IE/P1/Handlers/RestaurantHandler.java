package IE.P1.Handlers;
import IE.P1.Exceptions.NoRestaurant;
import IE.P1.Exceptions.OutOfBoundryLocation;
import IE.P1.JavalinServer;
import IE.P1.models.Restaurant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.javalin.plugin.rendering.template.TemplateUtil.model;

public class RestaurantHandler {

    public static void GetNearbyRestaurants(Context context) throws IOException {

        ArrayList<Restaurant> Selectedrestaurants = new ArrayList<>();
        JavalinServer server = context.cookieStore("server");
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Restaurant> restaurants = mapper.readValue(new File("src/main/resources/restaurants.json")
                , new TypeReference<List<Restaurant>>() {
                });
        for (Restaurant restaurant : restaurants) {
            if (restaurant.calculateLocation() <= 170) {
                Selectedrestaurants.add(restaurant);
            }
        }
        context.render("/restaurants.html", model("restaurants", Selectedrestaurants));
    }
    public static void GetAllRestaurants(Context context) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Restaurant> restaurants = mapper.readValue(new File("src/main/resources/restaurants.json")
                , new TypeReference<List<Restaurant>>() {
                });
        context.render("/restaurants.html", model("restaurants", restaurants));
    }

    public static void GetRestaurant(Context context) throws IOException {
        JavalinServer server = context.cookieStore("server");
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Restaurant> restaurants = mapper.readValue(new File("src/main/resources/restaurants.json")
                , new TypeReference<List<Restaurant>>() {
                });
        boolean getable = true;
        for (Restaurant restaurant : restaurants) {
            //System.out.println(restaurants.get(i).getId());
            try {
            if (restaurants.get(i).getId().equals(context.pathParam("restaurant-id"))) {
                //System.out.println(context.pathParam("restaurant-id"));
                if (restaurant.calculateLocation() < 170) {
                    context.render("/restaurant.html", model("restaurant", restaurant, "url", "/cart"));
                    return;
                } else {
                    //System.out.println("hi");
                    getable = false;
                    throw new OutOfBoundryLocation("chosen restaurant is too far");

                    }
                }
            else{
                    if (getable) {
                        //context.status(404);
                        throw new NoRestaurant("no restaurant found");
                    }
                }
            }catch (OutOfBoundryLocation e){
                System.out.println(e.getMessage());
                context.status(403);
                return;
            }catch (NoRestaurant e){
                System.out.println(e.getMessage());
                context.status(404);
                return;
            }
        }

    }
}
