package cs.upt.store.DTO;


public class HashedCardDTO {
    private String message;
    private boolean status;
    public HashedCardDTO(String message, boolean status){
        this.message = message;
        this.status = status;
    }
 
    public void setMessage(String message) {
        this.message = message;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public boolean isStatus() {
        return status;
    }

    
    
}
