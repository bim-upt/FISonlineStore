package cs.upt.store.DTO;


import cs.upt.store.model.Product;


public class ProductDTO {
    
    private String name;

    private String imgs; //urls

    private String code;
    private String seller;
    
    private Boolean status;
    private String message;
    public ProductDTO(){}
    public ProductDTO(Product product, boolean status, String message){
        this.imgs = product.getImgs();
        this.name = product.getName();
        this.seller = product.getSeller();
        this.status = status;
        this.message = message;
        this.code = product.getCode();
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

    public void setCode(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }
}
