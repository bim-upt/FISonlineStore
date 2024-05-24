package cs.upt.store;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cards")
public class Card{
    @Id
	private ObjectId cid;
    
    private String number;
    private String expMonth;
    private String expYear;
    private String ccv;

    private float amount;
}
