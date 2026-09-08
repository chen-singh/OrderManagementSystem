package in.cs.main.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import in.cs.main.entities.Accounts;
import in.cs.main.entities.Transaction;
import in.cs.main.repository.AccountRepository;
import in.cs.main.repository.TransactionRepository;
import jakarta.transaction.Transactional;

@Service
public class TransactionService {



	    private final TransactionRepository transactionRepository;
	    private final AccountRepository accountRepository;

	    public TransactionService(TransactionRepository transactionRepository,
	                              AccountRepository accountRepository) {
	        this.transactionRepository = transactionRepository;
	        this.accountRepository = accountRepository;
	    }

	   
	    // DEPOSIT
	    

	    @Transactional
	    public Transaction deposit(String accountNumber,
	                               BigDecimal amount) {

	        validateAmount(amount);

	        Accounts account = accountRepository
	                .findByAccountNumber(accountNumber)
	                .orElseThrow(() ->
	                        new RuntimeException("Account not found"));

	        if (!account.getStatus().equals("ACTIVE")) {
	            throw new RuntimeException("Account is not active");
	        }

	        BigDecimal newBalance =
	                account.getBalance().add(amount);

	        account.setBalance(newBalance);

	        accountRepository.save(account);

	        Transaction transaction = new Transaction();

	        transaction.setAccount(account);
	        transaction.setTransactionType("DEPOSIT");
	        transaction.setAmount(amount);
	        transaction.setDescription("Cash deposit");
	        transaction.setTransactionDate(LocalDateTime.now());

	        return transactionRepository.save(transaction);
	    }

	    
	    // WITHDRAW
	    

	    @Transactional
	    public Transaction withdraw(String accountNumber,
	                                BigDecimal amount) {

	        validateAmount(amount);

	        Accounts account = accountRepository
	                .findByAccountNumber(accountNumber)
	                .orElseThrow(() ->
	                        new RuntimeException("Account not found"));

	        if (!account.getStatus().equals("ACTIVE")) {
	            throw new RuntimeException("Account is not active");
	        }

	        if (account.getBalance().compareTo(amount) < 0) {
	            throw new RuntimeException("Insufficient balance");
	        }

	        BigDecimal newBalance =
	                account.getBalance().subtract(amount);

	        account.setBalance(newBalance);

	        accountRepository.save(account);

	        Transaction transaction = new Transaction();

	        transaction.setAccount(account);
	        transaction.setTransactionType("WITHDRAW");
	        transaction.setAmount(amount);
	        transaction.setDescription("Cash withdrawal");
	        transaction.setTransactionDate(LocalDateTime.now());

	        return transactionRepository.save(transaction);
	    }

	    
	    // TRANSFER
	    

	    @Transactional
	    public Transaction transfer(String fromAccountNumber,
	                                String toAccountNumber,
	                                BigDecimal amount) {

	        validateAmount(amount);

	        if (fromAccountNumber.equals(toAccountNumber)) {
	            throw new RuntimeException(
	                    "Cannot transfer to the same account");
	        }

	        Accounts sender = accountRepository
	                .findByAccountNumber(fromAccountNumber)
	                .orElseThrow(() ->
	                        new RuntimeException(
	                                "Sender account not found"));

	        Accounts receiver = accountRepository
	                .findByAccountNumber(toAccountNumber)
	                .orElseThrow(() ->
	                        new RuntimeException(
	                                "Receiver account not found"));

	        if (!sender.getStatus().equals("ACTIVE")) {
	            throw new RuntimeException(
	                    "Sender account is not active");
	        }

	        if (!receiver.getStatus().equals("ACTIVE")) {
	            throw new RuntimeException(
	                    "Receiver account is not active");
	        }

	        if (sender.getBalance().compareTo(amount) < 0) {
	            throw new RuntimeException(
	                    "Insufficient balance");
	        }

	        // Remove money from sender
	        sender.setBalance(
	                sender.getBalance().subtract(amount)
	        );

	        // Add money to receiver
	        receiver.setBalance(
	                receiver.getBalance().add(amount)
	        );

	        accountRepository.save(sender);
	        accountRepository.save(receiver);

	        // Sender transaction
	        Transaction transaction = new Transaction();

	        transaction.setAccount(sender);
	        transaction.setTransactionType("TRANSFER");
	        transaction.setAmount(amount);
	        transaction.setRelatedAccount(toAccountNumber);
	        transaction.setDescription(
	                "Transfer to " + toAccountNumber
	        );
	        transaction.setTransactionDate(LocalDateTime.now());

	        return transactionRepository.save(transaction);
	    }

	    
	    // TRANSACTION HISTORY
	    

	    public List<Transaction> getTransactionHistory(
	            Integer accountId) {

	        return transactionRepository
	                .findByAccountAccountId(accountId);
	    }

	    
	    // VALIDATION
	    

	    private void validateAmount(BigDecimal amount) {

	        if (amount == null) {
	            throw new RuntimeException(
	                    "Amount cannot be null");
	        }

	        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
	            throw new RuntimeException(
	                    "Amount must be greater than zero");
	        }
	    }
	}

