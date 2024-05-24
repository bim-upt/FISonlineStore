package cs.upt.store;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import java.util.List;

@Document(collection = "products")
public class Product{
    @Id
	private ObjectId pid;
    
    private String name;

    private List<String> imgs; //urls

    @DocumentReference
    private User seller;
}
