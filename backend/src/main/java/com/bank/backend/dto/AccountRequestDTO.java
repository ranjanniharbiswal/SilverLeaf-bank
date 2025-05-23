package com.bank.backend.dto;

import com.bank.backend.constant.AccountType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record AccountRequestDTO(
        @NotNull(message = "Account holder name should not be Null")
        String accountHolderName,
        @NotNull(message = "About customer should not be Null")
        String aboutCustomer,
        @NotNull(message = "Contact number should not be Null")
        String contactNumber,
        @NotNull(message = "Account email should not be Null")
        @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Enter a valid email")
        String accountEmail,
        @NotNull(message = "Account password should not be Null")
        String accountPassword,
        @NotNull(message = "Account type should not be Null")
        AccountType accountType
) {
}
