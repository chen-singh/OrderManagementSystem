package in.cs.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cs.main.entities.Accounts;
import in.cs.main.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountrepo;
	
	private Accounts createAccount(Accounts account) {
		return accountrepo.save(account);
		
	}
	
	
}
