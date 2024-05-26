package cs.upt.store.model;


import java.math.BigDecimal;
import java.time.YearMonth;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.LuhnCheck;
import org.springframework.data.annotation.Id;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
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
    @Future
    @NotNull(message = "expiration date mandatory")
    private YearMonth expDate;

    @Valid
    @NotBlank(message = "expiration month mandatory")
    @Pattern(regexp = "[0-9]+", message = "Must only contain digits")
    @NotEmpty(message = "expiration month mandatory")
    @NotNull(message = "expiration month mandatory")
    @Size(min=3, max = 3, message = "Card verification value must contain 3 digits")
    private String cvv;

    private BigDecimal amount;

    public Card(){}
    public Card(String number, YearMonth expDate, String cvv, BigDecimal amount) {
        this.number = number;
        this.expDate = expDate;
        this.cvv = cvv;
        this.amount = amount;
    }

    public String getNumber() {
        return number;
    }

    public String getExpDate() {
        return expDate.toString();
    }

    public String getCvv() {
        return cvv;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setExpDate(YearMonth expDate) {
        this.expDate = expDate;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
    
    
    
}
