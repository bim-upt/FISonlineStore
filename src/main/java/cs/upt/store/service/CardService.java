package cs.upt.store.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cs.upt.store.model.Card;
import cs.upt.store.model.HashedCard;
import cs.upt.store.repository.HashedCardRepository;

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

}
