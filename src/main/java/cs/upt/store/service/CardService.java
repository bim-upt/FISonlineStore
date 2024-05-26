package cs.upt.store.service;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cs.upt.store.exceptions.InsufficientFundsException;
import cs.upt.store.exceptions.NonExistentCardException;
import cs.upt.store.model.Card;
import cs.upt.store.model.HashedCard;
import cs.upt.store.model.HashedUser;
import cs.upt.store.repository.HashedCardRepository;
import cs.upt.store.repository.HashedUserRepository;



@Service
public class CardService {
    @Autowired
    private HashedCardRepository hashedCardRepository;
    
    @Autowired
    private HashedUserRepository hashedUserRepository;

    public HashedCard insertCard(Card card) throws NoSuchAlgorithmException, NameNotFoundException{
        try{
            Optional<HashedUser> user = hashedUserRepository.findById(card.getOwner());
            if(user.isEmpty()){
                throw new NameNotFoundException("No such user");
            }
            HashedCard result = new HashedCard(card);
            user.get().setCreditCard(result.getHash());
            hashedUserRepository.save(user.get());
            return hashedCardRepository.insert(result);
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
