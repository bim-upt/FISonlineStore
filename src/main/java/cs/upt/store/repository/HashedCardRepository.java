package cs.upt.store.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


import cs.upt.store.model.HashedCard;

@Repository
public interface HashedCardRepository extends MongoRepository<HashedCard, byte[]>{
    @Query("{ 'hash' : ?0 }")
    HashedCard findByHash(byte[] hash);
}
