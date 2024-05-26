package cs.upt.store.service;

import java.util.Optional;

import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cs.upt.store.DTO.OrderDTO;
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

    public OrderDTO addOrder(OrderDTO order) throws NameNotFoundException, UserIsSellerException{
        Order actual = new Order(order, productService);
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
   
}
