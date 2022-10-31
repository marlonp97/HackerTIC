package com.misiontic2022.HackerTIC.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "enterprise")
public class Enterprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    @NotBlank(message = "Este campo no puede estar en blanco")
    private String name;
    
    @Column(unique = true)
    @NotBlank(message = "Este campo no puede estar en blanco")
    private String document;
    
    private String phone;
    
    private String address;
    
    @OneToMany(mappedBy = "enterprise", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Employee> employees;
    
    @OneToMany(mappedBy = "enterprise", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Transaction> transactions;
    
    private Float totalAmountTransactions;
    
    private Date createdAt;
    
    private Date updatedAt;

    public Enterprise() {
        totalAmountTransactions = 0f;
    }

    public Enterprise(Long id, String name, String document, String phone, String address, List<Employee> employees, List<Transaction> transactions, Float totalAmountTransactions, Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.document = document;
        this.phone = phone;
        this.address = address;
        this.employees = employees;
        this.transactions = transactions;
        this.totalAmountTransactions = totalAmountTransactions;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Float getTotalAmountTransactions() {
        
        for(int i = 0; i < transactions.size(); i++) {
            
            totalAmountTransactions += transactions.get(i).getAmount();
            
        }
        
        return totalAmountTransactions;
    }

    public void setTotalAmountTransactions(Float totalAmountTransactions) {
        this.totalAmountTransactions = totalAmountTransactions;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}