package IE.P1;

import IE.P1.models.Order;
import IE.P1.models.Restaurant;
import IE.P1.models.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestFinalizeOrder {
    public User user;
    public ObjectMapper mapper;
    public String orderList;
    public ArrayList<Restaurant> restaurants;
    public ArrayList<Order> cart;

    @Before
    public void setup() throws IOException {
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
        mapper = new ObjectMapper();
        user.addRestaurant("{\"name\": \"Hesturan\", \"description\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}, \"menu\": [{\"name\": \"Gheime\", \"description\": \"it’s yummy!\", \"popularity\": 0.8, \"price\": 20000}, {\"name\": \"Kabab\", \"description\": \"it’s delicious!\", \"popularity\": 0.6, \"price\": 30000}]}","src/test/resources/restaurants.json", mapper, restaurants);
        mapper = new ObjectMapper();
        user.addRestaurant("{\"name\": \"akbarjooje\", \"description\": \"perfect\", \"location\": {\"x\": 4, \"y\": 6}, \"menu\": [{\"name\": \"Joje\", \"description\": \"it’s perfect!\", \"popularity\": 0.9, \"price\": 27000}, {\"name\": \"JojeBaSos\", \"description\": \"it’s really delicious!\", \"popularity\": 0.95, \"price\": 23000}]}","src/test/resources/restaurants.json", mapper, restaurants);
        mapper = new ObjectMapper();
        user.addRestaurant("{\"name\": \"ame kati\", \"description\": \"cheap\", \"location\": {\"x\": 2, \"y\": 4}, \"menu\": [{\"name\": \"salad\", \"description\": \"with food\", \"popularity\": 0.7, \"price\": 10000}, {\"name\": \"jooje\", \"description\": \"not bad\", \"popularity\": 0.6, \"price\": 15000}]}","src/test/resources/restaurants.json", mapper, restaurants);
        mapper = new ObjectMapper();
        user.addRestaurant("{\"name\": \"perperook\", \"description\": \"pro pizzas\", \"location\": {\"x\": 4, \"y\": 3}, \"menu\": [{\"name\": \"pizza arosto\", \"description\": \"pro pizza #1\", \"popularity\": 0.85, \"price\": 44000}, {\"name\": \"pizza shelf\", \"description\": \"pro pizza #2\", \"popularity\": 0.98, \"price\": 36000}]}","src/test/resources/restaurants.json", mapper, restaurants);
        mapper = new ObjectMapper();
        user.addRestaurant("{\"name\": \"pizza hot\", \"description\": \"delicious pizzas\", \"location\": {\"x\": 2, \"y\": 2}, \"menu\": [{\"name\": \"pizza makhloot\", \"description\": \"deliciously good\", \"popularity\": 0.7, \"price\": 34000}, {\"name\": \"pizza american\", \"description\": \"latin\", \"popularity\": 0.73, \"price\": 31000}]}","src/test/resources/restaurants.json", mapper, restaurants);
        mapper = new ObjectMapper();
        user.addRestaurant("{\"name\": \"boofe\", \"description\": \"boofe hast dige\", \"location\": {\"x\": 1, \"y\": 2}, \"menu\": [{\"name\": \"sandevich makhsoose bache haye fanni\", \"description\": \"not very bad\", \"popularity\": 0.4, \"price\": 18000}, {\"name\": \"pizza makhsoose fanni\", \"description\": \"latin\", \"popularity\": 0.6, \"price\": 13000}]}","src/test/resources/restaurants.json", mapper, restaurants);
        mapper = new ObjectMapper();
        user.addFood("{\"name\": \"ghorme\", \"description\": \"very cheap\", \"popularity\": 0.3, \"restaurantName\": \"ame kati\", \"price\": 12000}","src/test/resources/restaurants.json", mapper, restaurants);
        mapper = new ObjectMapper();
        user.addFood("{\"name\": \"ba ostokhan\", \"description\": \"it’s muscles!\", \"popularity\": 0.6, \"restaurantName\": \"boofe\", \"price\": 23000}","src/test/resources/restaurants.json", mapper, restaurants);
        mapper = new ObjectMapper();
        user.addToCart( "{\"foodName\": \"sandevich makhsoose bache haye fanni\", \"restaurantName\": \"boofe\"}","src/test/resources/cart.json", user, mapper, restaurants, cart);
        mapper = new ObjectMapper();
        user.addToCart( "{\"foodName\": \"ba ostokhan\", \"restaurantName\": \"boofe\"}","src/test/resources/cart.json", user, mapper, restaurants, cart);
        mapper = new ObjectMapper();
        user.addToCart( "{\"foodName\": \"pizza makhsoose fanni\", \"restaurantName\": \"boofe\"}","src/test/resources/cart.json", user, mapper, restaurants, cart);
        mapper = new ObjectMapper();
        user.addToCart( "{\"foodName\": \"pizza makhsoose fanni\", \"restaurantName\": \"boofe\"}","src/test/resources/cart.json", user, mapper, restaurants, cart);

    }

    @Test
    public void testSubmittedFile() throws IOException {
        String json = mapper.writeValueAsString(cart);
        assertEquals("[{\"foodName\":\"sandevich makhsoose bache haye fanni\",\"restaurantName\":\"boofe\",\"numOfOrder\":1},{\"foodName\":\"ba ostokhan\",\"restaurantName\":\"boofe\",\"numOfOrder\":1},{\"foodName\":\"pizza makhsoose fanni\",\"restaurantName\":\"boofe\",\"numOfOrder\":2}]",json);
    }

    @Test
    public void testSubmittedList() throws IOException {
        mapper = new ObjectMapper();
        orderList = user.finalizeOrder("src/main/resources/cart.json",user,mapper,cart);
        System.out.println(orderList);
        assertEquals("[{\"foodName\":\"sandevich makhsoose bache haye fanni\",\"numOfOrder\":\"1\"},{\"foodName\":\"ba ostokhan\",\"numOfOrder\":\"1\"},{\"foodName\":\"pizza makhsoose fanni\",\"numOfOrder\":\"2\"}]",orderList);
    }

    @Test
    public void isCartEmptytest() throws IOException {
        orderList = user.finalizeOrder("src/main/resources/cart.json",user,mapper,cart);
        assertTrue(cart.isEmpty());
    }

    @Test
    public void isFileCartEmpty() throws IOException {
        orderList = user.finalizeOrder("src/main/resources/cart.json",user,mapper,cart);
        String json = mapper.writeValueAsString(cart);
        assertEquals(json, "[]");
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
