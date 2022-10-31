    package com.misiontic2022.HackerTIC.Controllers;

import com.misiontic2022.HackerTIC.Entities.Employee;
import com.misiontic2022.HackerTIC.Services.EmployeeService;
import com.misiontic2022.HackerTIC.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transactions")
public class TransactionController2 {

    @Autowired
    private TransactionService service;
    
    @Autowired
    private EmployeeService serviceEmp;

    public TransactionController2(TransactionService service, EmployeeService serviceEmp) {

        this.service = service;
        this.serviceEmp = serviceEmp;

    }

    @GetMapping("/")
    public String getTransactionAll(Model mod, @AuthenticationPrincipal OidcUser principal) {
        
        mod.addAttribute("transactions", service.findTransactionAll());
        
        if(principal != null) {
            
            Employee employeeDB = serviceEmp.findEmployeeByEmail(principal.getEmail());
            
            if(employeeDB != null) {
                
                return "transaction";
                
            } else {
                
                return "redirect:/";
                
            }
            
        }

        return "redirect:/";

    }

}