package cs.upt.store.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cs.upt.store.model.HashedUser;

@Repository
public interface HashedUserRepository extends MongoRepository<HashedUser, String>{
}
