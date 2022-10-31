package com.misiontic2022.HackerTIC.Controllers;

import com.misiontic2022.HackerTIC.Entities.Employee;
import com.misiontic2022.HackerTIC.Services.EmployeeService;
import com.misiontic2022.HackerTIC.Services.EnterpriseService;
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
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;
    
    @Autowired
    private EnterpriseService serviceEnt;

    public EmployeeController(EmployeeService service, EnterpriseService serviceEnt) {

        this.service = service;
        this.serviceEnt = serviceEnt;

    }

    @GetMapping("/")
    public String getEmployeeAll(Model mod, @AuthenticationPrincipal OidcUser principal) {
        
        mod.addAttribute("employees", service.findEmployeeAll());
        
        if(principal != null) {
            
            Employee employeeDB = service.findEmployeeByEmail(principal.getEmail());
            
            if(employeeDB != null) {
                
                if(employeeDB.getRole().name() == "Admin") {
                    
                    mod.addAttribute("employeeDB", employeeDB);
                    
                }
                
                return "employee";
                
            } else {
                
                return "redirect:/";
                
            }
            
        }

        return "redirect:/";

    }

    @PostMapping("/")
    public String createEmployee(@Valid @ModelAttribute Employee employee, BindingResult resultV) {
        
        if(resultV.hasErrors()) {
            
            return "newEmployee";
            
        } else {
            
            service.createEmployee(employee);
            
            return "redirect:/employees/";
            
        }
        
    }

    @GetMapping("/{id}")
    public String getEmployee(Model mod, @PathVariable("id") Long id, @AuthenticationPrincipal OidcUser principal) {
        
        mod.addAttribute("employee", service.findEmployeeById(id));
        mod.addAttribute("enterprises", serviceEnt.findEnterpriseAll());
        
        if(principal != null) {
            
            Employee employeeDB = service.findEmployeeByEmail(principal.getEmail());
            
            if(employeeDB != null) {
                
                if(employeeDB.getRole().name() == "Admin") {
                    
                    mod.addAttribute("employeeDB", employeeDB);
                    
                }
                
                return "actEmployee";
                
            } else {
                
                return "redirect:/";
                
            }
            
        }

        return "redirect:/";

    }

    @PatchMapping("/{id}")
    public String updateEmployee(@Valid @ModelAttribute Employee employee, BindingResult resultV, @PathVariable("id") Long id) {
        
        if(resultV.hasErrors()) {
            
            return "actEmployee";
            
        } else {
            
            service.updateEmployee(id, employee);
            
            return "redirect:/employees/";
            
        }

    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) {

        service.deleteEmployee(id);
        
        return "redirect:/employees/";

    }

}