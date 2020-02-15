package IE.P1.Handlers;

import IE.P1.Exceptions.*;
import IE.P1.Loghme;
import IE.P1.models.Food;
import IE.P1.models.Order;
import IE.P1.models.Restaurant;
import IE.P1.models.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartHandler {




    public static void GetCart(Context context) throws IOException {
        User user = context.cookieStore("user");
        ArrayList<Order> cart = user.getOrders();
        try {
            if (cart.size() == 0) {
                //context.result("cart is empty");
                throw new EmptyCart("cart is empty");
            }
        }catch (EmptyCart e){
            context.result(e.getMessage());
            return;
        }

        context.json(cart);


    }

    public static void AddToCart(Context context) throws IOException {
        Loghme loghme = new Loghme();
        User user = context.cookieStore("user");
        ArrayList<Order> cart = user.getOrders();
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Restaurant> restaurants = mapper.readValue(new File("src/main/resources/restaurants.json")
                , new TypeReference<List<Restaurant>>() {
                });
        String name = context.formParam("name");
        String FoodName = context.formParam("RestaurantName");
        float price = Float.parseFloat(Objects.requireNonNull(context.formParam("price")));
        Food food = new Food(name,FoodName,price);
        try {
            user = loghme.addToCart(food,user,mapper,restaurants,cart);
        }
        catch (NoRestaurant | WrongFood | DifRestaurants e){
            context.result(e.getMessage());
            return;
        }

        context.cookieStore("user",user);
        context.status(200);
        String Responce ="<html>"
                + "<body><h1>Food added successfully!</h1></body>"
                + "</html>";
        //context.html(Responce);



    }
    public static void FinalizeOrder(Context context) throws IOException {
        Loghme loghme = new Loghme();
        User user = context.cookieStore("user");
        ArrayList<Order> cart = user.getOrders();
        try {
            if (cart.size() == 0) {
                //context.result("cart is empty");
                throw new EmptyCart("cart is empty");
            }
        }catch (EmptyCart e){
            context.result(e.getMessage());
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        float TotalPrice = 0;
        for (int i = 0; i < cart.size(); i++){
            TotalPrice+=cart.get(i).getCost();
        }
        try {
            if (user.getWallet() < TotalPrice) {
                //context.result("Insufficient money");
                throw new InsufficientMoney("Insufficient money");
            }
        }catch (InsufficientMoney e){
            context.result(e.getMessage());
            return;
        }
        user = loghme.finalizeOrder(user,mapper,cart);
        context.cookieStore("user",user);
        context.status(200);

    }
}
