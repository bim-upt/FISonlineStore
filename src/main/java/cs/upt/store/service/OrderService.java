package cs.upt.store.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.naming.NameNotFoundException;
import javax.swing.text.html.Option;

import org.apache.coyote.BadRequestException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import cs.upt.store.DTO.OrderDTO;
import cs.upt.store.exceptions.NoEligibleProductsException;
import cs.upt.store.exceptions.UserIsNotASellerException;
import cs.upt.store.exceptions.UserIsSellerException;
import cs.upt.store.model.HashedUser;
import cs.upt.store.model.Order;
import cs.upt.store.model.Product;
import cs.upt.store.repository.HashedUserRepository;
import cs.upt.store.repository.OrderRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private HashedUserRepository hashedUserRepository;

    @Autowired
    private ProductService productService;

    

    public OrderDTO addOrder(OrderDTO order) throws NameNotFoundException, UserIsSellerException, NoEligibleProductsException{
        Order actual = new Order(order, productService);
        if(actual.getProducts().isEmpty()){
            throw new NoEligibleProductsException("No products eligible to be bought");
        }
        orderRepository.insert(actual);
        Optional<HashedUser> buyer = hashedUserRepository.findById(order.getBuyer());
        if(buyer.isEmpty()){
            throw new NameNotFoundException("Buyer not found");
        }
        if(buyer.get().getType() == 1){
            throw new UserIsSellerException("User isn't a buyer");
        }
        for(int i = 0; i < order.getProducts().size(); i++){
            Product current = productService.findByCodeAndSeller(order.getProducts().get(i).getCode(),order.getProducts().get(i).getSeller());
            if(current != null){
                order.getProducts().get(i).setMessage("Product added successfully");
                order.getProducts().get(i).setStatus(true);
            }else{
                order.getProducts().get(i).setMessage("Product not added");
                order.getProducts().get(i).setStatus(false);
            }
        }
        order.setStatus(true);
        order.setOid(actual.getOid());
        return order;
    }

    public List<Order> getBuyerOrder(String name) throws NotFoundException, UserIsSellerException{
        Optional<HashedUser> user = hashedUserRepository.findById(name);
        if(user.isEmpty()){
            throw new NotFoundException();
        }
        if(user.get().getType() == 0){
            return orderRepository.findByBuyer(name);
        }
        throw new UserIsSellerException("User is a seller");
    }
   
}
