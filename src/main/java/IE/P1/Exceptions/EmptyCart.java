package IE.P1.Exceptions;

public class EmptyCart extends Exception {
    public EmptyCart(String errorMessage){
        super(errorMessage);

    }
}
