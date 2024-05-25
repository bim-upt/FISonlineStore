package cs.upt.store.model;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "cards")
public class HashedCard {
    @Id
    byte[] number;
    byte[] expDate;
    byte[] cvv;
    BigDecimal amount;
    public HashedCard(Card card) throws NoSuchAlgorithmException{
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            this.number = digest.digest(
                card.getNumber().getBytes(StandardCharsets.UTF_8));
            this.expDate = digest.digest(
                card.getExpDate().getBytes(StandardCharsets.UTF_8));
            this.cvv = digest.digest(
                card.getCvv().getBytes(StandardCharsets.UTF_8));
            this.amount = card.getAmount();
        }catch(Exception e){
            throw e;
        }
    }
    
}
