package cs.upt.store.service;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cs.upt.store.exceptions.InsufficientFundsException;
import cs.upt.store.exceptions.NonExistentCardException;
import cs.upt.store.model.Card;
import cs.upt.store.model.HashedCard;
import cs.upt.store.repository.HashedCardRepository;
import jakarta.validation.constraints.Null;



@Service
public class CardService {
    @Autowired
    private HashedCardRepository hashedCardRepository;
    
    public HashedCard insertCard(Card card) throws NoSuchAlgorithmException{
        try{
            return hashedCardRepository.insert(new HashedCard(card));
        }catch(Exception e){
            throw e;
        }
    }

    public HashedCard saveHashedCard(HashedCard card) throws NoSuchAlgorithmException{
        try{
            return hashedCardRepository.save(card);
        }catch(Exception e){
            throw e;
        }
    }

    public void updateCardByAmount(HashedCard card, BigDecimal amount) throws InsufficientFundsException, NonExistentCardException{
        HashedCard existingCard = hashedCardRepository.findByHash(card.getHash());
        if(existingCard == null){
            throw new NonExistentCardException();
        }
    
        //System.out.print(existingCard.getHash().toString() + '\n');
        //System.out.print(existingCard.getAmount().toString() + '\n');
        if(amount.compareTo(new BigDecimal(0)) == -1){
            if(existingCard.getAmount() == null){
                throw new InsufficientFundsException("No funds registered on card");
            }
            if(amount.abs().compareTo(existingCard.getAmount()) == 1){
                throw new InsufficientFundsException();
            }
        }
        if(existingCard.getAmount() == null){
            card.setAmount(amount);
            return;
        }
        card.setAmount(existingCard.getAmount().add(amount));
        return;
    }
}
