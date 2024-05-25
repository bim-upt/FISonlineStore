package cs.upt.store.exceptions;

public class InsufficientFundsException extends Exception{
    public InsufficientFundsException(String msg){
        super(msg);
    }
    public InsufficientFundsException(){
        super("Insufficient funds on card");
    }
}