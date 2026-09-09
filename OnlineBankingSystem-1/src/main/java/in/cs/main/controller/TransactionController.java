package in.cs.main.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import in.cs.main.entities.Transaction;
import in.cs.main.service.TransactionService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

	@Autowired
    private  TransactionService transactionService;



   
    // DEPOSIT
   

    @PostMapping("/deposit")
    public ResponseEntity<Transaction> deposit(
            @RequestParam String accountNumber,
            @RequestParam BigDecimal amount) {

        Transaction transaction =
                transactionService.deposit(
                        accountNumber,
                        amount
                );

        return ResponseEntity.ok(transaction);
    }


    // WITHDRAW
   

    @PostMapping("/withdraw")
    public ResponseEntity<Transaction> withdraw(
            @RequestParam String accountNumber,
            @RequestParam BigDecimal amount) {

        Transaction transaction =
                transactionService.withdraw(
                        accountNumber,
                        amount
                );

        return ResponseEntity.ok(transaction);
    }


    // TRANSFER
    

    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transfer(
            @RequestParam String fromAccount,
            @RequestParam String toAccount,
            @RequestParam BigDecimal amount) {

        Transaction transaction =
                transactionService.transfer(
                        fromAccount,
                        toAccount,
                        amount
                );

        return ResponseEntity.ok(transaction);
    }


    // TRANSACTION HISTORY
  

    @GetMapping("/history/{accountId}")
    public ResponseEntity<List<Transaction>> getHistory(
            @PathVariable Integer accountId) {

        List<Transaction> transactions =
                transactionService.getTransactionHistory(
                        accountId
                );

        return ResponseEntity.ok(transactions);
    }
}

