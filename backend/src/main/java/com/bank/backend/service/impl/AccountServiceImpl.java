package com.bank.backend.service.impl;

import com.bank.backend.constant.TransactionMode;
import com.bank.backend.model.Account;
import com.bank.backend.model.Transaction;
import com.bank.backend.repository.AccountRepository;
import com.bank.backend.repository.AddressRepository;
import com.bank.backend.service.IAccountService;
import com.bank.backend.service.ITransactionService;
import com.bank.backend.utility.GenerateAccountNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {
    private final AccountRepository accountRepository;
    private final AddressRepository addressRepository;
    private final ITransactionService transactionService;
    private final PasswordEncoder passwordEncoder;

    @Value("${upload.file.name}")
    private String uploadFileLocation;

    @Override
    public Account createAccount(Account account) {
        account.setAccountNumber(GenerateAccountNumber.generate());
        var bcrypt = passwordEncoder.encode(account.getCredential().getAccountPassword());
        account.getCredential().setAccountPassword(bcrypt);

        accountRepository.save(account);
        return account;
    }

    @Override
    public Account saveAccount(Account account) {
        account.setAccountNumber(GenerateAccountNumber.generate());
        var bcrypt = passwordEncoder.encode(account.getCredential().getAccountPassword());
        account.getCredential().setAccountPassword(bcrypt);

        var address = account.getAddress();
        address.setAccount(account);

        // addressRepository.save(address);
        accountRepository.save(account);
        return account;
    }

    @Override
    public Account updateAccount(long accountNumber, Account account) {
        var existingAccount = getAccount(accountNumber);
        existingAccount.setAccountHolderName(account.getAccountHolderName());
        existingAccount.setContactNumber(account.getContactNumber());
        existingAccount.setAboutCustomer(account.getAboutCustomer());
        existingAccount.setAccountType(account.getAccountType());

        if (existingAccount.getAddress() != null) {
            account.getAddress().setAddressId(existingAccount.getAddress().getAddressId());

        }

        account.getAddress().setAccount(existingAccount);
        existingAccount.setAddress(account.getAddress());

        // addressRepository.save(account.getAddress());
        accountRepository.save(existingAccount);

        return existingAccount;
    }

    @Override
    public Account uploadProfilePicture(long accountNumber, MultipartFile file) throws Exception {
        var account = getAccount(accountNumber);

        var fileName = file.getOriginalFilename();
        var extensionName = fileName.substring(fileName.lastIndexOf('.'));
        var name = fileName.substring(0, fileName.lastIndexOf('.'));
        var newName = uploadFileLocation + name + "-" + System.currentTimeMillis() + extensionName;

        var fos = new FileOutputStream(newName);
        fos.write(file.getBytes());

        account.setProfilePicture(newName);
        accountRepository.save(account);

        fos.close();
        return account;
    }

    @Override
    public void depositBalance(long accountNumber, double balance) {
        getAccount(accountNumber);
        accountRepository.addBalance(accountNumber, balance);

        var transaction = new Transaction();
        transaction.setMode(TransactionMode.CREDIT);
        transaction.setAmount(balance);

        transactionService.addTransaction(transaction, accountNumber);
    }

    @Override
    public void withdrawalBalance(long accountNumber, double balance) {
        var account = getAccount(accountNumber);
        if (account.getAccountBalance() < balance)
            throw new RuntimeException("Insufficient balance");

        accountRepository.deductBalance(accountNumber, balance);

        var transaction = new Transaction();
        transaction.setMode(TransactionMode.DEBIT);
        transaction.setAmount(balance);

        transactionService.addTransaction(transaction, accountNumber);
    }

    @Transactional
    @Override
    public void transfer(long sender, long receiver, double balance) {
        var receiverAccount = accountRepository.existsByAccountNumber(receiver);
        if (!receiverAccount) {
            throw new RuntimeException("Receiver account not found");
        }

        var senderAccount = getAccount(sender);
        var senderBalance = senderAccount.getAccountBalance();

        if (senderBalance < balance) {
            throw new RuntimeException("Insufficient balance");
        }

        accountRepository.addBalance(receiver, balance);
        accountRepository.deductBalance(sender, balance);

        var transaction = new Transaction();
        transaction.setMode(TransactionMode.TRANSFER);
        transaction.setAmount(balance);

        transactionService.addTransaction(transaction, sender);
    }

    @Override
    public Account deleteAccount(long accountNumber) {
        var account = getAccount(accountNumber);
        accountRepository.delete(account);
        return account;
    }
    @Override
    public byte[] getProfilePicture(long accountNumber) throws Exception {
        var account = getAccount(accountNumber);
        var fileLocation = account.getProfilePicture();
        if (fileLocation == null)
            throw new NoSuchElementException("Image not present");
        var fis = new FileInputStream(fileLocation);
        var image = fis.readAllBytes();
        fis.close();
        return image;
    }

    @Override
    public Account getAccount(long accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber).orElseThrow();
    }

    @Override
    public Account getAccountBySlNo(int slNo) {
        return accountRepository.findById(slNo).orElseThrow();
    }

    @Override
    public Account getAccountByEmail(String email) {
        return accountRepository.findByEmail(email).orElseThrow();
    }

    @Override
    public Account getAccountByEmailAndPassword(String email, String password) {
        return accountRepository.findByCredentialAccountEmailAndCredentialAccountPassword(email, password)
                .orElseThrow();
    }

    @Override
    public Account getAccountByAddressId(String addressId) {
        var result = addressRepository.findById(addressId).orElseThrow();
        return result.getAccount();
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }
}
