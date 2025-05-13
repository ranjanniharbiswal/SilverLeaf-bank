package com.bank.backend.mapper;

import com.bank.backend.dto.AccountAddressDTO;
import com.bank.backend.dto.AccountRequestDTO;
import com.bank.backend.dto.AccountResponseDTO;
import com.bank.backend.dto.UpdateDTO;
import com.bank.backend.model.Account;
import com.bank.backend.model.Address;
import com.bank.backend.model.Credential;
import org.springframework.beans.BeanUtils;

public class AccountMapper {
    private AccountMapper() {}

    public static Account modelMapper(AccountAddressDTO dto) {
        var credential = new Credential();
        BeanUtils.copyProperties(dto, credential);

        var address = new Address();
        BeanUtils.copyProperties(dto, address);

        var account = new Account();
        BeanUtils.copyProperties(dto, account);

        account.setCredential(credential);
        account.setAddress(address);

        return account;
    }

    public static Account modelMapper(AccountRequestDTO dto) {
        var credential = new Credential();
        BeanUtils.copyProperties(dto, credential);

        var account = new Account();
        BeanUtils.copyProperties(dto, account);
        account.setCredential(credential);

        return account;
    }

    public static Account modelMapper(UpdateDTO dto) {
        var address = new Address();
        BeanUtils.copyProperties(dto, address);

        var account = new Account();
        BeanUtils.copyProperties(dto, account);

        account.setAddress(address);

        return account;
    }

    public static AccountResponseDTO dtoMapper(Account account) {
        var credential = account.getCredential();
        var address = account.getAddress() == null ?
                new Address() : account.getAddress();

        return new AccountResponseDTO(
                account.getAccountNumber(),
                account.getAccountHolderName(),
                account.getAboutCustomer(),
                account.getContactNumber(),
                credential.getAccountEmail(),
                account.getAccountBalance(),
                account.getAccountType(),
                address.getCity(),
                address.getState(),
                address.getCountry(),
                address.getZipcode()
        );
    }
}
