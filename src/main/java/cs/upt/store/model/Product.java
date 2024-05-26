package cs.upt.store.model;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "products")
public class Product{
    @Id
	private ObjectId pid;
    
    @NotEmpty(message = "Product must have a name")
    @NotNull(message = "Product must have a name")
    private String name;

    @URL(message = "Invalid url")
    //@NotNull(message = "Product must have an image")
    //@NotEmpty(message = "Product must have an sdfsdfsdfdsfds")
    private String imgs; //urls

    //@DocumentReference(collection = "users")
    @NotNull(message = "Product must have a seller")
    private String seller;

    public Product(
            @NotEmpty(message = "Product must have a name") @NotNull(message = "Product must have a name") String name,
            @URL @NotNull(message = "Product must have an image") String imgs,
            @NotNull(message = "Product must have a seller") String seller) {
        this.name = name;
        this.imgs = imgs;
        this.seller = seller;
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

    
}
