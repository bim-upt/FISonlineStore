package cs.upt.store.exceptions;

public class CardExistsException extends Exception{
    public CardExistsException(){}
    public CardExistsException(String msg){
        super(msg);
    }
}
