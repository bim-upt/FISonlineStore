package cs.upt.store.service;

import java.security.NoSuchAlgorithmException;

import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cs.upt.store.DTO.HashedUserDTO;
import cs.upt.store.exceptions.UserIsNotASellerException;
import cs.upt.store.model.Product;
import cs.upt.store.repository.ProductRepository;



@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    public Product insertProduct(Product product) throws NoSuchAlgorithmException, NameNotFoundException, UserIsNotASellerException{
        try{
            HashedUserDTO seller = userService.getUser(product.getSeller());
            if(seller.getType() == 0){
                throw new UserIsNotASellerException("Not a seller");
            }
            return productRepository.insert(product);
        }catch(NameNotFoundException e){
            throw e;
        }catch(Exception e){
            throw e;
        }
    }
}
