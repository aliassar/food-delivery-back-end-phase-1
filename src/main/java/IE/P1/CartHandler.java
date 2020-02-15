package IE.P1;

import IE.P1.Exceptions.DifRestaurants;
import IE.P1.Exceptions.NoRestaurant;
import IE.P1.Exceptions.WrongFood;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CartHandler {




    public static void GetCart(Context context) throws IOException {
        User user = context.cookieStore("user");
        ArrayList<Order> cart = user.getOrders();
        if (cart.size() == 0){
            context.result("cart is empty");
        }
        else {
            context.json(cart);
        }

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
        float price = Float.valueOf(context.formParam("price"));
        Food food = new Food(name,FoodName,price);
        try {
            user = loghme.addToCart(food,user,mapper,restaurants,cart);
        }
        catch (NoRestaurant e){
            context.result(e.getMessage());
        }
        catch (WrongFood e){
            context.result(e.getMessage());
        }
        catch (DifRestaurants e){
            context.result(e.getMessage());
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
        if (cart.size() == 0){
            context.result("cart is empty");
        }
        ObjectMapper mapper = new ObjectMapper();
        float TotalPrice = 0;
        for (int i = 0; i < cart.size(); i++){
            TotalPrice+=cart.get(i).getCost();
        }
        if (user.getWallet()<TotalPrice){
            context.result("Insufficient money");
        }
        user = loghme.finalizeOrder(user,mapper,cart);
        context.cookieStore("user",user);
        context.status(200);

    }
}
