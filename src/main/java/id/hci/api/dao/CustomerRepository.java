package id.hci.api.dao;

import id.hci.api.dao.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value = "SELECT * from customer where user_name = ?1 ",
            nativeQuery = true)
    Optional<Customer> findByUser_name(String username);

}
