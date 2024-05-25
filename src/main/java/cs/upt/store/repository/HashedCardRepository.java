package cs.upt.store.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


import cs.upt.store.model.HashedCard;

@Repository
public interface HashedCardRepository extends MongoRepository<HashedCard, ObjectId>{
    @Query("{ 'hash' : ?0 }")
    HashedCard findByHash(byte[] hash);
}
