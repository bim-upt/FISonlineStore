package cs.upt.store.DTO;

import java.util.List;

public class HistoryDTO {
    private List<ProductBoughtDTO> history;
    private String message;
    private boolean status;
    public List<ProductBoughtDTO> getHistory() {
        return history;
    }
    public String getMessage() {
        return message;
    }
    public boolean isStatus() {
        return status;
    }
    public void setHistory(List<ProductBoughtDTO> history) {
        this.history = history;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public HistoryDTO(List<ProductBoughtDTO> history, String message, boolean status) {
        this.history = history;
        this.message = message;
        this.status = status;
    }
    public HistoryDTO() {
    }
    
}
