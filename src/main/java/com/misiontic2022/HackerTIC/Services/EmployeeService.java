package com.misiontic2022.HackerTIC.Services;

import com.misiontic2022.HackerTIC.Entities.Employee;
import com.misiontic2022.HackerTIC.Entities.Enterprise;
import com.misiontic2022.HackerTIC.Entities.Profile;
import com.misiontic2022.HackerTIC.Repositories.EmployeeRepo;
import com.misiontic2022.HackerTIC.Repositories.EnterpriseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo repo;
    
    @Autowired
    private EnterpriseRepo repoEnt;

    public EmployeeService(EmployeeRepo repo, EnterpriseRepo repoEnt) {

        this.repo = repo;
        this.repoEnt = repoEnt;

    }

    public List<Employee> findEmployeeAll() {

        return repo.findAll();

    }

    public Employee findEmployeeById(Long id) {

        return repo.findById(id).orElse(null);

    }
    
    public Employee findEmployeeByEmail(String email) {
        
        return repo.findByEmail(email);
        
    }

    public Employee createEmployee(Employee employee) {

        Employee employeeDB = repo.findByEmail(employee.getEmail());

        if(employeeDB != null) {

            return null;

        } else {
                
            Enterprise enterprise = repoEnt.findByName(employee.getEnterprise().getName());
            employee.setEnterprise(enterprise);
        	
        	Calendar calendar = Calendar.getInstance();
            employee.setCreatedAt(new Date((calendar.getTime()).getTime()));
            employee.getProfile().setCreatedAt(new Date((calendar.getTime()).getTime()));

            return repo.save(employee);

        }

    }

    public Employee updateEmployee(Long id, Employee employee) {

        Employee employeeDB = repo.findById(id).orElse(null);

        if(employeeDB == null) {
        	
        	return null;

        } else {
        	
        	Profile profileDB = employeeDB.getProfile();
        	profileDB.setImage(employee.getProfile().getImage());
        	profileDB.setPhone(employee.getProfile().getPhone());
        	
        	Enterprise enterprise = repoEnt.findByName(employee.getEnterprise().getName());
        	employeeDB.setEnterprise(enterprise);

            employeeDB.setRole(employee.getRole());
            Calendar calendar = Calendar.getInstance();
            employeeDB.setUpdatedAt(new Date((calendar.getTime()).getTime()));
            profileDB.setUpdatedAt(new Date((calendar.getTime()).getTime()));
            employeeDB.setProfile(profileDB);

            return repo.save(employeeDB);

        }

    }

    public void deleteEmployee(Long id) {
    	
    	Employee employeeDB = repo.findById(id).orElse(null);

        if(employeeDB != null) {
        	
        	repo.deleteById(id);

        }

    }

}