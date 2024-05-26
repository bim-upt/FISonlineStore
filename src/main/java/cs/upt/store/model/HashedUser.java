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
}
