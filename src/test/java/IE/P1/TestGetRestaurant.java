package IE.P1;

import io.javalin.http.Context;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.*;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;


public class TestGetRestaurant {
    private Context ctx = mock(Context.class);

    @BeforeClass
    public static void setup() throws IOException {
        JavalinServer.serverConfig(true);
        JavalinServer.startServer(12336);
    }


    @Test
    public void checkOutOfBoundaryStatus() {
        HttpResponse response = Unirest.get("http://localhost:12336/r/5e445b6c6ab90e0af6068d9e").asString();
        assertEquals(403, response.getStatus());
        assertEquals("chosen restaurant is too far", response.getBody());
    }

    @Test
    public void checkInvalidRestaurantStatus() {
        HttpResponse response = Unirest.get("http://localhost:12336/r/5e445b6c6ab923068d9e").asString();
        assertEquals(404, response.getStatus());
        assertEquals("no such restaurant found", response.getBody());
    }

    @Test
    public void checkGetRestaurant() {
        HttpResponse response = Unirest.get("http://localhost:12336/r/5e445b6c6ab90e0af6068d3f").asString();
        assertEquals(200, response.getStatus());
    }

    @AfterClass
    public static void finalizing() {
        JavalinServer.stopServer();
    }
}
