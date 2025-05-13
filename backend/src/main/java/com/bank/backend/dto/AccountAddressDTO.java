package com.bank.backend.dto;

import com.bank.backend.constant.AccountType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AccountAddressDTO(
        @NotNull(message = "Account holder name should not be Null")
        String accountHolderName,
        @NotNull(message = "About customer should not be Null")
        String aboutCustomer,
        @NotNull(message = "Contact number should not be Null")
        @Pattern(regexp = "^\\d{10}$",message = "enter valid phone number.")
        String contactNumber,
        @NotNull(message = "Account email should not be Null")
        @Email(message = "Enter valid email")
        String accountEmail,
        @NotNull(message = "Account password should not be Null")
        @Size(min = 8,max = 16,message = "Password should be between 8 to 16 characters.")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-={}|[\\]:;\"'<>,.?~])(?=.*[A-Z]).*$",
                message = "Password must contain at least one number, one symbol, and one capital letter")
        String accountPassword,
        @NotNull(message = "Account type should not be Null")
        AccountType accountType,
        @NotNull(message = "City should not be null")
        String city,
        @NotNull(message = "State should not be null")
        String state,
        @NotNull(message = "Country should not be null")
        String country,
        @NotNull(message = "Zipcode should not be null")
        String zipcode
) {
}
