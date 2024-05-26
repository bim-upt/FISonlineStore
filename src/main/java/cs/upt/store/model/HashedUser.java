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
    
    @DocumentReference
    private List<Card> creditCards;

    public HashedUser(User user) throws NoSuchAlgorithmException{
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String stringToEncode = user.getPassword();
            this.password = digest.digest(stringToEncode.getBytes(StandardCharsets.UTF_8));
            this.type = user.getType();
            this.creditCards = user.getCreditCards();
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

    public List<Card> getCreditCards() {
        return creditCards;
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

    public void setCreditCards(List<Card> creditCards) {
        this.creditCards = creditCards;
    }    
    
}
