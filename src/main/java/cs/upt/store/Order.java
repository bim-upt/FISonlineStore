package cs.upt.store;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import java.util.List;

@Document(collection = "orders")
public class Order{
    @Id
	private ObjectId oid;
    
    @DocumentReference
    private List<Product> products;

    @DocumentReference
    private User buyer;
}

