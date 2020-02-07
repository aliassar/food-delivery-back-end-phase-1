package IE.P1;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class TestGetRecommendedRestaurants {
    public User user;
    public ObjectMapper mapper;
    public String orderList;
    public ArrayList<Restaurant> restaurants;
    public ArrayList<Order> cart;

    @Before
    public void setup() throws JsonParseException, JsonMappingException, IOException {
        ArrayList<Order> NewOrders = new ArrayList<Order>();
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
        user.addRestaurant("{\"name\": \"Hesturan\", \"description\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}, \"menu\": [{\"name\": \"Gheime\", \"description\": \"it’s yummy!\", \"popularity\": 0.8, \"price\": 20000}, {\"name\": \"Kabab\", \"description\": \"it’s delicious!\", \"popularity\": 0.6, \"price\": 30000}]}","src/test/resources/restaurants.json", mapper, restaurants);
        user.addFood("{\"name\": \"gheime\", \"description\": \"it’s yummy!\", \"popularity\": 0.8, \"restaurantName\": \"Hesturan\", \"price\": 20000}","src/test/resources/restaurants.json", mapper, restaurants);
        user.addToCart( "{\"foodName\": \"Kabab\", \"restaurantName\": \"Hesturan\"}","src/test/resources/cart.json", user, mapper, restaurants, cart);
    }

    @Test
    public void testGetRecommendedRestaurants() {
        ArrayList<String> test = new ArrayList<String>();
        test.add("Hesturan");
        ArrayList<String> recommendedRestaurants = user.getRecommendedRestaurants(restaurants);
        assertEquals(recommendedRestaurants,test);
    }
}
