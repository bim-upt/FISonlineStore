package cs.upt.store.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ProductSoldDTO {
    @NotEmpty(message = "Code mandatory")
    @NotNull(message = "Code mandatory")
    private String code;
    @NotEmpty(message = "Buyer mandatory")
    @NotNull(message = "Buyer mandatory")
    private String buyer;
    public String getCode() {
        return code;
    }
    public String getBuyer() {
        return buyer;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }
    public ProductSoldDTO(@NotEmpty(message = "Code mandatory") @NotNull(message = "Code mandatory") String code,
            @NotEmpty(message = "Buyer mandatory") @NotNull(message = "Buyer mandatory") String buyer) {
        this.code = code;
        this.buyer = buyer;
    }
    public ProductSoldDTO() {
    }
    
}
