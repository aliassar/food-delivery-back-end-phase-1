package IE.P1;
import java.io.File;
import java.io.IOException;
import java.util.*;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


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

    public void addRestaurant(String Order, String restaurantsPath, ObjectMapper mapper, ArrayList<Restaurant> restaurants) throws IOException {
        Restaurant restaurant = mapper.readValue(Order, Restaurant.class);
        boolean Addable = true;
        for (Restaurant value : restaurants) {
            if (value.getName().equals(restaurant.getName())) {
                Addable = false;
                break;
            }
        }
        if(Addable){
            //System.out.println("hi");
            restaurants.add(restaurant);
            mapper.writeValue(new File(restaurantsPath),restaurants);
        }
    }
    public void addFood(String Order, String restaurantsPath, ObjectMapper mapper, ArrayList<Restaurant> restaurants) throws IOException {
        Food food = mapper.readValue(Order, Food.class);
        System.out.println(food.getRestaurantName());
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
                if (restaurants.get(index).getMenu().get(i).getName().equals(food.getName())) {
                    Addable = false;
                    break;
                }
            }
        }
        if (Addable){
            restaurants.get(index).addToMenu(food);
            mapper.writeValue(new File(restaurantsPath),restaurants);

        }
    }
    public void getRestaurants(ArrayList<Restaurant> restaurants) {
        for (Restaurant restaurant : restaurants) {
            System.out.println(restaurant.getName());
        }
    }
    public void getRestaurant(String Order, ObjectMapper mapper, ArrayList<Restaurant> restaurants) throws IOException {
        //System.out.println(Order);
        Restaurant restaurant = mapper.readValue(Order, Restaurant.class);
        //System.out.println(restaurant.getName());
        boolean getable = false;

        for (Restaurant value : restaurants) {
            if (value.getName().equals(restaurant.getName())) {

                getable = true;
                String json = mapper.writeValueAsString(value);
                System.out.println(json);
                break;
            }
        }
        if(!getable){
            System.out.println("restaurant not found");
        }

    }
    public void getFood(String Order,ObjectMapper mapper, ArrayList<Restaurant> restaurants) throws IOException {
        Food food = mapper.readValue(Order, Food.class);
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
        else { System.out.println("restaurant not found");}
    }
    public void addToCart(String Order,String cartPath,User user, ObjectMapper mapper, ArrayList<Restaurant> restaurants, ArrayList<Order> cart) throws IOException {
        Food food = mapper.readValue(Order, Food.class);
        if (restaurants.size() == 0){
            System.out.println("there is no restaurants to choose");
            return;
        }

        boolean addable = false ;

        for (IE.P1.Order value : cart) {
            if (value.getRestaurantName().equals(food.getRestaurantName())) {
                addable = true;
                break;
            }
        }

        if (cart.size() == 0){addable = true;}

        boolean SameFood = false;
        if(addable){
            for (int i=0; i < cart.size(); i++ ) {
                if (cart.get(i).getFoodName().equals(food.getName())){
                    cart.get(i).AddNum();
                    SameFood = true;
                    mapper.writeValue(new File(cartPath),cart);
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
                mapper.writeValue(new File(cartPath),cart);
            }
        }
        else{
            System.out.println("different restaurant");
        }
    }
    public void getCart(ObjectMapper mapper, ArrayList<Order> cart) throws IOException {
        String json = mapper.writeValueAsString(cart);
        System.out.println(json);
    }
    public void finalizeOrder(String cartPath,User user, ObjectMapper mapper, ArrayList<Order> cart) throws IOException {
        String json = mapper.writeValueAsString(cart);
        System.out.println(json);
        System.out.println("Order recorded successfully");
        cart.clear();
        user.finalizeOrder();
        mapper.writeValue(new File(cartPath),cart);
    }
    public void getRecommendedRestaurants(ArrayList<Restaurant> restaurants) {
        HashMap<String,Double> suggestions = new HashMap<String, Double>() ;
        for (Restaurant restaurant : restaurants) {
            suggestions.put(restaurant.getName(), restaurant.calculatePopularity());
            //System.out.println(restaurants.get(i).calculatePopularity());
        }
        if (restaurants.size()<3){
            for (Restaurant restaurant : restaurants) {
                System.out.println(restaurant.getName());
            }
            return;
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
            System.out.println(maxEntry != null ? maxEntry.getKey() : null);
            suggestions.remove(maxEntry != null ? maxEntry.getKey() : null);
        }
    }

    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
        ArrayList<Order> NewOrders = new ArrayList<Order>();
        User user = new User();
        user.setOrders(NewOrders);
        while (true){
            Scanner input = new Scanner(System.in);
            String myString = input.next();
            String Order = input.nextLine();
            ObjectMapper mapper = new ObjectMapper();
            ArrayList<Restaurant> restaurants = mapper.readValue(new File("src/main/resources/restaurants.json")
                    , new TypeReference<List<Restaurant>>(){});
            ArrayList<Order> cart = mapper.readValue(new File("src/main/resources/cart.json")
                    , new TypeReference<List<Order>>(){});
            if(myString.equals("exit")){
                break;
            }
            else if (myString.equals("addRestaurant")){
                user.addRestaurant(Order,"src/main/resources/restaurants.json",mapper,restaurants);
            }
            else if (myString.equals("addFood")){
                user.addFood(Order,"src/main/resources/restaurants.json",mapper,restaurants);
            }
            else if (myString.equals("getRestaurants")){
                user.getRestaurants(restaurants);
            }else if (myString.equals("getRestaurant")){
                user.getRestaurant(Order,mapper,restaurants);
            }
            else if (myString.equals("getFood")){
                user.getFood(Order,mapper,restaurants);
            }
            else if (myString.equals("addToCart")){
                user.addToCart(Order,"src/main/resources/cart.json",user,mapper,restaurants,cart);
            }
            else if (myString.equals("getCart")){
                user.getCart(mapper,cart);
            }
            else if (myString.equals("finalizeOrder")){
                user.finalizeOrder("src/main/resources/cart.json",user,mapper,cart);
            }
            else if (myString.equals("getRecommendedRestaurants")){
                user.getRecommendedRestaurants(restaurants);
            }
            else{
                System.out.println(myString);
            }
        }
    }
}

