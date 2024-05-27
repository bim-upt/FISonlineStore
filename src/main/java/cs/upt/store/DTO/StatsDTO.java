package cs.upt.store.DTO;

public class StatsDTO {
    private int buyers;
    private int count;
    private String message;
    private boolean status;
    public void setBuyers(int buyers) {
        this.buyers = buyers;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public int getBuyers() {
        return buyers;
    }
    public int getCount() {
        return count;
    }
    public String getMessage() {
        return message;
    }
    public boolean isStatus() {
        return status;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public StatsDTO() {
    }
    public StatsDTO(int buyers, int count, String message, boolean status) {
        this.buyers = buyers;
        this.count = count;
        this.message = message;
        this.status = status;
    }
    
}
