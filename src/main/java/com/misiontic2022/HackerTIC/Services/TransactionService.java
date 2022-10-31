package com.misiontic2022.HackerTIC.Services;

import com.misiontic2022.HackerTIC.Entities.Employee;
import com.misiontic2022.HackerTIC.Entities.Enterprise;
import com.misiontic2022.HackerTIC.Entities.Transaction;
import com.misiontic2022.HackerTIC.Repositories.EmployeeRepo;
import com.misiontic2022.HackerTIC.Repositories.EnterpriseRepo;
import com.misiontic2022.HackerTIC.Repositories.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepo repo;
    
    @Autowired
    private EnterpriseRepo repoEnt;
    
    @Autowired
    private EmployeeRepo repoEmp;

    public TransactionService(TransactionRepo repo, EnterpriseRepo repoEnt, EmployeeRepo repoEmp) {

        this.repo = repo;
        this.repoEnt = repoEnt;
        this.repoEmp = repoEmp;

    }
    
    public List<Transaction> findTransactionAll() {

        return repo.findAll();

    }

    public List<Transaction> findTransactionAllEnterprise(Long enterpriseId) {

        return repo.findByEnterprise(repoEnt.findById(enterpriseId).orElse(null));

    }

    public Transaction findTransactionById(Long enterpriseId, Long id) {
    	
    	Enterprise enterpriseDB = repoEnt.findById(enterpriseId).orElse(null);
    	
    	if(enterpriseDB == null) {
    		
    		return null;
    		
    	} else {
    		
    		return repo.findByIdAndEnterprise(id, enterpriseDB);
    		
    	}

    }

    public Transaction createTransaction(Long enterpriseId, Transaction transaction) {
    	
    	Enterprise enterpriseDB = repoEnt.findById(enterpriseId).orElse(null);
    	Employee employeeDB = repoEmp.findByEmail(transaction.getEmployee().getEmail());
    	
    	if((enterpriseDB == null) || (employeeDB == null)) {
    		
    		return null;
    		
    	} else {
    		
    		transaction.setEnterprise(enterpriseDB);
    		transaction.setEmployee(employeeDB);
        	
            Calendar calendar = Calendar.getInstance();
            transaction.setCreatedAt(new Date((calendar.getTime()).getTime()));
            
            return repo.save(transaction);
    		
    	}
    	
    }

    public Transaction updateTransaction(Long enterpriseId, Long id, Transaction transaction) {

    	Enterprise enterpriseDB = repoEnt.findById(enterpriseId).orElse(null);
    	Employee employeeDB = repoEmp.findByEmail(transaction.getEmployee().getEmail());
    	
    	if((enterpriseDB == null) || (employeeDB == null)) {
    		
    		return null;
    		
    	} else {
    		
    		Transaction transactionDB = repo.findByIdAndEnterprise(id, enterpriseDB);
    		
    		if(transactionDB == null) {

                return null;

            } else {
            	
            	Enterprise enterprise = repoEnt.findByName(transaction.getEnterprise().getName());
            	transactionDB.setEnterprise(enterprise);
            	transactionDB.setEmployee(employeeDB);

                transactionDB.setConcept(transaction.getConcept());
                transactionDB.setAmount(transaction.getAmount());
                Calendar calendar = Calendar.getInstance();
                transactionDB.setUpdatedAt(new Date((calendar.getTime()).getTime()));

                return repo.save(transactionDB);

            }
    		
    	}

    }

    public void deleteTransaction(Long enterpriseId, Long id) {
    	
    	Enterprise enterpriseDB = repoEnt.findById(enterpriseId).orElse(null);
    	Transaction transactionDB = repo.findById(id).orElse(null);
    	
    	if((enterpriseDB != null) && (transactionDB != null)) {
    		
    		repo.deleteById(id);
    		
    	}

    }

}