package cs.upt.store.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.security.NoSuchAlgorithmException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.mongodb.assertions.Assertions;

import cs.upt.store.model.HashedUser;
import cs.upt.store.model.User;
import cs.upt.store.service.UserService;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    private User user = null;

    @Before
    public void setup(){
        try{
            user = new User("testNameNobodyWouldChoose", 0, "test2");
            userService.insertUser(user);
        }catch(NoSuchAlgorithmException e){
            fail("User Insertion failed: " + e.getMessage());
        }
    }


    @After
    public void teardown(){
        //System.err.println("\n\n\n\n\n\n\n\n\\n\n\n\n");
        try{
            userService.deleteUser(new User("testNameNobodyWouldChoose", 0, "test2"));
        }catch(Exception e){
            fail("User Deletion failed: " + e.getMessage());
        }
    }

    @Test
    public void testInsertExistingUser_returnsFalse(){
        try{
            userService.insertUser(user);
            Assertions.assertTrue(false);
        }catch(NoSuchAlgorithmException e){
            fail("User Insertion failed: " + e.getMessage());
        }catch(DataIntegrityViolationException e){
            Assertions.assertTrue(true);
        }
    }
}
