package com.misiontic2022.HackerTIC.Services;

import com.misiontic2022.HackerTIC.Entities.Enterprise;
import com.misiontic2022.HackerTIC.Repositories.EnterpriseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Service
public class EnterpriseService {

    @Autowired
    private EnterpriseRepo repo;

    public EnterpriseService(EnterpriseRepo repo) {

        this.repo = repo;

    }

    public List<Enterprise> findEnterpriseAll() {

        return repo.findAll();

    }

    public Enterprise findEnterpriseById(Long id) {

        return repo.findById(id).orElse(null);

    }

    public Enterprise createEnterprise(Enterprise enterprise) {

    	Enterprise enterpriseByNameDB = repo.findByName(enterprise.getName());
        Enterprise enterpriseByDocumentDB = repo.findByDocument(enterprise.getDocument());
        
        if((enterpriseByNameDB != null) || (enterpriseByDocumentDB != null)) {

            return null;

        } else {

            Calendar calendar = Calendar.getInstance();
            enterprise.setCreatedAt(new Date((calendar.getTime()).getTime()));

            return repo.save(enterprise);

        }

    }

    public Enterprise updateEnterprise(Long id, Enterprise enterprise) {
    	
    	Enterprise enterpriseDB = repo.findById(id).orElse(null);

        if(enterpriseDB == null) {

            return null;

        } else {

        	enterpriseDB.setPhone(enterprise.getPhone());
        	enterpriseDB.setAddress(enterprise.getAddress());
            Calendar calendar = Calendar.getInstance();
            enterpriseDB.setUpdatedAt(new Date((calendar.getTime()).getTime()));

            return repo.save(enterpriseDB);

        }

    }

    public void deleteEnterprise(Long id) {

    	Enterprise enterpriseDB = repo.findById(id).orElse(null);

        if(enterpriseDB != null) {
        	
        	repo.deleteById(id);

        }

    }

}