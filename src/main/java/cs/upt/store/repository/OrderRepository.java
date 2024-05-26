package cs.upt.store.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cs.upt.store.model.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order,ObjectId>{
    
}
