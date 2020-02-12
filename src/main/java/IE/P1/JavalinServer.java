package IE.P1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JavalinServer {

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Restaurant> restaurants = mapper.readValue(new URL("http://138.197.181.131:8080/restaurants " )
                , new TypeReference<List<Restaurant>>() {
                });
        System.out.println(restaurants.get(0).getId());
    }
}
