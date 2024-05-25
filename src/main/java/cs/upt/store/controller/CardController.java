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
import org.springframework.web.bind.annotation.RestController;

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
            HashedCard cardSaved = cardService.saveCard(newCard);
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

    // @PutMapping("/{amount}")
    // public ResponseEntity<HashedCard> addToCardAmount(@PathVariable BigDecimal amount, @Valid @RequestBody Card updatedCard){
    //     try{
    //         cardService.updateCardByAmount(updatedCard, amount);
    //         HashedCard resultingCard = cardService.saveCard(updatedCard);
    //         return new ResponseEntity<>(resultingCard, HttpStatus.OK);
    //     }catch(DataIntegrityViolationException e){
    //         return new ResponseEntity<>(null, HttpStatus.CONFLICT);
    //     }catch(NoSuchAlgorithmException e){
    //         return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }catch(Exception e){
    //         System.err.println(e.getMessage());
    //         return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }
}
