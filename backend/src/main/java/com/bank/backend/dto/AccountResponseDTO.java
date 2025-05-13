package com.bank.backend.dto;

import com.bank.backend.constant.AccountType;

public record AccountResponseDTO(
        long accountNumber,
        String accountHolderName,
        String aboutCustomer,
        String contactNumber,
        String accountEmail,
        double accountBalance,
        AccountType accountType,
        String city,
        String state,
        String country,
        String zipcode
) {
}
