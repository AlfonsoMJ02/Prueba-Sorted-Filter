package com.digis01.AMorenoPruebTecnicaSortedFilter.Service;

import com.digis01.AMorenoPruebTecnicaSortedFilter.Model.Usuario;
import com.digis01.AMorenoPruebTecnicaSortedFilter.Util.AES256;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    public UsuarioService service;
    
    private Map<String, String> sesiones = new HashMap<>();

    public String login(String taxId, String password) {

        Usuario usuario = service.findByTaxId(taxId);

        if (usuario == null) return null;

        String passwordDesencriptado = AES256.decrypt(usuario.getPassword());

        if (!passwordDesencriptado.equals(password)) {
            return null;
        }

        String token = UUID.randomUUID().toString();
        sesiones.put(token, taxId);

        return token;
    }

    public boolean validarToken(String token) {
        return sesiones.containsKey(token);
    }
}