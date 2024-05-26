package cs.upt.store.model;

import java.math.BigDecimal;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "products")
public class Product{
    @Id
	private ObjectId pid;
    
    @NotEmpty(message = "Product must have a name")
    @NotNull(message = "Product must have a name")
    private String name;

    @URL(message = "Invalid url")
    @NotNull(message = "Product must have an image")
    @NotEmpty(message = "Product must have an sdfsdfsdfdsfds")
    private String imgs; //urls

    @NotNull(message = "Product must have a code")
    @NotEmpty(message = "Product must have a code")
    private String code;

    //@DocumentReference(collection = "users")
    @NotNull(message = "Product must have a seller")
    private String seller;

    @Positive
    @NotNull
    private BigDecimal price;

   

    public Product(
        @NotEmpty(message = "Product must have a name") @NotNull(message = "Product must have a name") String name,
        @URL(message = "Invalid url") @NotNull(message = "Product must have an image") @NotEmpty(message = "Product must have an sdfsdfsdfdsfds") String imgs,
        @NotNull(message = "Product must have a code") @NotEmpty(message = "Product must have a code") String code,
        @NotNull(message = "Product must have a seller") String seller, @NotNull BigDecimal price) {
    this.name = name;
    this.imgs = imgs;
    this.code = code;
    this.seller = seller;
    this.price = price;
    }

    public ObjectId getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public String getImgs() {
        return imgs;
    }

    public String getSeller() {
        return seller;
    }

    public void setPid(ObjectId pid) {
        this.pid = pid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public Product() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
    
    
}
