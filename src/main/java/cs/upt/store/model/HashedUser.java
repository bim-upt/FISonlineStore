package cs.upt.store.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cs.upt.store.DTO.ProductBoughtDTO;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "users")
public class HashedUser {
    @Id
    @NotNull
    private String name;

    @NotNull
    private byte[] password;
    
    @NotNull(message = "User type is mandatory")
    @Range(min = 0, max = 1, message = "Unkown user type")
    private int type; //0 - normal, 1 - seller
    
    @NotNull(message = "With what money you gonna buy stuff")
    private byte[] creditCard;

    private List<ProductBoughtDTO> history;

    public HashedUser(User user) throws NoSuchAlgorithmException{
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String stringToEncode = user.getPassword();
            this.password = digest.digest(stringToEncode.getBytes(StandardCharsets.UTF_8));
            this.type = user.getType();
            //if(user.getCreditCard()!= null){this.creditCard = (new HashedCard(user.getCreditCard())).getHash();}
            this.name = user.getName();
        }catch(Exception e){
            throw e;
        }
    }

    public HashedUser(){}

    public String getName() {
        return name;
    }

    public byte[] getPassword() {
        return password;
    }

    public int getType() {
        return type;
    }

    public byte[] getCreditCard() {
        return creditCard;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setCreditCard(byte[] creditCard) {
        this.creditCard = creditCard;
    }

    public List<ProductBoughtDTO> getHistory() {
        return history;
    }

    public void setHistory(List<ProductBoughtDTO> history) {
        this.history = history;
    }    
    
}
