package cs.upt.store.DTO;

import org.bson.types.ObjectId;

import cs.upt.store.model.Product;


public class ProductDTO {
	private ObjectId pid;
    
    private String name;

    private String imgs; //urls

    private String seller;

    private Boolean status;
    private String message;
    public ProductDTO(){}
    public ProductDTO(Product product, boolean status, String message){
        this.imgs = product.getImgs();
        this.name = product.getName();
        this.pid = product.getPid();
        this.seller = product.getSeller();
        this.status = status;
        this.message = message;
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


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    
}
