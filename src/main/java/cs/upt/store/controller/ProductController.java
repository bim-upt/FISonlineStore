package cs.upt.store.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List; 

import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cs.upt.store.DTO.ProductDTO;
import cs.upt.store.exceptions.UserIsNotASellerException;
import cs.upt.store.model.Product;
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
            return new ResponseEntity<>(new ProductDTO(newProduct, false, "No seller found"), HttpStatus.NOT_FOUND);
        }catch(UserIsNotASellerException e){
            return new ResponseEntity<>(new ProductDTO(newProduct, false, "User is not a seller"), HttpStatus.BAD_REQUEST);
        }catch(DataIntegrityViolationException e){
            return new ResponseEntity<>(new ProductDTO(newProduct, false, "Seller has a product with this code"), HttpStatus.CONFLICT);
        }catch(Exception e){
            System.err.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/modify")
    public ResponseEntity<ProductDTO> modifyProduct(@Valid @RequestBody Product newProduct){
        try{
            productService.modifyProduct(newProduct);
            return new ResponseEntity<>(new ProductDTO(newProduct, true, "Product modified"), HttpStatus.OK);
        }catch(NotFoundException e){
            return new ResponseEntity<>(new ProductDTO(newProduct, false, "Seller doesn't exist, or doesn't have a matching code product"), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>(new ProductDTO(newProduct, false, "Server-side error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ProductDTO> deleteProduct(@RequestParam String code, @RequestParam String seller){
        try{
            return new ResponseEntity<>(new ProductDTO(productService.deleteProduct(code, seller), true, "Product deleted"), HttpStatus.OK);
        }catch(NotFoundException e){
            return new ResponseEntity<>(new ProductDTO("No product to delte has been found", false), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>(new ProductDTO("Server-side error", false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts(){
        try{
            List<ProductDTO> products = productService.getProducts();
            return new ResponseEntity<>(products, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }
} 
