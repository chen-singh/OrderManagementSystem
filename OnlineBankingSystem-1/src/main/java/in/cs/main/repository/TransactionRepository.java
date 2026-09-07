package in.cs.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cs.main.entities.Transaction;

import java.util.List;
@Repository
public interface TransactionRepository
        extends JpaRepository<Transaction, Integer> {

    List<Transaction> findByAccountAccountId(Integer accountId);
}
