package cs.upt.store.controller;



import javax.naming.NameNotFoundException;
import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;

import cs.upt.store.DTO.HashedUserDTO;
import cs.upt.store.DTO.HistoryDTO;
import cs.upt.store.DTO.ProductBoughtDTO;
import cs.upt.store.DTO.StatsDTO;
import cs.upt.store.exceptions.CardExistsException;
import cs.upt.store.exceptions.UserIsNotASellerException;
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
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<HashedUserDTO> addUser(@Valid @RequestBody User newUser){
        try{
            userService.insertUser(newUser);
            return new ResponseEntity<>(new HashedUserDTO("New user registered", newUser.getName(), true, newUser.getType()), HttpStatus.CREATED);
        }catch(DataIntegrityViolationException e){
            return new ResponseEntity<>(new HashedUserDTO("User already exists", newUser.getName(), false, -1), HttpStatus.CONFLICT);
        }catch(Exception e){
            System.err.println(e.getMessage());
            return new ResponseEntity<>(new HashedUserDTO("Server side error", newUser.getName(), false, -1), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{name}")
    @CrossOrigin(origins = "http://localhost:3000")
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
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ProductBoughtDTO> addToHistory(@PathVariable String name, @RequestBody ProductBoughtDTO productBoughtDTO){
        try{
            return new ResponseEntity<>(userService.addToHistory(name, productBoughtDTO), HttpStatus.CREATED);
        }catch(NameNotFoundException e){
            productBoughtDTO.setMessage(e.getMessage());
            productBoughtDTO.setStatus(false);
            return new ResponseEntity<>(productBoughtDTO, HttpStatus.NOT_FOUND);
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
    @GetMapping("getHistory/{name}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<HistoryDTO> getHistory(@PathVariable String name){
        try{
            return new ResponseEntity<>(new HistoryDTO(userService.getHistory(name), "Successful",true), HttpStatus.OK);
        }catch(NameNotFoundException e){
            return new ResponseEntity<>(new HistoryDTO(null, e.getMessage(),false), HttpStatus.NOT_FOUND);
        }catch(UserIsSellerException e){
            return new ResponseEntity<>(new HistoryDTO(null, e.getMessage(),false), HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            System.err.println(e.getMessage());
            return new ResponseEntity<>(new HistoryDTO(null, "Server-side error",false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{name}/getProductStats")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<StatsDTO> getProductStats(@PathVariable String name, @RequestParam(required = true) String code){
        try{
            return new ResponseEntity<>(userService.getProductStats(name, code), HttpStatus.OK);
        }catch(NameNotFoundException e){
            return new ResponseEntity<>(new StatsDTO(0,0, e.getMessage(),false), HttpStatus.NOT_FOUND);
        }catch(UserIsNotASellerException e){
            return new ResponseEntity<>(new StatsDTO(0,0, e.getMessage(),false), HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            System.err.println(e.getMessage());
            return new ResponseEntity<>(new StatsDTO(0,0, e.getMessage(),false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{name}/getStats")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<StatsDTO> getStats(@PathVariable String name){
        try{
            return new ResponseEntity<>(userService.getStats(name), HttpStatus.OK);
        }catch(NameNotFoundException e){
            return new ResponseEntity<>(new StatsDTO(0,0, e.getMessage(),false), HttpStatus.NOT_FOUND);
        }catch(UserIsNotASellerException e){
            return new ResponseEntity<>(new StatsDTO(0,0, e.getMessage(),false), HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            System.err.println(e.getMessage());
            return new ResponseEntity<>(new StatsDTO(0,0, e.getMessage(),false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<HashedUserDTO> deleteUser(@Valid @RequestBody User newUser){
        try{
            return new ResponseEntity<>(userService.deleteUser(newUser), HttpStatus.OK);
        }catch(LoginException e){
            return new ResponseEntity<>(new HashedUserDTO(e.getMessage(), newUser.getName(), false, -1), HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            System.err.println(e.getMessage());
            return new ResponseEntity<>(new HashedUserDTO("Server side error", newUser.getName(), false, -1), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
