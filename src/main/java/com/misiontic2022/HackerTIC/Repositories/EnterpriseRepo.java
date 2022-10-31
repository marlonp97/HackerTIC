package com.misiontic2022.HackerTIC.Repositories;

import com.misiontic2022.HackerTIC.Entities.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnterpriseRepo extends JpaRepository<Enterprise, Long> {

    public Enterprise findByName(String name);
    public Enterprise findByDocument(String document);

}