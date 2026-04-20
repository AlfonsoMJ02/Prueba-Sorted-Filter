package com.digis01.AMorenoPruebTecnicaSortedFilter.Model;

public class LoginRequest {
    private String taxId;
    private String password;
    
    public String getTaxId(){
        return taxId;
    }
    
    public void setTaxId(String taxId){
        this.taxId = taxId;
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
}
