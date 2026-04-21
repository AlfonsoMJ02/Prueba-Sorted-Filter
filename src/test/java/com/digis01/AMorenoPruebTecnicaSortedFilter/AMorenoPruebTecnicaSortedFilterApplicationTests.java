package com.digis01.AMorenoPruebTecnicaSortedFilter;

import com.digis01.AMorenoPruebTecnicaSortedFilter.Model.Direccion;
import com.digis01.AMorenoPruebTecnicaSortedFilter.Model.Usuario;
import com.digis01.AMorenoPruebTecnicaSortedFilter.Service.UsuarioService;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AMorenoPruebTecnicaSortedFilterApplicationTests {

    @Autowired
    public UsuarioService service;

    @Test
    void GetAll() {
        List<Usuario> usuariosMemoria = service.obtenerUsuario();
        assertNotNull(usuariosMemoria);
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
        
        List<Usuario> usuariosMemoria = service.obtenerUsuario();
        int tamaño = usuariosMemoria.size();
        System.out.println("Tamaño de lista -> " + tamaño);
    }

    @Test
    void Update() {
        List<Usuario> usuariosMemoria = service.obtenerUsuario();
        assertNotNull(usuariosMemoria);

        UUID uuid = usuariosMemoria.get(0).getId();
        Usuario usuario = new Usuario();
        String name = usuario.getName();
        System.out.println("UUID a actualizar -> " + uuid);
        System.out.println("Nombre a actualizar -> " + name);

        usuario.setName("Alfonso");

        service.updateUsuario(uuid, usuario);

        String newName = usuario.getName();
        System.out.println("Nuevo nombre -> " + newName);
    }

    @Test
    void Delete() {
        List<Usuario> usuariosMemoria = service.obtenerUsuario();
        assertFalse(usuariosMemoria.isEmpty(), "La lista no tienen ningun usuario");

        UUID uuidEliminar = usuariosMemoria.get(0).getId();
        int tamañoLista = usuariosMemoria.size();

        service.deleteUsuario(uuidEliminar);

        List<Usuario> usuariosMemoriaDespues = service.obtenerUsuario();
        assertEquals(tamañoLista - 1, usuariosMemoriaDespues.size(), "El tamaño de la lista no se redujo");

    }
}
