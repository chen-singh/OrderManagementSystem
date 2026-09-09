package in.cs.main.controller;


import in.cs.main.entities.Accounts;
import in.cs.main.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")
public class AccountController {
	
	
@Autowired
 private	AccountService accountService;
	
  
    // CREATE ACCOUNT
    

    @PostMapping("/create/{userId}")
    public ResponseEntity<Accounts> createAccount(
            @PathVariable Integer userId,
            @RequestBody Accounts account) {

        Accounts createdAccount =
                accountService.createAccount(
                        account,
                        userId
                );

        return ResponseEntity.ok(createdAccount);
    }

    
    // GET ACCOUNT BY ID
   

//    @GetMapping("/{accountId}")
//    public ResponseEntity<Accounts> getAccountById(
//            @PathVariable Integer accountId) {
//
//        Accounts account =
//                accountService.getAccountById(accountId);
//
//        return ResponseEntity.ok(account);
//    }

    
    // GET ACCOUNT BY NUMBER
    

    @GetMapping("/number/{accountNumber}")
    public ResponseEntity<Accounts> getAccountByNumber(
            @PathVariable String accountNumber) {

        Accounts account =
                accountService.getAccountByNumber(
                        accountNumber
                );

        return ResponseEntity.ok(account);
    }

    
    // CHECK BALANCE
    

    @GetMapping("/balance/{accountNumber}")
    public ResponseEntity<BigDecimal> checkBalance(
            @PathVariable String accountNumber) {

        BigDecimal balance =
                accountService.checkBalance(
                        accountNumber
                );

        return ResponseEntity.ok(balance);
    }

    
    // GET USER ACCOUNTS
    

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Accounts>> getUserAccounts(
            @PathVariable Integer userId) {

        return ResponseEntity.ok(
                accountService.getUserAccounts(userId)
        );
    }

    
    // GET ALL ACCOUNTS
    

    @GetMapping
    public ResponseEntity<List<Accounts>> getAllAccounts() {

        return ResponseEntity.ok(
                accountService.getAllAccounts()
        );
    }
}

