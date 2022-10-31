package com.misiontic2022.HackerTIC.Controllers;

import com.misiontic2022.HackerTIC.Entities.Employee;
import com.misiontic2022.HackerTIC.Entities.Enterprise;
import com.misiontic2022.HackerTIC.Services.EmployeeService;
import com.misiontic2022.HackerTIC.Services.EnterpriseService;
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
import javax.validation.Valid;

@Controller
@RequestMapping("/enterprises")
public class EnterpriseController {

    @Autowired
    private EnterpriseService service;
    
    @Autowired
    private EmployeeService serviceEmp;

    public EnterpriseController(EnterpriseService service, EmployeeService serviceEmp) {

        this.service = service;
        this.serviceEmp = serviceEmp;

    }
    
    @GetMapping("/")
    public String getEnterpriseAll(Model mod, @AuthenticationPrincipal OidcUser principal) {

        mod.addAttribute("enterprises", service.findEnterpriseAll());
        
        if(principal != null) {
            
            Employee employeeDB = serviceEmp.findEmployeeByEmail(principal.getEmail());
            
            if(employeeDB != null) {
                
                if(employeeDB.getRole().name() == "Admin") {
                    
                    mod.addAttribute("employeeDB", employeeDB);
                    
                }
                
                return "enterprise";
                
            } else {
                
                return "redirect:/";
                
            }
            
        }
        
        return "redirect:/";

    }

    @PostMapping("/")
    public String createEnterprise(@Valid @ModelAttribute Enterprise enterprise, BindingResult resultV) {

        if(resultV.hasErrors()) {
            
            return "newEnterprise";
            
        } else {
            
            service.createEnterprise(enterprise);
            
            return "redirect:/enterprises/";
            
        }        

    }

    @GetMapping("/{id}")
    public String getEnterprise(Model mod, @PathVariable("id") Long id, @AuthenticationPrincipal OidcUser principal) {
        
        mod.addAttribute("enterprise", service.findEnterpriseById(id));
        
        if(principal != null) {
            
            Employee employeeDB = serviceEmp.findEmployeeByEmail(principal.getEmail());
            
            if(employeeDB != null) {
                
                if(employeeDB.getRole().name() == "Admin") {
                    
                    mod.addAttribute("employeeDB", employeeDB);
                    
                }
                
                return "actEnterprise";
                
            } else {
                
                return "redirect:/";
                
            }
            
        }
        
        return "redirect:/";

    }

    @PatchMapping("/{id}")
    public String updateEnterprise(@Valid @ModelAttribute Enterprise enterprise, BindingResult resultV, @PathVariable("id") Long id) {

        if(resultV.hasErrors()) {
            
            return "actEnterprise";
            
        } else {
            
            service.updateEnterprise(id, enterprise);
            
            return "redirect:/enterprises/";
            
        }

    }

    @DeleteMapping("/{id}")
    public String deleteEnterprise(@PathVariable("id") Long id) {

        service.deleteEnterprise(id);
        
        return "redirect:/enterprises/";

    }

}