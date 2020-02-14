package IE.P1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Loghme {

    public User addToCart(Food food, User user, ObjectMapper mapper, ArrayList<Restaurant> restaurants, ArrayList<Order> cart) throws IOException {

        //Food food = mapper.readValue(Order, Food.class);
        if (restaurants.size() == 0) {
            System.out.println("there is no restaurants to choose");
            return user;
        }
        boolean UnknownFood = true;
        Restaurant rest = new Restaurant();
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName().equals(food.getRestaurantName())) {
                rest = restaurant;
                UnknownFood = false;

            }
        }
        if (UnknownFood){
            System.out.println("there is no restaurant with that name");
            return user;
        }
        UnknownFood = true;
        for (int i = 0; i < rest.getMenu().size(); i++){
            if (rest.getMenu().get(i).getName().equals(food.getName())) {
                UnknownFood = false;

                break;
            }
        }
        if (UnknownFood){
            System.out.println("no food with this name in this restaurant");
            return user;
        }


        boolean addable = false;

        for (IE.P1.Order value : cart) {
            //System.out.println(food.getRestaurantName());
            if (value.getRestaurantName().equals(food.getRestaurantName())) {
                addable = true;
                break;
            }
        }

        if (cart.size() == 0) {
            addable = true;
        }

        boolean SameFood = false;
        if (addable) {
            for (int i = 0; i < user.getOrders().size(); i++) {
                if (user.getOrders().get(i).getFoodName().equals(food.getName())) {
                    user.getOrders().get(i).setCost(user.getOrders().get(i).getCost()/user.getOrders().get(i).getNumOfOrder());
                    user.getOrders().get(i).AddNum();
                    SameFood = true;
                    return user;
                    //mapper.writeValue(new File(cartPath), cart);


                }
            }
            if (!SameFood) {
                Order order = new Order(food.getName(),food.getRestaurantName(),1,food.getPrice());
//                order.setFoodName(food.getName());
//                order.setRestaurantName(food.getRestaurantName());
//                order.setNumOfOrder(1);
                cart.add(order);
                user.AddToCart(order);
                return user;
                //System.out.println(order.getRestaurantName());
                //mapper.writeValue(new File(cartPath), cart);
            }
        } else {
            System.out.println("different restaurant");
            return user;
        }
        return user;
    }


    public User finalizeOrder( User user, ObjectMapper mapper, ArrayList<Order> cart) throws IOException {
        SimpleModule module =
                new SimpleModule();
        module.addSerializer(Order.class, new CustomCartSerializer());
        mapper.registerModule(module);
        String cartJson = mapper.writeValueAsString(cart);
        System.out.println(cartJson);
        System.out.println("Order recorded successfully");
        cart.clear();
        user.cleanOrders();
        //mapper.writeValue(new File(cartPath), cart);
        return user;
    }
}
