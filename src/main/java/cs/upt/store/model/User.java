package cs.upt.store.model;


import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User{
    @Id
    @NotEmpty(message = "Username is mandatory")
    @NotNull(message = "Username is mandatory")
    @NotBlank(message = "Username is mandatory")
    private String name;
    
    @NotNull(message = "User type is mandatory")
    @Range(min = 0, max = 1, message = "Unkown user type")
    private int type; //0 - normal, 1 - seller
    
    @DocumentReference
    private List<Card> creditCards;
    
    @Length(min = 6, message = "Password too short!")
    @NotEmpty(message = "Password is mandatory")
    @NotNull(message = "Password is mandatory")
    @NotBlank(message = "Password is mandatory")
    private String password;

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public List<Card> getCreditCards() {
        return creditCards;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setCreditCards(List<Card> creditCards) {
        this.creditCards = creditCards;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
