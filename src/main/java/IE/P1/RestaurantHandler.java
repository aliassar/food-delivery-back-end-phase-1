package IE.P1;

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
            if (restaurant.getId().equals(context.pathParam("restaurant-id"))) {
                //System.out.println(context.pathParam("restaurant-id"));
                if (restaurant.calculateLocation() < 170) {
                    context.render("/restaurant.html", model("restaurant", restaurant, "url", "/cart"));
                } else {
                    //System.out.println("hi");
                    context.status(403);
                    getable = false;

                }
            } else {
                if (getable) {
                    context.status(404);
                }
            }
        }

    }
}
