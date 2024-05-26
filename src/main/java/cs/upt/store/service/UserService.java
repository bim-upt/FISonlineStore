package cs.upt.store.service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cs.upt.store.DTO.HashedUserDTO;
import cs.upt.store.exceptions.CardExistsException;
import cs.upt.store.model.HashedCard;
import cs.upt.store.model.HashedUser;
import cs.upt.store.model.User;
import cs.upt.store.repository.HashedCardRepository;
import cs.upt.store.repository.HashedUserRepository;

@Service
public class UserService {
    @Autowired
    private HashedUserRepository hashedUserRepository;

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
}
