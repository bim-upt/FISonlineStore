package cs.upt.store.exceptions;

public class NonExistentCardException extends Exception{
    public NonExistentCardException(String msg){
        super(msg);
    }
    public NonExistentCardException(){
        super("Card does not exist in the db");
    }
}
