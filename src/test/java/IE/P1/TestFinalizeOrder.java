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
        mapper = new ObjectMapper();
    }

    @Test
    public void testSubmittedFile() throws IOException {
        String json = mapper.writeValueAsString(cart);
        assertEquals("[{\"foodName\":\"sandevich makhsoose bache haye fanni\",\"restaurantName\":\"boofe\",\"numOfOrder\":1},{\"foodName\":\"ba ostokhan\",\"restaurantName\":\"boofe\",\"numOfOrder\":1},{\"foodName\":\"pizza makhsoose fanni\",\"restaurantName\":\"boofe\",\"numOfOrder\":2}]",json);
    }

    @Test
    public void testSubmittedList() {
        mapper = new ObjectMapper();
        System.out.println(orderList);
        assertEquals("[{\"foodName\":\"sandevich makhsoose bache haye fanni\",\"numOfOrder\":\"1\"},{\"foodName\":\"ba ostokhan\",\"numOfOrder\":\"1\"},{\"foodName\":\"pizza makhsoose fanni\",\"numOfOrder\":\"2\"}]",orderList);
    }

    @Test
    public void isCartEmptytest() {
        assertTrue(cart.isEmpty());
    }

    @Test
    public void isFileCartEmpty() throws IOException {
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
