package com.misiontic2022.HackerTIC.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.misiontic2022.HackerTIC.Entities.Employee;
import com.misiontic2022.HackerTIC.Entities.Enterprise;
import com.misiontic2022.HackerTIC.Entities.Transaction;
import com.misiontic2022.HackerTIC.Services.EmployeeService;
import com.misiontic2022.HackerTIC.Services.EnterpriseService;

@Controller
public class FrontController {
    
    @Autowired
    private EmployeeService serviceEmp;
    
    @Autowired
    private EnterpriseService serviceEnt;

    public FrontController(EmployeeService serviceEmp, EnterpriseService serviceEnt) {

        this.serviceEmp = serviceEmp;
        this.serviceEnt = serviceEnt;

    }
	
	@GetMapping("/")
	public String index() {
	    
		return "index";
		
	}
	
	@GetMapping("/enterprises/newEnterprise/")
    public String newEnterprise(Model mod, @AuthenticationPrincipal OidcUser principal) {
	    
	    mod.addAttribute("enterprise", new Enterprise());
	    
        if(principal != null) {
            
            Employee employeeDB = serviceEmp.findEmployeeByEmail(principal.getEmail());
            
            if(employeeDB != null) {
                
                if(employeeDB.getRole().name() == "Admin") {
                    
                    mod.addAttribute("employeeDB", employeeDB);
                    
                }
                
                return "newEnterprise";
                
            } else {
                
                return "redirect:/";
                
            }
            
        }
        
        return "redirect:/";
        
    }
	
	@GetMapping("/employees/newEmployee/")
    public String newEmployee(Model mod, @AuthenticationPrincipal OidcUser principal) {
        
        mod.addAttribute("employee", new Employee());
        mod.addAttribute("enterprises", serviceEnt.findEnterpriseAll());
        
        if(principal != null) {
            
            Employee employeeDB = serviceEmp.findEmployeeByEmail(principal.getEmail());
            
            if(employeeDB != null) {
                
                if(employeeDB.getRole().name() == "Admin") {
                    
                    mod.addAttribute("employeeDB", employeeDB);
                    
                }
                
                return "newEmployee";
                
            } else {
                
                return "redirect:/";
                
            }
            
        }
        
        return "redirect:/";
        
    }
	
	@GetMapping("/enterprises/{enterpriseId}/transactions/newTransaction/")
    public String newTransaction(Model mod, @PathVariable("enterpriseId") Long enterpriseId, @AuthenticationPrincipal OidcUser principal) {
        
        Transaction transaction = new Transaction();
        
        mod.addAttribute("enterprise", serviceEnt.findEnterpriseById(enterpriseId));
        
        if(principal != null) {
            
            Employee employeeDB = serviceEmp.findEmployeeByEmail(principal.getEmail());
            
            if(employeeDB != null) {
                
                transaction.setEmployee(employeeDB);
                mod.addAttribute("transaction", transaction);
                
                return "newTransaction";
                
            } else {
                
                return "redirect:/";
                
            }
            
        }
        
        return "redirect:/";
        
    }

}