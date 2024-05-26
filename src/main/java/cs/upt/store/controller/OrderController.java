package cs.upt.store.controller;

import java.rmi.server.ObjID;
import java.util.List;

import javax.naming.NameNotFoundException;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cs.upt.store.DTO.OrderDTO;
import cs.upt.store.DTO.ProductSoldDTO;
import cs.upt.store.exceptions.NoEligibleProductsException;
import cs.upt.store.exceptions.UserIsSellerException;
import cs.upt.store.model.Order;
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
            return new ResponseEntity<>(orderDTO, HttpStatus.NOT_FOUND);
        }catch(UserIsSellerException e){
            orderDTO.setMessage("Not a buyer");
            orderDTO.setStatus(false);
            return new ResponseEntity<>(orderDTO, HttpStatus.BAD_REQUEST);
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

    @GetMapping("/buyer/{name}")
    public ResponseEntity<List<Order>> getBuyerOrder(@PathVariable String name){
        try{
            return new ResponseEntity<>(orderService.getBuyerOrder(name), HttpStatus.FOUND);
        }catch(UserIsSellerException e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            System.out.println(e.getMessage());                             
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/seller/{name}")
    public ResponseEntity<List<ProductSoldDTO>> getSellerOrder(@PathVariable String name){
        try{
            return new ResponseEntity<>(orderService.getSellerOrder(name), HttpStatus.FOUND);
        }catch(UserIsSellerException e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            System.out.println(e.getMessage());                             
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
