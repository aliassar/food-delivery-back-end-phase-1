package IE.P1;

import IE.P1.models.User;
import io.javalin.http.Context;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.*;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class TestAddToCart {
    private Context ctx = mock(Context.class);
    private static User user ;

    @BeforeClass
    public static void setup() throws IOException {
        user = JavalinServer.serverConfig(true);
        JavalinServer.startServer(12336);
    }


    @Test
    public void checkNoRestaurant() {
        HttpResponse response = Unirest.post("http://localhost:12336/cart")
                .field("name", "جوجه")
                .field("restaurantName", "جدید")
                .field("price", "1000")
                .asString();
        assertEquals(401, response.getStatus());
        assertEquals("there is no restaurant with that name", response.getBody());
        user.cleanOrders();
    }

    @Test
    public void checkDiffRestaurant() {
        HttpResponse response = Unirest.post("http://localhost:12336/cart")
                .field("name", "پیتزا مخصوص")
                .field("restaurantName", "پیتزا همبرگر بهروز (اصلی)")
                .field("price", "1000")
                .asString();
        HttpResponse response2 = Unirest.post("http://localhost:12336/cart")
                .field("name", "چلو کباب کوبیده سنتی ( دو سیخ )")
                .field("restaurantName", "کترینگ و تشریفات اسنوژی")
                .field("price", "20000.0")
                .asString();
        assertEquals(401, response2.getStatus());
        assertEquals("you can not choose different restaurant", response2.getBody());
        user.cleanOrders();
    }

    @Test
    public void checkWrongFood() {
        HttpResponse response = Unirest.post("http://localhost:12336/cart")
                .field("name", "جوجه")
                .field("restaurantName", "کترینگ و تشریفات اسنوژی")
                .field("price", "1000")
                .asString();
        assertEquals(401, response.getStatus());
        assertEquals("no food with this name in this restaurant", response.getBody());
        user.cleanOrders();
    }

    @Test
    public void checkWithoutError() {
        HttpResponse response = Unirest.post("http://localhost:12336/cart")
                .field("name", "چلو کباب کوبیده سنتی ( دو سیخ )")
                .field("restaurantName", "کترینگ و تشریفات اسنوژی")
                .field("price", "20000.0")
                .asString();
        assertEquals(200, response.getStatus());
        user.cleanOrders();
    }

    @AfterClass
    public static void finalizing() {
        JavalinServer.stopServer();
    }
}
