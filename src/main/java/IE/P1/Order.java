package IE.P1;





public class Order {
    private String foodName;
    private String restaurantName;
    private int NumOfOrder;

    public Order(String foodName, String restaurantName, int numOfOrder) {
        this.foodName = foodName;
        this.restaurantName = restaurantName;
        NumOfOrder = numOfOrder;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public int getNumOfOrder() {
        return NumOfOrder;
    }

    public void setNumOfOrder(int numOfOrder) {
        this.NumOfOrder = numOfOrder;
    }

    public void AddNum() {
        this.NumOfOrder += 1;
    }
}
