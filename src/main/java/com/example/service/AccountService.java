package com.example.service;

import java.util.List;
import java.util.Optional;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exceptions.ApiExceptions.BadRequestException;
import com.example.exceptions.ApiExceptions.DuplicateResourceException;
import com.example.exceptions.ApiExceptions.UnauthorizedException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account registerAccount(String username, String password) {
        if (username.isBlank() || username == null){
            throw new BadRequestException("username must not be blank");
        }
       if (password.length() < 4 || password == null) {
            throw new BadRequestException("Password must at least 4 characters");
        }
        if (accountRepository.existsByUsername(username)){
            throw new DuplicateResourceException("Duplicate user found");
        }
        Account account = new Account(username, password);
        return accountRepository.save(account);  
    }

    public Account login(String username, String password) {
        return accountRepository.findByUsernameAndPassword(username, password)
            .orElseThrow(() -> new UnauthorizedException("Username and password combination does not exist"));
    }

    public boolean usernameExists(String username) {
        return accountRepository.existsByUsername(username);
    }

}
