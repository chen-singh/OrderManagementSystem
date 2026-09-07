package in.cs.main.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cs.main.entities.Accounts;
import in.cs.main.entities.Users;
import in.cs.main.repository.AccountRepository;
import in.cs.main.repository.UserRepository;

@Service
public class AccountService {

		private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository,
                          UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    // Create Bank Account
    public Accounts createAccount(Accounts account, Integer userId) {

        Users user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // Generate account number
        long accountNumber = generateAccountNumber();

        account.setAccount_number(accountNumber);
        account.setUser(user);
        account.setAccountHolderName(user.getFullName());

        if (account.getBalance() == null) {
            account.setBalance(BigDecimal.ZERO);
        }

        account.setStatus("ACTIVE");

        return accountRepository.save(account);
    }

    // Generate Account Number
    private long generateAccountNumber() {

        return "ACC" +
                UUID.randomUUID()
                        .toString()
                        .replace("-", "")
                        .substring(0, 12)
                        .toUpperCase();
    }

    // Find account by account number
    public Accounts getAccountByNumber(String accountNumber) {

        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));
    }
	
	
}
