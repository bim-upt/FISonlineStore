package cs.upt.store.model;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cs.upt.store.DTO.OrderDTO;
import cs.upt.store.service.ProductService;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "orders")
public class Order{

    @Id
	private ObjectId oid;
    
    @NotNull(message = "There must be products")
    private List<ObjectId> products;

    @NotNull(message = "There must be a buyer")
    private String buyer;

    @NotNull(message = "Order mustn't be in limbo")
    @Range(min = 0, max = 1)
    private int status;

    public ObjectId getOid() {
        return oid;
    }

    public Order() {
    }

    public Order(@NotNull(message = "There must be products") List<ObjectId> products,
            @NotNull(message = "There must be a buyer") String buyer) {
        this.products = products;
        this.buyer = buyer;
    }


    public Order(OrderDTO order, ProductService productService){
        this.oid = order.getOid();
        this.buyer = order.getBuyer();
        this.status = 1;
        this.products = new ArrayList<ObjectId>();
        for(int i = 0; i < order.getProducts().size(); i++){
            Product current = productService.findByCodeAndSeller(order.getProducts().get(i).getCode(),order.getProducts().get(i).getSeller());
            if(current != null){
                products.add(current.getPid());
            }
        }
    }

    public void setOid(ObjectId oid) {
        this.oid = oid;
    }

    public void setProducts(List<ObjectId> products) {
        this.products = products;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public List<ObjectId> getProducts() {
        return products;
    }

    public String getBuyer() {
        return buyer;
    }
    
}

