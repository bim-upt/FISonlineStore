package cs.upt.store.controller;

import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import cs.upt.store.DTO.OrderDTO;
import cs.upt.store.exceptions.NoEligibleProductsException;
import cs.upt.store.exceptions.UserIsSellerException;
import cs.upt.store.service.OrderService;
import jakarta.validation.Valid;

@RequestMapping("/v1/orders")
@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<OrderDTO> addOrder(@Valid @RequestBody OrderDTO orderDTO){
        try{
            orderService.addOrder(orderDTO);
            return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
        }catch(NameNotFoundException e){
            orderDTO.setMessage("Owner not found");
            orderDTO.setStatus(false);
            return new ResponseEntity<>(orderDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch(UserIsSellerException e){
            orderDTO.setMessage("Not a buyer");
            orderDTO.setStatus(false);
            return new ResponseEntity<>(orderDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch(NoEligibleProductsException e){
            orderDTO.setMessage("No product was acceptable");
            orderDTO.setStatus(false);
            return new ResponseEntity<>(orderDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch(Exception e){
            
            orderDTO.setMessage("Server-side error");
            orderDTO.setStatus(false);
            System.out.println(e.getMessage());                             
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
