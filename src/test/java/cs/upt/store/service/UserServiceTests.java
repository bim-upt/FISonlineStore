package cs.upt.store.service;

import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import javax.naming.NameNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.mongodb.assertions.Assertions;

import cs.upt.store.DTO.ProductBoughtDTO;
import cs.upt.store.model.Card;
import cs.upt.store.model.HashedCard;
import cs.upt.store.model.HashedUser;
import cs.upt.store.model.Product;
import cs.upt.store.model.User;
import cs.upt.store.repository.HashedCardRepository;
import cs.upt.store.repository.HashedUserRepository;
import cs.upt.store.service.UserService;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    private User user = new User("testNameNobodyWouldChoose", 0, "test2test2");
    private User seller = new User("testNameNobodyWouldChooseSeller", 1, "test2test2");
    private Product product = new Product("name", "https://imgur.com/F9WQoYe","code",seller.getName(), new BigDecimal(200));
    public User getUser() {
        return user;
    }


    public User getSeller() {
        return seller;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public void setSeller(User seller) {
        this.seller = seller;
    }


    @Before
    public void setup(){
        try{
            User user2 = new User("testNameNobodyWouldChoose", 0, "test2test2");
            User seller2 = new User("testNameNobodyWouldChooseSeller", 1, "test2test2");
            Product product2 = new Product("name", "https://imgur.com/F9WQoYe","code",seller.getName(), new BigDecimal(200));
            userService.insertUser(user2);
            userService.insertUser(seller2);
            productService.insertProduct(product2);
        }catch(Exception e){
            fail("Setup  failed: " + e.getMessage());
        }
        
        
    }


    @After
    public void teardown(){
        try{
            User user2 = new User("testNameNobodyWouldChoose", 0, "test2test2");
            User seller2 = new User("testNameNobodyWouldChooseSeller", 1, "test2test2");
            Product product2 = new Product("name", "https://imgur.com/F9WQoYe","code",seller.getName(), new BigDecimal(200));
           userService.deleteUser(user2);
           userService.deleteUser(seller2);
           productService.deleteProduct(product2.getCode(),product2.getSeller());
        }catch(Exception e){
            fail("Teardown failed: " + e.getMessage());
        }
    }

    @Test
    public void testInsertExistingUser_returnsFalse(){
        try{
            userService.insertUser(user);
            Assertions.assertFalse(true);
        }catch(NoSuchAlgorithmException e){
            fail("User Insertion failed: " + e.getMessage());
        }catch(DataIntegrityViolationException e){
            Assertions.assertTrue(true);
        }
    }



    @Test 
    public void getInexistingUser_returnFalse(){
        try{
            User nouser = new User("testNameNobodyWouldChoose2ElectriBoongaloo", 0, "test2test2");
            userService.getUser(nouser.getName());
            Assertions.assertTrue(false);
        }catch(NameNotFoundException e){
            Assertions.assertTrue(true);
            System.err.println(e.getMessage());
        }catch(Exception e){
            fail("Server side error: " + e.getMessage());
        }
    }

    @Test 
    public void addInexistingProductToHistory_returnFalse(){
        try{
            userService.addToHistory(user.getName(), new ProductBoughtDTO("hopefullyCodeDoesNotExist", "NonExistendSellerPleaseAndThanks"));
            Assertions.assertTrue(false);
        }catch(NameNotFoundException e){
            Assertions.assertTrue(true);
        }catch(Exception e){
            fail("Server side error: " + e.getMessage());
        }
    }

 

   
}
