package com.misiontic2022.HackerTIC.Repositories;

import com.misiontic2022.HackerTIC.Entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    public Employee findByEmail(String email);

}