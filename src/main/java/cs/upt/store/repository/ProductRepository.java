package cs.upt.store.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import cs.upt.store.model.Product;

public interface ProductRepository extends MongoRepository<Product,ObjectId>{
}
