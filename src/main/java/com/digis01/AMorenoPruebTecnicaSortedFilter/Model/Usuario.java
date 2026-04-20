package com.digis01.AMorenoPruebTecnicaSortedFilter.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Usuario {
    private UUID id;
    private String email;
    private String name;
    @Pattern(regexp = "^(\\+\\d{1,3}\\s?)?[0-9]{10}$", message = "Este formato de numero no es valido")
    private String phone;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Pattern(regexp = "^[A-Z]{4}\\d{6}[A-Z0-9]{3}$", message = "No cumple con la estructura de un RFC")
    private String taxId;
    private LocalDateTime createdAt;
    private List<Direccion> addresses;
    
    public Usuario(){
        
    }
    
    public Usuario(UUID id, String email, String name, String phone, String password, String taxId, List<Direccion> addresses){
        this.id= id;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.taxId = taxId;
        this.addresses = addresses;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Direccion> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Direccion> addresses) {
        this.addresses = addresses;
    }
}
