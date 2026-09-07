package in.cs.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cs.main.entities.Accounts;
@Repository
public interface AccountRepository extends JpaRepository<Accounts, Integer> {

    Optional<Accounts> findByAccountNumber(String accountNumber);

    List<Accounts> findByUserUserId(Integer userId);

    boolean existsByAccountNumber(String accountNumber);

}
