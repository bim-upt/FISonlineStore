package cs.upt.store.service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cs.upt.store.DTO.HashedUserDTO;
import cs.upt.store.model.HashedUser;
import cs.upt.store.model.User;
import cs.upt.store.repository.HashedUserRepository;

@Service
public class UserService {
    @Autowired
    private HashedUserRepository hashedUserRepository;

    public HashedUser insertUser(User user) throws NoSuchAlgorithmException{
        try{
            return hashedUserRepository.insert(new HashedUser(user));
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
