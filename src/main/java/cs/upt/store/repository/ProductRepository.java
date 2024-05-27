package cs.upt.store.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import cs.upt.store.model.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product,ObjectId>{
    @Query("{ 'seller' : ?0 }")
    public List<Product> findBySeller(String name);

    @Query("{ 'code' : ?0 }")
    public Product findByCode(String code);

    @Query("{ 'code' : ?0 , 'seller' : ?1 }")
    public Product findByCodeAndSeller(String code, String seller);
}
