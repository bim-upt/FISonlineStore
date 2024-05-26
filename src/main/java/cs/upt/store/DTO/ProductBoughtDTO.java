package cs.upt.store.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ProductBoughtDTO {
    @NotEmpty(message = "Code mandatory")
    @NotNull(message = "Code mandatory")
    private String code;
    @NotEmpty(message = "Code mandatory")
    @NotNull(message = "Code mandatory")
    private String seller;

    private String message;
    private boolean status;

    public String getCode() {
        return code;
    }
    public String getSeller() {
        return seller;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public void setSeller(String seller) {
        this.seller = seller;
    }
    public ProductBoughtDTO(@NotEmpty(message = "Code mandatory") @NotNull(message = "Code mandatory") String code,
            @NotEmpty(message = "Code mandatory") @NotNull(message = "Code mandatory") String seller) {
        this.code = code;
        this.seller = seller;
    }
    public ProductBoughtDTO() {
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
    
}
