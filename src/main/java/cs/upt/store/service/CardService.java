package cs.upt.store.service;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import cs.upt.store.DTO.HashedCardDTO;
import cs.upt.store.exceptions.CardExistsException;
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

    public HashedCard insertCard(Card card) throws NoSuchAlgorithmException, NameNotFoundException, CardExistsException{
        try{
            Optional<HashedUser> user = hashedUserRepository.findById(card.getOwner());
            if(user.isEmpty()){
                throw new NameNotFoundException("No such user");
            }
            if(user.get().getCreditCard() != null){
                throw new CardExistsException("User already has a card");
            }
            HashedCard result = new HashedCard(card);
            user.get().setCreditCard(result.getHash());
            hashedCardRepository.insert(result);
            hashedUserRepository.save(user.get());
            return result;
        }catch(Exception e){
            throw e;
        }
    }

    public HashedCardDTO deleteCard(Card card) throws Exception{
        HashedCard hashedCard = null;
        try{
            hashedCard = new HashedCard(card);
        }catch(Exception e){
            throw e;
        }
        Optional<HashedUser> user = hashedUserRepository.findById(hashedCard.getOwner());
        if(hashedCardRepository.findByHash(hashedCard.getHash()) == null){
            throw new NotFoundException();
        }
        if(user.isPresent()){
            user.get().setCreditCard(null);
            hashedUserRepository.save(user.get());
        }
        hashedCardRepository.delete(hashedCard);
        return new HashedCardDTO("Success", true);
    }

    public HashedCard saveHashedCard(HashedCard card) throws NoSuchAlgorithmException{
        try{
            return hashedCardRepository.save(card);
        }catch(Exception e){
            throw e;
        }
    }

    public void updateCardByAmount(HashedCard card, BigDecimal amount) throws InsufficientFundsException, NonExistentCardException{
        if(card == null){
            throw new NonExistentCardException();
        }
        HashedCard existingCard = hashedCardRepository.findByHash(card.getHash());
        if(existingCard == null){
            throw new NonExistentCardException("NO such card");
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
        hashedCardRepository.save(card);
        return;
    }
}
