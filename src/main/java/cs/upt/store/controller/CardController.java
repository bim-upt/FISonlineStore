package cs.upt.store.controller;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cs.upt.store.exceptions.InsufficientFundsException;
import cs.upt.store.exceptions.NonExistentCardException;
import cs.upt.store.model.Card;
import cs.upt.store.model.HashedCard;
import cs.upt.store.service.CardService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping
    public ResponseEntity<HashedCard> addCard(@Valid @RequestBody Card newCard){
        try{
            HashedCard cardSaved = cardService.insertCard(newCard);
            return new ResponseEntity<>(cardSaved, HttpStatus.CREATED);
        }catch(DataIntegrityViolationException e){
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }catch(NoSuchAlgorithmException e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch(Exception e){
            System.err.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } 

    @PutMapping("add")
    public ResponseEntity<HashedCard> addToCardAmount(@RequestParam(required = true) BigDecimal amount, @Valid @RequestBody Card updatedCard){
        try{
            HashedCard resultingCard = new HashedCard(updatedCard);
            cardService.updateCardByAmount(resultingCard, amount);
            cardService.saveHashedCard(resultingCard);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }catch(DataIntegrityViolationException e){
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }catch(NoSuchAlgorithmException e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch(InsufficientFundsException e){
            return new ResponseEntity<>(null, HttpStatus.PAYMENT_REQUIRED);
        }catch(NonExistentCardException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }catch(Exception e){
            System.err.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
