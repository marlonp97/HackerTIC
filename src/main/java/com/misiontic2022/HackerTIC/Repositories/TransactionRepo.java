package com.misiontic2022.HackerTIC.Repositories;

import com.misiontic2022.HackerTIC.Entities.Enterprise;
import com.misiontic2022.HackerTIC.Entities.Transaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
	
    public List<Transaction> findByEnterprise(Enterprise enterprise);
    public Transaction findByIdAndEnterprise(Long id, Enterprise enterprise);
	
}