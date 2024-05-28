package cs.upt.store.controller;

import java.math.BigDecimal;

import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import cs.upt.store.DTO.HashedCardDTO;
import cs.upt.store.exceptions.CardExistsException;
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
        }catch(CardExistsException e){
            return new ResponseEntity<>(new HashedCardDTO("User already has a card", false), HttpStatus.CONFLICT);
        }catch(NameNotFoundException e){
            return new ResponseEntity<>(new HashedCardDTO("User not found", false), HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            return new ResponseEntity<>(new HashedCardDTO("Server side error", false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } 

    @PutMapping("addAmount")
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

    @DeleteMapping("delete")
    public ResponseEntity<HashedCardDTO> deleteCard(@Valid @RequestBody Card newCard){
        try{
            return new ResponseEntity<>(cardService.deleteCard(newCard), HttpStatus.OK);
        }catch(NotFoundException e){
            return new ResponseEntity<>(new HashedCardDTO("No such card has been found", false), HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            return new ResponseEntity<>(new HashedCardDTO("Server side error", false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } 

}
