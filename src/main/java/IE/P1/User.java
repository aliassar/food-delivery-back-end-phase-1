package IE.P1;
import java.io.File;
import java.io.IOException;
import java.util.*;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class User {
    private ArrayList<Order> orders;

    public void finalizeOrder(){
        this.orders.clear();
    }

    public void AddToCart(Order order){
        orders.add(order);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
        ArrayList<Order> NewOrders = new ArrayList<Order>();
        User user = new User();
        user.setOrders(NewOrders);
        while (true){
            Scanner input = new Scanner(System.in);
            String myString = input.next();
            if(myString.equals("exit")){
                break;
            }
            else if (myString.equals("addRestaurant")){
                String Order = input.nextLine();
                System.out.println(Order);
                ObjectMapper mapper = new ObjectMapper();
                Restaurant restaurant = mapper.readValue(Order, Restaurant.class);
                ArrayList<Restaurant> restaurants = mapper.readValue(new File("src/main/resources/restaurants.json")
                        , new TypeReference<List<Restaurant>>(){});
                boolean Addable = true;
                for (int i = 0; i < restaurants.size(); i++){
                    if(restaurants.get(i).getName().equals(restaurant.getName())){
                        Addable = false;
                    }
                }
                if(Addable){
                    //System.out.println("hi");
                    restaurants.add(restaurant);
                    mapper.writeValue(new File("src/main/resources/restaurants.json"),restaurants);
                }

            }
            else if (myString.equals("addFood")){
                String Order = input.nextLine();
                System.out.println(Order);
                ObjectMapper mapper = new ObjectMapper();
                Food food = mapper.readValue(Order, Food.class);
                System.out.println(food.getRestaurantName());
                ArrayList<Restaurant> restaurants = mapper.readValue(new File("src/main/resources/restaurants.json")
                        , new TypeReference<List<Restaurant>>(){});
                boolean Addable = false;
                int index = -1;
                for (int i = 0; i < restaurants.size(); i++){
                    if(restaurants.get(i).getName().equals(food.getRestaurantName())){
                        Addable = true;
                        index = i;
                    }
                }
                if (!Addable){System.out.println("restaurant not found");}
                if (Addable){
                    for (int i = 0; i<restaurants.get(index).getMenu().size(); i++){
                        if (restaurants.get(index).getMenu().get(i).getName().equals(food.getName())){
                            Addable = false;
                        }
                    }
                }
                if (Addable){
                    restaurants.get(index).addToMenu(food);
                    mapper.writeValue(new File("src/main/resources/restaurants.json"),restaurants);

                }


            }
            else if (myString.equals("getRestaurants")){
                ObjectMapper mapper = new ObjectMapper();
                ArrayList<Restaurant> restaurants = mapper.readValue(new File("src/main/resources/restaurants.json")
                        , new TypeReference<List<Restaurant>>(){});
                for (int i = 0; i < restaurants.size(); i++){
                    System.out.println(restaurants.get(i).getName());
                }
            }
            else if (myString.equals("getRestaurant")){
                String Order = input.nextLine();
                //System.out.println(Order);
                ObjectMapper mapper = new ObjectMapper();
                Restaurant restaurant = mapper.readValue(Order, Restaurant.class);
                //System.out.println(restaurant.getName());
                ArrayList<Restaurant> restaurants = mapper.readValue(new File("src/main/resources/restaurants.json")
                        , new TypeReference<List<Restaurant>>(){});
                boolean getable = false;

                for (int i = 0; i < restaurants.size(); i++){
                    if (restaurants.get(i).getName().equals(restaurant.getName())){

                        getable = true;
                        String json = mapper.writeValueAsString(restaurants.get(i));
                        System.out.println(json);
                        break;
                    }
                }
                if(!getable){
                    System.out.println("restaurant not found");
                }
            }
            else if (myString.equals("getFood")){
                String Order = input.nextLine();
                ObjectMapper mapper = new ObjectMapper();
                Food food = mapper.readValue(Order, Food.class);
                ArrayList<Restaurant> restaurants = mapper.readValue(new File("src/main/resources/restaurants.json")
                        , new TypeReference<List<Restaurant>>(){});
                boolean getable = false;
                int index = -1;
                for (int i = 0; i < restaurants.size(); i++){
                    if (restaurants.get(i).getName().equals(food.getRestaurantName())){
                        index = i;
                        getable = true;
                    }
                }
                if (getable){
                    for (int i = 0; i < restaurants.get(index).getMenu().size();i++){
                        if (restaurants.get(index).getMenu().get(i).getName().equals(food.getName())){
                            String json = mapper.writeValueAsString(restaurants.get(index).getMenu().get(i));
                            System.out.println(json);
                            break;
                        }
                    }
                }
                else if(!getable){ System.out.println("restaurant not found");}
            }
            else if (myString.equals("addToCart")){
                String Order = input.nextLine();
                ObjectMapper mapper = new ObjectMapper();
                Food food = mapper.readValue(Order, Food.class);
                ArrayList<Order> cart = mapper.readValue(new File("src/main/resources/cart.json")
                        , new TypeReference<List<Order>>(){});
                ArrayList<Restaurant> restaurants = mapper.readValue(new File("src/main/resources/restaurants.json")
                        , new TypeReference<List<Restaurant>>(){});
                if (restaurants.size() == 0){
                    System.out.println("there is no restaurants to choose");
                    continue;
                }

                boolean addable = false ;
                for (int i = 0; i < cart.size(); i++ ) {



                    if (cart.get(i).getRestaurantName().equals(food.getRestaurantName()))
                        addable = true;
                }
                if (cart.size() == 0){addable = true;}

                boolean SameFood = false;
                if(addable){
                    for (int i=0; i < cart.size(); i++ ) {
                        if (cart.get(i).getFoodName().equals(food.getName())){
                            cart.get(i).AddNum();
                            SameFood = true;
                            mapper.writeValue(new File("src/main/resources/cart.json"),cart);
                            break;
                        }
                    }
                    if (!SameFood){
                        Order order = new Order();
                        order.setFoodName(food.getName());
                        order.setRestaurantName(food.getRestaurantName());
                        order.setNumOfOrder(1);
                        cart.add(order);
                        user.AddToCart(order);
                        mapper.writeValue(new File("src/main/resources/cart.json"),cart);
                    }
                }
                else{
                    System.out.println("different restaurant");
                }
            }
            else if (myString.equals("getCart")){
                ObjectMapper mapper = new ObjectMapper();
                ArrayList<Order> cart = mapper.readValue(new File("src/main/resources/cart.json")
                        , new TypeReference<List<Order>>(){});
                String json = mapper.writeValueAsString(cart);
                System.out.println(json);


            }
            else if (myString.equals("finalizeOrder")){
                ObjectMapper mapper = new ObjectMapper();
                ArrayList<Order> cart = mapper.readValue(new File("src/main/resources/cart.json")
                        , new TypeReference<List<Order>>(){});
                String json = mapper.writeValueAsString(cart);
                System.out.println(json);
                System.out.println("Order recorded successfully");
                cart.clear();
                user.finalizeOrder();
                mapper.writeValue(new File("src/main/resources/cart.json"),cart);
            }

            else if (myString.equals("getRecommendedRestaurants")){
                ObjectMapper mapper = new ObjectMapper();
                ArrayList<Restaurant> restaurants = mapper.readValue(new File("src/main/resources/restaurants.json")
                        , new TypeReference<List<Restaurant>>(){});
                HashMap<String,Double> suggestions = new HashMap<String, Double>() ;
                for (int i = 0; i<restaurants.size(); i++){
                    suggestions.put(restaurants.get(i).getName(),restaurants.get(i).calculatePopularity());
                    //System.out.println(restaurants.get(i).calculatePopularity());
                }
                if (restaurants.size()<3){
                    for (int i = 0; i < restaurants.size(); i++){
                        System.out.println(restaurants.get(i).getName());
                    }
                    continue;
                }
                for (int i = 0; i < 3; i++){
                    Map.Entry<String, Double> maxEntry = null;

                    for (Map.Entry<String, Double> entry : suggestions.entrySet())
                    {
                        if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
                        {
                            maxEntry = entry;
                        }
                    }
                    System.out.println(maxEntry.getKey());
                    suggestions.remove(maxEntry.getKey());
                }


            }


            else{
                System.out.println(myString);

            }
        }
    }
}

