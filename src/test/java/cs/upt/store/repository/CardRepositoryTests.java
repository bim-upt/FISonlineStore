package cs.upt.store.repository;

import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.mongodb.assertions.Assertions;

import cs.upt.store.model.Card;
import cs.upt.store.model.HashedCard;
import cs.upt.store.model.HashedUser;
import cs.upt.store.model.User;
import cs.upt.store.repository.HashedCardRepository;
import cs.upt.store.repository.HashedUserRepository;
import cs.upt.store.service.UserService;


@SpringBootTest
public class CardRepositoryTests {
    @Autowired
    private HashedCardRepository hashedCardRepository;

    @Autowired
    private HashedUserRepository hashedUserRepository;

    @Autowired
    private UserService userService;

    private HashedUser hashedUser;

    @Before
    public void setup(){
        try{
            hashedUser = new HashedUser(new User("testNameNobodyWouldChoose", 0, "test2test2"));
            hashedUserRepository.insert(hashedUser);
        }catch(NoSuchAlgorithmException e){
            fail("User Insertion failed: " + e.getMessage());
        }
    }

    @After
    public void teardown(){
        try{
            userService.deleteUser(new User("testNameNobodyWouldChoose", 0, "test2test2"));
        }catch(Exception e){
            fail("User Deletion failed: " + e.getMessage());
        }
    }

    @Test
    public void HashedCardRepository_saveCard_returnsTrue(){

        //setup
        
        Card card = new Card("12345678929", YearMonth.parse("2029-05", DateTimeFormatter.ofPattern("yyyy-MM")), "123", new BigDecimal(1000), "testNameNobodyWouldChoose");
        HashedCard hashedCard = null;
        try{
            hashedCard = new HashedCard(card);
        }catch(NoSuchAlgorithmException e){
            fail("Server error: " + e.getMessage());
        }

        //act
        HashedCard savedCard = hashedCardRepository.save(hashedCard);

        //assert
        Assertions.assertTrue(savedCard != null);

    }

    @Test
    public void HashedCardRepository_getCardbyHash_returnsTrue(){

        //setup
        
        Card card = new Card("12345678929", YearMonth.parse("2029-05", DateTimeFormatter.ofPattern("yyyy-MM")), "123", new BigDecimal(1000), "test");
        HashedCard hashedCard = null;
        try{
            hashedCard = new HashedCard(card);
        }catch(NoSuchAlgorithmException e){
            fail("Server error: " + e.getMessage());
        }

        //act
        HashedCard savedCard = hashedCardRepository.findByHash(hashedCard.getHash());

        //assert
        Assertions.assertTrue(savedCard != null);

    }
}
