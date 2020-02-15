package IE.P1.models;





public class Order {
    private String foodName;
    private String restaurantName;
    private float cost;
    private int NumOfOrder;


    public Order(String foodName, String restaurantName, int numOfOrder, float cost) {
        this.foodName = foodName;
        this.restaurantName = restaurantName;
        NumOfOrder = numOfOrder;
        this.cost = cost;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
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
