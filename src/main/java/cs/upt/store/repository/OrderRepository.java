package cs.upt.store.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import cs.upt.store.model.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order,ObjectId>{
    @Query("{ 'buyer' : ?0 }")
    public List<Order> findByBuyer(String buyer);
}
