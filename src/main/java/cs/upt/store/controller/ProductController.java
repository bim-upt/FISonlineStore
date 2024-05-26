package cs.upt.store.controller;

import java.security.NoSuchAlgorithmException;

import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import cs.upt.store.DTO.HashedUserDTO;
import cs.upt.store.DTO.ProductDTO;
import cs.upt.store.exceptions.UserIsNotASellerException;
import cs.upt.store.model.Product;
import cs.upt.store.model.User;
import cs.upt.store.repository.ProductRepository;
import cs.upt.store.service.ProductService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/v1/products")
public class ProductController {
    @Autowired
    ProductService productService;
    
    @PostMapping
    public ResponseEntity<ProductDTO> addUser(@Valid @RequestBody Product newProduct){
    try{
        ProductDTO response = new ProductDTO(productService.insertProduct(newProduct), true, "Product added");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }catch(NoSuchAlgorithmException e){
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }catch(NameNotFoundException e){
        return new ResponseEntity<>(new ProductDTO(newProduct, true, "No seller found"), HttpStatus.NOT_FOUND);
    }catch(UserIsNotASellerException e){
        return new ResponseEntity<>(new ProductDTO(newProduct, true, "User is not a seller"), HttpStatus.BAD_REQUEST);
    }
    catch(Exception e){
        System.err.println(e.getMessage());
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
} 
