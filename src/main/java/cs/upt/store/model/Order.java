package cs.upt.store.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "orders")
public class Order{
    @Id
	private ObjectId oid;
    
    @DocumentReference
    private List<Product> products;

    @DocumentReference
    private User buyer;
}

