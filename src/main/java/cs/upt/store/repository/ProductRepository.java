package cs.upt.store.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import cs.upt.store.model.Product;

public interface ProductRepository extends MongoRepository<Product,ObjectId>{
    @Query("{ 'seller' : ?0 }")
    List<Product> findBySeller(String name);

    @Query("{ 'code' : ?0 }")
    Product findByCode(String code);
}
