package cs.upt.store.controller;


import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import cs.upt.store.DTO.HashedUserDTO;
import cs.upt.store.DTO.ProductBoughtDTO;
import cs.upt.store.exceptions.CardExistsException;
import cs.upt.store.exceptions.UserIsSellerException;
import cs.upt.store.model.User;
import cs.upt.store.service.UserService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<HashedUserDTO> addUser(@Valid @RequestBody User newUser){
        try{
            userService.insertUser(newUser);
            return new ResponseEntity<>(new HashedUserDTO("New user registered", newUser.getName(), true, newUser.getType()), HttpStatus.CREATED);
        }catch(DataIntegrityViolationException e){
            return new ResponseEntity<>(new HashedUserDTO("User already exists", newUser.getName(), false, -1), HttpStatus.CONFLICT);
        }catch(CardExistsException e){
            return new ResponseEntity<>(new HashedUserDTO("Card alreay belongs to somebody else", newUser.getName(), false, -1), HttpStatus.CONFLICT);
        }catch(Exception e){
            System.err.println(e.getMessage());
            return new ResponseEntity<>(new HashedUserDTO("Server side error", newUser.getName(), false, -1), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<HashedUserDTO> getUser(@PathVariable String name){
        try{
            HashedUserDTO response = userService.getUser(name);
            return new ResponseEntity<>(response, HttpStatus.FOUND);
        }catch(NameNotFoundException e){
            return new ResponseEntity<>(new HashedUserDTO("User not found", name, false, -1), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            System.err.println(e.getMessage());
            return new ResponseEntity<>(new HashedUserDTO("Server side error", name, false, -1), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("addHistory/{name}")
    public ResponseEntity<ProductBoughtDTO> addToHistory(@PathVariable String name, @RequestBody ProductBoughtDTO productBoughtDTO){
        try{
            return new ResponseEntity<>(userService.addToHistory(name, productBoughtDTO), HttpStatus.CREATED);
        }catch(NameNotFoundException e){
            productBoughtDTO.setMessage(e.getMessage());
            productBoughtDTO.setStatus(false);
            return new ResponseEntity<>(productBoughtDTO, HttpStatus.CREATED);
        }catch(UserIsSellerException e){
            productBoughtDTO.setMessage("Sellers have no history");
            productBoughtDTO.setStatus(false);
            return new ResponseEntity<>(productBoughtDTO, HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            productBoughtDTO.setMessage("Server-side error");
            productBoughtDTO.setStatus(false);
            System.err.println(e.getMessage());
            return new ResponseEntity<>(productBoughtDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
