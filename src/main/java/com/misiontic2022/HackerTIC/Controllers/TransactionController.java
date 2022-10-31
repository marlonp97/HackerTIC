package com.misiontic2022.HackerTIC.Controllers;

import com.misiontic2022.HackerTIC.Entities.Employee;
import com.misiontic2022.HackerTIC.Entities.Transaction;
import com.misiontic2022.HackerTIC.Services.EmployeeService;
import com.misiontic2022.HackerTIC.Services.EnterpriseService;
import com.misiontic2022.HackerTIC.Services.TransactionService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/enterprises/{enterpriseId}/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;
    
    @Autowired
    private EnterpriseService serviceEnt;
    
    @Autowired
    private EmployeeService serviceEmp;

    public TransactionController(TransactionService service, EnterpriseService serviceEnt, EmployeeService serviceEmp) {

        this.service = service;
        this.serviceEnt = serviceEnt;
        this.serviceEmp = serviceEmp;

    }

    @GetMapping("/")
    public String getTransactionAll(Model mod, @PathVariable("enterpriseId") Long enterpriseId, @AuthenticationPrincipal OidcUser principal) {

        mod.addAttribute("transactions", service.findTransactionAllEnterprise(enterpriseId));
        mod.addAttribute("enterprise", serviceEnt.findEnterpriseById(enterpriseId));
        
        if(principal != null) {
            
            Employee employeeDB = serviceEmp.findEmployeeByEmail(principal.getEmail());
            
            if(employeeDB != null) {
                
                if(employeeDB.getRole().name() == "Admin") {
                    
                    mod.addAttribute("employeeDB", employeeDB);
                    
                }
                
                return "enterpriseTransaction";
                
            } else {
                
                return "redirect:/";
                
            }
            
        }
        
        return "redirect:/";
        
    }

    @PostMapping("/")
    public String createTransaction(@Valid @ModelAttribute Transaction transaction, @PathVariable("enterpriseId") Long enterpriseId, BindingResult resultV) {
        
        if(resultV.hasErrors()) {
            
            return "newTransaction";
            
        } else {
            
            service.createTransaction(enterpriseId, transaction);
            
            return "redirect:/enterprises/{enterpriseId}/transactions/";
            
        }

    }

    @GetMapping("/{id}")
    public String getTransaction(Model mod, @PathVariable("enterpriseId") Long enterpriseId, @PathVariable("id") Long id, @AuthenticationPrincipal OidcUser principal) {

        mod.addAttribute("transaction", service.findTransactionById(enterpriseId, id));
        mod.addAttribute("enterprise", serviceEnt.findEnterpriseById(enterpriseId));
        
        if(principal != null) {
            
            Employee employeeDB = serviceEmp.findEmployeeByEmail(principal.getEmail());
            
            if(employeeDB != null) {
                
                if(employeeDB.getRole().name() == "Admin") {
                    
                    mod.addAttribute("employeeDB", employeeDB);
                    
                }
                
                return "actTransaction";
                
            } else {
                
                return "redirect:/";
                
            }
            
        }

        return "redirect:/";

    }

    @PatchMapping("/{id}")
    public String updateTransaction(@Valid @ModelAttribute Transaction transaction, BindingResult resultV,  @PathVariable("enterpriseId") Long enterpriseId, @PathVariable("id") Long id) {
        
        if(resultV.hasErrors()) {
            
            return "actTransaction";
            
        } else {
            
            service.updateTransaction(enterpriseId, id, transaction);
            
            return "redirect:/enterprises/{enterpriseId}/transactions/";
            
        }

    }

    @DeleteMapping("/{id}")
    
    public String deleteTransaction(@PathVariable("enterpriseId") Long enterpriseId, @PathVariable("id") Long id) {

        service.deleteTransaction(enterpriseId, id);
        
        return "redirect:/enterprises/{enterpriseId}/transactions/";

    }

}