package cs.upt.store.DTO;

public class HashedUserDTO {
    private String message;
    private boolean status;
    private String username;
    private int type; //0 - normal, 1 = seller
    public HashedUserDTO(String message, String username, boolean status, int type){
        this.message = message;
        this.status = status;
        this.username = username;
        this.type = type;
    }
    public HashedUserDTO(){}
    public String getMessage() {
        return message;
    }
    public boolean isStatus() {
        return status;
    }
    public String getUsername() {
        return username;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    
}

