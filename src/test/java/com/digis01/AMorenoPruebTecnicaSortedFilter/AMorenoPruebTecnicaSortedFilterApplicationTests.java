package com.digis01.AMorenoPruebTecnicaSortedFilter;

import com.digis01.AMorenoPruebTecnicaSortedFilter.Model.Direccion;
import com.digis01.AMorenoPruebTecnicaSortedFilter.Model.Usuario;
import com.digis01.AMorenoPruebTecnicaSortedFilter.Service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AMorenoPruebTecnicaSortedFilterApplicationTests {

    @Autowired
    public UsuarioService service;
    
        @Test
    void GetAll(){
        service.obtenerUsuario();
    }
    
    @Test
    void GetAdd() {
        Usuario usuario = new Usuario();
        usuario.setEmail("alfonso@gmail.com");
        usuario.setName("Alfonso");
        usuario.setPhone("7761140206");
        usuario.setPassword("Alfonso123");
        usuario.setTaxId("ALFA020322EW1");
        
        Direccion direccion = new Direccion();
        direccion.setId(4);
        direccion.setName("México");
        direccion.setStreet("México");
        direccion.setCountryCode("MX");
        
        service.Add(usuario);
    }
    
    @Test
    void Update(){
        
    }
    
    @Test
    void Delete(){
        
    }

}
