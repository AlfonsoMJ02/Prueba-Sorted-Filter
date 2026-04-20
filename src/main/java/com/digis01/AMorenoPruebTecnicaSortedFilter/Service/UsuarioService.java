package com.digis01.AMorenoPruebTecnicaSortedFilter.Service;

import com.digis01.AMorenoPruebTecnicaSortedFilter.Model.Direccion;
import com.digis01.AMorenoPruebTecnicaSortedFilter.Model.Usuario;
import com.digis01.AMorenoPruebTecnicaSortedFilter.Util.AES256;
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private List<Usuario> usuarios = new ArrayList<>();

    public List<Usuario> obtenerUsuario() {
        return usuarios;
    }

    @PostConstruct
    public void UsuariosEnMemoria() {
        ZoneId zone = ZoneId.of("Indian/Antananarivo");

        Usuario usuario1 = new Usuario();
        usuario1.setId(UUID.randomUUID());
        usuario1.setEmail("juan@gmail.com");
        usuario1.setName("Juan");
        usuario1.setPhone("52+5566998877");
        usuario1.setPassword(AES256.encrypt("Juan123$"));
        usuario1.setTaxId("JUAN020322NV9");
        usuario1.setCreatedAt(LocalDateTime.now(zone));
        usuario1.setAddresses(List.of(
                new Direccion(1, "Hawai", "Hawai", "UX")
        ));
        usuarios.add(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setId(UUID.randomUUID());
        usuario2.setEmail("carlos@outlook.com");
        usuario2.setName("Carlos");
        usuario2.setPhone("22+9988776655");
        usuario2.setPassword(AES256.encrypt("Carlos123$"));
        usuario2.setTaxId("CARL020322NV9");
        usuario2.setCreatedAt(LocalDateTime.now(zone));
        usuario2.setAddresses(List.of(
                new Direccion(2, "Pachuca", "Pachuca", "MX")
        ));
        usuarios.add(usuario2);

        Usuario usuario3 = new Usuario();
        usuario3.setId(UUID.randomUUID());
        usuario3.setEmail("pedro@hotmail.com");
        usuario3.setName("Pedro");
        usuario3.setPhone("123+2233665544");
        usuario3.setPassword(AES256.encrypt("Pedro123$"));
        usuario3.setTaxId("PEDR020322NV9");
        usuario3.setCreatedAt(LocalDateTime.now(zone));
        usuario3.setAddresses(List.of(
                new Direccion(3, "Puebla", "Puebla", "MX")
        ));
        usuarios.add(usuario3);
    }

    public List<Usuario> getUsuario(String sortedBy, String filter) {
        List<Usuario> resultado = new ArrayList<>(usuarios);

        if (filter != null && !filter.isEmpty()) {
            resultado = filtro(resultado, filter);
        }

        if (sortedBy != null && !sortedBy.isEmpty()) {
            return usuarios.stream().sorted(getComparator(sortedBy.toLowerCase())).toList();
        }
        return resultado;
    }

    private List<Usuario> filtro(List<Usuario> lista, String filter) {
        String[] parseo = filter.split(" ");

        if (parseo.length != 3) {
            throw new RuntimeException("Formato de filtro invalido");
        }

        String field = parseo[0].toLowerCase();
        String operador = parseo[1].toLowerCase();
        String valor = parseo[2].toLowerCase();

        return lista.stream().filter(usuario -> evaluarFiltro(usuario, field, operador, valor)).toList();
    }

    private boolean evaluarFiltro(Usuario usuario, String field, String operador, String valor) {
        String campo = switch (field) {
            case "email" ->
                usuario.getEmail();
            case "name" ->
                usuario.getName();
            case "phone" ->
                usuario.getPhone();
            case "taxId" ->
                usuario.getTaxId();
            case "createdAt" ->
                usuario.getCreatedAt().toString();
            default ->
                "";
        };

        if (campo == null) {
            return false;
        }
        campo = campo.toLowerCase();
        return switch (operador) {
            case "co" ->
                campo.contains(valor);
            case "eq" ->
                campo.contains(valor);
            case "sw" ->
                campo.contains(valor);
            case "ew" ->
                campo.contains(valor);
            default ->
                false;
        };
    }

    private Comparator<Usuario> getComparator(String sortedBy) {
        return switch (sortedBy) {
            case "email" ->
                Comparator.comparing(Usuario::getEmail);
            case "name" ->
                Comparator.comparing(Usuario::getName);
            case "phone" ->
                Comparator.comparing(Usuario::getPhone);
            case "taxId" ->
                Comparator.comparing(Usuario::getTaxId);
            case "createdAt" ->
                Comparator.comparing(Usuario::getCreatedAt);
            default ->
                (usuario1, usuario2) -> 0;
        };
    }

    public boolean Add(Usuario usuario) {
        boolean existe = usuarios.stream().anyMatch(u -> u.getTaxId().equalsIgnoreCase(usuario.getTaxId()));

        if (existe) {
            throw new RuntimeException("El taxId ya existe");
        }
        usuario.setId(UUID.randomUUID());
        ZoneId zone = ZoneId.of("Indian/Antananarivo");
        usuario.setCreatedAt(LocalDateTime.now(zone));
        usuario.setPassword(AES256.encrypt(usuario.getPassword()));

        return usuarios.add(usuario);
    }

    public boolean deleteUsuario(UUID id) {
        return usuarios.removeIf(usuario -> usuario.getId().equals(id));
    }

    public boolean updateUsuario(UUID id, Usuario usuario) {
        try {
            Optional<Usuario> optional = usuarios.stream().filter(u -> u.getId().equals(id)).findFirst();

            if (optional.isEmpty()) {
                return false;
            }

            Usuario existe = optional.get();

            if (usuario.getTaxId() != null && usuarios.stream().anyMatch(u -> u.getTaxId().equalsIgnoreCase(usuario.getTaxId()) && !u.getTaxId().equals(id))) {
                return false;
            }

            if (usuario.getEmail() != null) {
                existe.setEmail(usuario.getEmail());
            }
            if (usuario.getName() != null) {
                existe.setName(usuario.getName());
            }
            if (usuario.getPhone() != null) {
                existe.setPhone(usuario.getPhone());
            }
            if (usuario.getPassword() != null) {
                existe.setPassword(AES256.encrypt(usuario.getPassword()));
            }
            if (usuario.getTaxId() != null) {
                existe.setTaxId(usuario.getTaxId());
            }
            if (usuario.getAddresses() != null) {
                existe.setAddresses(usuario.getAddresses());
            }

            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Usuario findByTaxId(String taxId) {
        return usuarios.stream().filter(u -> u.getTaxId().equalsIgnoreCase(taxId)).findFirst().orElse(null);
    }
}
