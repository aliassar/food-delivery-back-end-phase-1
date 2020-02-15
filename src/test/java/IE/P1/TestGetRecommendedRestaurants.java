package IE.P1;

import IE.P1.models.Order;
import IE.P1.models.Restaurant;
import IE.P1.models.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TestGetRecommendedRestaurants {
    public User user;
    public ObjectMapper mapper;
    public String orderList;
    public ArrayList<Restaurant> restaurants;
    public ArrayList<Order> cart;

    @Before
    public void setup() throws IOException {
        ArrayList<Order> NewOrders = new ArrayList<>();
        user = new User();
        user.setOrders(NewOrders);
        mapper = new ObjectMapper();
        restaurants = mapper.readValue(new File("src/main/resources/restaurants.json")
                , new TypeReference<List<Restaurant>>() {
                });
        cart = mapper.readValue(new File("src/main/resources/cart.json")
                , new TypeReference<List<Order>>() {
                });
        cart.clear();
        restaurants.clear();
        mapper.writeValue(new File("src/main/resources/restaurants.json"), restaurants);
        mapper.writeValue(new File("src/main/resources/cart.json"), cart);
    }

    @After
    public void finalize() throws IOException {
        restaurants.clear();
        cart.clear();
        mapper.writeValue(new File("src/main/resources/restaurants.json"), restaurants);
        mapper.writeValue(new File("src/main/resources/cart.json"), cart);
        user=null;
        mapper=null;
        orderList=null;
    }
}
