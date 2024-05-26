package cs.upt.store.controller;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cs.upt.store.DTO.HashedCardDTO;
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
    public ResponseEntity<HashedCardDTO> addCard(@Valid @RequestBody Card newCard){
        try{
            cardService.insertCard(newCard);
            return new ResponseEntity<>(new HashedCardDTO("Card added", true), HttpStatus.CREATED);
        }catch(DataIntegrityViolationException e){
            return new ResponseEntity<>(new HashedCardDTO("Card already exists", false), HttpStatus.CONFLICT);
        }catch(Exception e){
            System.err.println(e.getMessage());
            return new ResponseEntity<>(new HashedCardDTO("Server side error", false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } 

    @PutMapping("add")
    public ResponseEntity<HashedCardDTO> addToCardAmount(@RequestParam(required = true) BigDecimal amount, @Valid @RequestBody Card updatedCard){
        try{
            HashedCard resultingCard = new HashedCard(updatedCard);
            cardService.updateCardByAmount(resultingCard, amount);
            cardService.saveHashedCard(resultingCard);
            return new ResponseEntity<>(new HashedCardDTO("Transaction successful", true), HttpStatus.OK);
        }catch(InsufficientFundsException e){
            return new ResponseEntity<>(new HashedCardDTO("Funds too low for transaction", false), HttpStatus.PAYMENT_REQUIRED);
        }catch(NonExistentCardException e){
            return new ResponseEntity<>(new HashedCardDTO("Card not found", false), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            System.err.println(e.getMessage());
            return new ResponseEntity<>(new HashedCardDTO("Server-side error", false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
