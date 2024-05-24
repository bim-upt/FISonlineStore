package cs.upt.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cs.upt.store.model.Card;
import cs.upt.store.repository.CardRepository;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;
    public Card saveCard(Card card){
        return cardRepository.insert(card);
    }

}
