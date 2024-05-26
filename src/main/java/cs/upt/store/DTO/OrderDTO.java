package cs.upt.store.DTO;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import cs.upt.store.model.Order;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class OrderDTO {
    @Id
    private ObjectId oid;


    @NotEmpty(message = "There must be products")
    @NotNull(message = "There must be products")
    private List<ProductBoughtDTO> products;

    @NotNull(message = "There must be a buyer")
    @NotEmpty(message = "There must be a buyer")
    private String buyer;

    private boolean finished;

    private String message;

    private boolean status;

    public void setOid(ObjectId oid) {
        this.oid = oid;
    }

    public OrderDTO() {
    }
   

    public void setProducts(List<ProductBoughtDTO> products) {
        this.products = products;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public ObjectId getOid() {
        return oid;
    }

    public List<ProductBoughtDTO> getProducts() {
        return products;
    }

    public String getBuyer() {
        return buyer;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isFinished() {
        return finished;
    }

    public String getMessage() {
        return message;
    }

    
}
