package IE;

import IE.Handlers.CartHandler;
import IE.Handlers.RestaurantHandler;
import IE.Handlers.UserHandler;
import IE.models.Order;
import IE.models.Restaurant;
import IE.models.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;

import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinPebble;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static io.javalin.apibuilder.ApiBuilder.*;

public class JavalinServer {
    ArrayList<Restaurant> restaurants;
    static Javalin app;

    public ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(ArrayList<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }


    public static User serverConfig(boolean test) throws IOException {
        User user = new User("H", "M", "+0", "test@test.com", 2500000);
        ArrayList<Order> NewOrders = new ArrayList<>();
        user.setOrders(NewOrders);
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Restaurant> restaurants = test ? mapper.readValue(new File("src/test/resources/restaurants.json")
                , new TypeReference<List<Restaurant>>() {
                }) : mapper.readValue(new URL("http://138.197.181.131:8080/restaurants")
                , new TypeReference<List<Restaurant>>() {
                });
        mapper.writeValue(new File("src/main/resources/restaurants.json"), restaurants);
        JavalinRenderer.register(JavalinPebble.INSTANCE, ".peb", ".pebble");
        app = Javalin.create().before(ctx -> ctx.cookieStore("user", user)).routes(() -> {
            path("/", () -> get(RestaurantHandler::GetAllRestaurants));
            path("r/", () -> {
                get(RestaurantHandler::GetNearbyRestaurants);
                path(":restaurant-id", () -> get(RestaurantHandler::GetRestaurant));
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
                path("finalize", () -> post(CartHandler::FinalizeOrder));
            });
        });
        return user;
    }
    public static void startServer(int port){
        app.start(port);
    }
    public static void stopServer() {
        app.stop();
    }

    public static void main(String[] args) throws IOException {
        serverConfig( true);
        startServer(12330);
    }
}
