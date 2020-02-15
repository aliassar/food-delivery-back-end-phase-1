package IE.P1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;

import io.javalin.http.Context;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinPebble;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.*;

public class JavalinServer {
    ArrayList<Restaurant> restaurants;

    public ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(ArrayList<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public static void Test(Context context) {
        User user = context.cookieStore("user");
        context.result(user.getFname());
    }


    public static void main(String[] args) throws IOException {
        User user = new User("Hamid", "Mohtadi", "+989125555134", "panah@yahoo.com", 50010);
        ArrayList<Order> NewOrders = new ArrayList<>();
        user.setOrders(NewOrders);
        JavalinServer server = new JavalinServer();
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Restaurant> restaurants = mapper.readValue(new URL("http://138.197.181.131:8080/restaurants")
                , new TypeReference<List<Restaurant>>() {
                });
        mapper.writeValue(new File("src/main/resources/restaurants.json"), restaurants);
        JavalinRenderer.register(JavalinPebble.INSTANCE, ".peb", ".pebble");
        Javalin app = Javalin.create().before(ctx -> {
            ctx.cookieStore("user", user);
        }).routes(() -> {
            path("/", () -> {
                get(RestaurantHandler::GetAllRestaurants);
            });
            path("r/", () -> {
                get(RestaurantHandler::GetNearbyRestaurants);
                path(":restaurant-id", () -> {
                    get(RestaurantHandler::GetRestaurant);
                });
            });
            {
                path("user/", () -> {
                    get(UserHandler::GetUserInfo);
                    post(UserHandler::IncreaseWallet);
                });
            }
            path("cart/", () -> {
                get(CartHandler::GetCart);
                post(CartHandler::AddToCart);
                path("finalize", () -> {
                    post(CartHandler::FinalizeOrder);
                });
            });
        }).start(12337);
    }
}
