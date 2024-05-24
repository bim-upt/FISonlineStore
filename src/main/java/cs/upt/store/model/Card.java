package cs.upt.store.model;


import java.math.BigDecimal;
import java.time.YearMonth;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.LuhnCheck;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "cards")
public class Card{
    
    @Valid
    @Pattern(regexp = "[0-9]+", message = "Must only contain digits")
    @Id
    @NotBlank
    @NotEmpty
    @Size(min=8,max=19,message = "Card PAN too small or too big") //numbers yoinked from wiki
    @CreditCardNumber(ignoreNonDigitCharacters = true)
    @LuhnCheck
    private String number;

    @Valid
    @NotBlank(message = "expiration month mandatory")
    @Pattern(regexp = "[0-9]+", message = "Must only contain digits")
    @NotEmpty(message = "expiration month mandatory")
    @NotNull(message = "expiration month mandatory")
    @Size(min=2,max=2,message = "Use a two digit format for single digit months")
    private String expMonth;

    @Valid
    @NotBlank(message = "expiration year mandatory")
    @NotEmpty(message = "expiration year mandatory")
    @NotNull(message = "expiration year mandatory")
    @Pattern(regexp = "[0-9]+", message = "Must only contain digits")
    @Size(min=2,max=2,message = "Only the last two digits are needed")
    private String expYear;

    @Valid
    @NotBlank(message = "expiration month mandatory")
    @Pattern(regexp = "[0-9]+", message = "Must only contain digits")
    @NotEmpty(message = "expiration month mandatory")
    @NotNull(message = "expiration month mandatory")
    @Size(min=3, max = 3, message = "Card verification value must contain 3 digits")
    private String cvv;

    @NotNull
    private BigDecimal amount;

    public Card(String number, String expMonth, String expYear, String cvv, BigDecimal amount) {
        this.number = number;
        this.expMonth = expMonth;
        this.expYear = expYear;
        this.cvv = cvv;
        this.amount = amount;
    }
}
