package cs.upt.store.controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import cs.upt.store.DTO.HashedCardDTO;
import cs.upt.store.DTO.HashedUserDTO;
import cs.upt.store.model.HashedUser;
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
            return new ResponseEntity<>(new HashedUserDTO("New user registered", newUser.getName(), true), HttpStatus.CREATED);
        }catch(DataIntegrityViolationException e){
            return new ResponseEntity<>(new HashedUserDTO("User already exists", newUser.getName(), false), HttpStatus.CONFLICT);
        }catch(NoSuchAlgorithmException e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch(Exception e){
            System.err.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } 
}
