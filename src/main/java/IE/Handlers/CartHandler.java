package IE.Handlers;

import IE.Exceptions.*;
import IE.Loghme;
import IE.models.Food;
import IE.models.Order;
import IE.models.Restaurant;
import IE.models.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.javalin.plugin.rendering.template.TemplateUtil.model;

public class CartHandler {


    public static void GetCart(Context context) {
        User user = context.cookieStore("user");
        ArrayList<Order> cart = user.getOrders();
        try {
            if (cart.size() == 0) {
                //context.result("cart is empty");
                throw new EmptyCart("cart is empty");
            } else {
                context.render("/cart.html", model("cart", cart, "url", "/cart/finalize"));
            }
        } catch (EmptyCart e) {
            context.result(e.getMessage());
        }
    }

    public static void AddToCart(Context context) throws IOException {
        User user = context.cookieStore("user");
        ArrayList<Order> cart = user.getOrders();
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Restaurant> restaurants = mapper.readValue(new File("src/main/resources/restaurants.json")
                , new TypeReference<List<Restaurant>>() {
                });
        String name = context.formParam("name");
        String FoodName = context.formParam("restaurantName");
        float price = Float.parseFloat(Objects.requireNonNull(context.formParam("price")));
        Food food = new Food(name, FoodName, price);
        try {
            Loghme.addToCart(food, user, mapper, restaurants, cart);
        } catch (NoRestaurant | WrongFood | DifRestaurants e) {
            context.result(e.getMessage());
            context.status(401);
            return;
        }

        context.cookieStore("user", user);
        context.status(200);
    }

    public static void FinalizeOrder(Context context) throws IOException {
        User user = context.cookieStore("user");
        ArrayList<Order> cart = user.getOrders();
        try {
            if (cart.size() == 0) {
                //context.result("cart is empty");
                throw new EmptyCart("cart is empty");
            }
        } catch (EmptyCart e) {
            context.result(e.getMessage());
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        float totalPrice = 0;
        for (Order order : cart) {
            totalPrice += (order.getCost()*order.getNumOfOrder());
        }
        try {
            if (user.getWallet() < totalPrice) {
                //context.result("Insufficient money");
                throw new InsufficientMoney("Insufficient money");
            }
        } catch (InsufficientMoney e) {
            context.result(e.getMessage());
            return;
        }
        Loghme.finalizeOrder(user, mapper, cart,totalPrice);
        context.cookieStore("user", user);
        context.status(200);
    }
}
