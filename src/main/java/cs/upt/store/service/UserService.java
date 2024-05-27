package cs.upt.store.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Optional;

import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cs.upt.store.DTO.HashedUserDTO;
import cs.upt.store.DTO.ProductBoughtDTO;
import cs.upt.store.exceptions.CardExistsException;
import cs.upt.store.exceptions.UserIsSellerException;
import cs.upt.store.model.HashedCard;
import cs.upt.store.model.HashedUser;
import cs.upt.store.model.Product;
import cs.upt.store.model.User;
import cs.upt.store.repository.HashedCardRepository;
import cs.upt.store.repository.HashedUserRepository;
import cs.upt.store.repository.ProductRepository;

@Service
public class UserService {
    @Autowired
    private HashedUserRepository hashedUserRepository;

    @Autowired
    private ProductRepository productRepository;

    // @Autowired
    // private HashedCardRepository hashedCardRepository;


    public HashedUser insertUser(User user) throws NoSuchAlgorithmException, CardExistsException{
        try{
            HashedUser result = new HashedUser(user);
            // if(user.getCreditCard() != null){
            //     if(hashedCardRepository.findByHash(result.getCreditCard()) != null){
            //         throw new CardExistsException("Card belongs to another user");
            //     }else{
            //         hashedCardRepository.insert(new HashedCard(user.getCreditCard()));
            //     }
            // }
            return hashedUserRepository.insert(result);
        }catch(Exception e){
            throw e;
        }
    }

    public HashedUserDTO getUser(String username) throws NameNotFoundException{
        Optional<HashedUser> found =  hashedUserRepository.findById(username);
        if(found.isEmpty()){
            throw new NameNotFoundException();
        }
        return new HashedUserDTO("User found", found.get().getName(), true, found.get().getType());
    }

    public ProductBoughtDTO addToHistory(String name, ProductBoughtDTO productBoughtDTO) throws NameNotFoundException, UserIsSellerException{
        Optional<HashedUser> user = hashedUserRepository.findById(name);
        if(user.isEmpty()){
            throw new NameNotFoundException("User has not been found");
        }
        if(user.get().getType() == 1){
            throw new UserIsSellerException("Sellers have no history");
        }
        Product product = productRepository.findByCodeAndSeller(productBoughtDTO.getCode(), productBoughtDTO.getSeller());
        if(product == null){
            throw new NameNotFoundException("Product not found");
        }
        productBoughtDTO.setStatus(true);
        productBoughtDTO.setMessage("Product added");
        if(user.get().getHistory() == null){
            user.get().setHistory(new ArrayList<ProductBoughtDTO>());
        }
        user.get().getHistory().add(productBoughtDTO);
        hashedUserRepository.save(user.get());
        return productBoughtDTO;
    }
}
