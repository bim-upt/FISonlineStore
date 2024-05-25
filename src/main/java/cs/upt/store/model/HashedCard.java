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
    byte[] expMonth;
    byte[] expYear;
    byte[] cvv;
    BigDecimal amount;
    public HashedCard(Card card) throws NoSuchAlgorithmException{
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            this.number = digest.digest(
                card.getNumber().getBytes(StandardCharsets.UTF_8));
            this.expMonth = digest.digest(
                card.getExpMonth().getBytes(StandardCharsets.UTF_8));
            this.expYear = digest.digest(
                card.getExpYear().getBytes(StandardCharsets.UTF_8));
            this.cvv = digest.digest(
                card.getCvv().getBytes(StandardCharsets.UTF_8));
            this.amount = card.getAmount();
        }catch(Exception e){
            throw e;
        }
    }
    
}
