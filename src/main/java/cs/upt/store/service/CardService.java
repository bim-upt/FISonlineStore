package cs.upt.store.service;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cs.upt.store.model.Card;
import cs.upt.store.model.HashedCard;
import cs.upt.store.repository.HashedCardRepository;
import jakarta.validation.constraints.Null;

class InsufficientFunds extends Exception{
    public InsufficientFunds(String msg){
        super(msg);
    }
    public InsufficientFunds(){
        super("Insufficient funds on card");
    }
}

@Service
public class CardService {
    @Autowired
    private HashedCardRepository hashedCardRepository;
    
    public HashedCard saveCard(Card card) throws NoSuchAlgorithmException{
        try{
            return hashedCardRepository.insert(new HashedCard(card));
        }catch(Exception e){
            throw e;
        }
    }

    // public void updateCardByAmount(Card card, BigDecimal amount) throws InsufficientFunds{
    //     if(amount.compareTo(new BigDecimal(0)) == -1){
    //         if(card.getAmount() == null){
    //             throw new InsufficientFunds("No funds registered on card");
    //         }
    //         if(amount.abs().compareTo(card.getAmount()) == 1){
    //             throw new InsufficientFunds();
    //         }
    //     }
    //     if(card.getAmount() == null){
    //         card.setAmount(amount);
    //         return;
    //     }
    //     card.setAmount(card.getAmount().add(amount));
    //     return;
    // }
}
