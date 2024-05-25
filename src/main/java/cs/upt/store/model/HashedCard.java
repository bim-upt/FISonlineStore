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
    byte[] hash;
    BigDecimal amount;
    
    public HashedCard() {
    }
    public HashedCard(Card card) throws NoSuchAlgorithmException{
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String stringToEncode = card.getNumber()+card.getExpDate()+card.getCvv();
            this.hash = digest.digest(stringToEncode.getBytes(StandardCharsets.UTF_8));
            this.amount = card.getAmount();
        }catch(Exception e){
            throw e;
        }
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public byte[] getHash() {
        return hash;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public void setHash(byte[] hash) {
        this.hash = hash;
    }
    
    
}
