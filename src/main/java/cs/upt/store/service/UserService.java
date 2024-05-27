package cs.upt.store.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.naming.NameNotFoundException;
import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cs.upt.store.DTO.HashedUserDTO;
import cs.upt.store.DTO.ProductBoughtDTO;
import cs.upt.store.DTO.StatsDTO;
import cs.upt.store.exceptions.CardExistsException;
import cs.upt.store.exceptions.UserIsNotASellerException;
import cs.upt.store.exceptions.UserIsSellerException;
import cs.upt.store.model.HashedUser;
import cs.upt.store.model.Order;
import cs.upt.store.model.Product;
import cs.upt.store.model.User;
import cs.upt.store.repository.HashedCardRepository;
import cs.upt.store.repository.HashedUserRepository;
import cs.upt.store.repository.OrderRepository;
import cs.upt.store.repository.ProductRepository;

@Service
public class UserService {
    @Autowired
    private HashedUserRepository hashedUserRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private HashedCardRepository hashedCardRepository;


    public HashedUser insertUser(User user) throws NoSuchAlgorithmException{
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

    public List<ProductBoughtDTO> getHistory(String name) throws NameNotFoundException, UserIsSellerException{
        Optional<HashedUser> user = hashedUserRepository.findById(name);
        if(user.isEmpty()){
            throw new NameNotFoundException("User has not been found");
        }
        if(user.get().getType() == 1){
            throw new UserIsSellerException("Sellers have no history");
        }
        return user.get().getHistory();
    }

    public StatsDTO getProductStats(String name, String code) throws NameNotFoundException, UserIsNotASellerException{
        Optional<HashedUser> user = hashedUserRepository.findById(name);
        if(user.isEmpty()){
            throw new NameNotFoundException("User has not been found");
        }
        if(user.get().getType() == 0){
            throw new UserIsNotASellerException("Buyers have no products");
        }
        Product sold = productRepository.findByCodeAndSeller(code, name);
        if(sold == null){
            throw new NameNotFoundException("Seller has no product with that code");
        }
        List<Order> orders = orderRepository.findAll();
        int count = 0;
        int buyers = 0;
        for(int i = 0; i < orders.size(); i++){
            int added = 0;
            for(int j = 0; j < orders.get(i).getProducts().size(); j++){
                if(orders.get(i).getProducts().get(j).getSeller().equals(name) && orders.get(i).getProducts().get(j).getCode().equals(code)){
                    if(added == 0){
                        buyers++;
                        added = 1;
                    }
                    count++;
                }
            }
        }
        return new StatsDTO(buyers, count, "Completed", true);
    }

    public StatsDTO getStats(String name) throws NameNotFoundException, UserIsNotASellerException{
        Optional<HashedUser> user = hashedUserRepository.findById(name);
        if(user.isEmpty()){
            throw new NameNotFoundException("User has not been found");
        }
        if(user.get().getType() == 0){
            throw new UserIsNotASellerException("Buyers have no products");
        }
        List<Order> orders = orderRepository.findAll();
        int count = 0;
        int buyers = 0;
        for(int i = 0; i < orders.size(); i++){
            int added = 0;
            for(int j = 0; j < orders.get(i).getProducts().size(); j++){
                if(orders.get(i).getProducts().get(j).getSeller().equals(name)){
                    if(added == 0){
                        buyers++;
                        added = 1;
                    }
                    count++;
                }
            }
        }
        return new StatsDTO(buyers, count, "Completed", true);
    }

    public HashedUserDTO deleteUser(User user) throws Exception{
        HashedUser hashedUser = null;
        try{
            hashedUser = new HashedUser(user);
        }catch(Exception e){
            throw e;
        }
        Optional<HashedUser> savedUser = hashedUserRepository.findById(user.getName());
        if(savedUser.isEmpty()){
            return new HashedUserDTO("User wasn't in the db", user.getName(), true, user.getType());
        }
        if(!Arrays.equals(savedUser.get().getPassword(), hashedUser.getPassword()) || !savedUser.get().getName().equals(hashedUser.getName()) || savedUser.get().getType() != hashedUser.getType()){
            throw new LoginException("Credentials do not match, either password, type or username");
        }
        hashedUserRepository.delete(hashedUser);
        if(hashedUser.getCreditCard() != null){
            hashedCardRepository.delete(hashedCardRepository.findByHash(hashedUser.getCreditCard()));
        }
        return new HashedUserDTO("User no longer exists", user.getName(), true, user.getType());
    }
}
