package cs.upt.store;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection = "users")
public class User{
    @Id
	private ObjectId uid;

    private String name;
    
    private int type; //0 - normal, 1 - seller
    
    @DocumentReference
    private List<Card> creditCards;
    
    private String password;
}
