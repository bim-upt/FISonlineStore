package cs.upt.store.exceptions;

public class NoEligibleProductsException extends Exception{
    public NoEligibleProductsException(){}
    public NoEligibleProductsException(String msg){
        super(msg);
    }
}
