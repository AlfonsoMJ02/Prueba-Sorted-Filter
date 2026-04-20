package com.digis01.AMorenoPruebTecnicaSortedFilter.RestController;

import com.digis01.AMorenoPruebTecnicaSortedFilter.Model.Usuario;
import com.digis01.AMorenoPruebTecnicaSortedFilter.Service.AuthService;
import com.digis01.AMorenoPruebTecnicaSortedFilter.Service.UsuarioService;
import com.digis01.AMorenoPruebTecnicaSortedFilter.Util.SwaggerExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.UUID;
import javax.naming.spi.DirStateFactory.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Usuario")
@ApiResponses(value = {
    @ApiResponse(
            responseCode = "400",
            description = "Solicitud incorrecta",
            content = @Content(
                    examples = @ExampleObject(value = SwaggerExamples.BAD_REQUEST)
            )
    ),

    @ApiResponse(
            responseCode = "404",
            description = "Recurso no encontrado",
            content = @Content(
                    examples = @ExampleObject(value = SwaggerExamples.NOT_FOUND)
            )
    ),

    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(
                    examples = @ExampleObject(value = SwaggerExamples.INTERNAL_ERROR)
            )
    )
})
public class UsuarioRestController {

    @Autowired
    public UsuarioService service;

    @Autowired
    public AuthService authService;

    private boolean validar(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return false;
        }
        String token = authHeader.replace("Bearer ", "");
        return authService.validarToken(token);
    }

    @Operation(summary = "Mostrar usuarios", description = "Permite mostrar todos los usuarios")
    @ApiResponse(
            responseCode = "200",
            description = "Usuarios mostrados correctamente",
            content = @Content(
                    schema = @Schema(implementation = Result.class),
                    examples = @ExampleObject(value = SwaggerExamples.SUCCESS)
            )
    )
    @GetMapping("/GetAll")
    public ResponseEntity<?> GetAll(@RequestHeader("Authorization") String authHeader) {

        if (!validar(authHeader)) {
            return ResponseEntity.status(401).body("No autorizado");
        }

        return ResponseEntity.ok(service.obtenerUsuario());
    }

    @Operation(summary = "Agregar un usuario", description = "Permite añadir un nuevo usuario")
    @ApiResponse(
            responseCode = "200",
            description = "Usuario añadido correctamente",
            content = @Content(
                    schema = @Schema(implementation = Result.class),
                    examples = @ExampleObject(value = SwaggerExamples.SUCCESS)
            )
    )
    @PostMapping("/Add")
    public ResponseEntity<?> Add(@RequestHeader("Authorization") String authHeader, @RequestBody Usuario usuario) {

        if (!validar(authHeader)) {
            return ResponseEntity.status(401).body("No autorizado");
        }

        boolean nuevo = service.Add(usuario);

        if (nuevo) {
            return ResponseEntity.ok("Usuario insertado correctamente");
        } else {
            return ResponseEntity.status(400).body("No se pudo insertar el usuario");
        }
    }
    
    @Operation(summary = "Buscar o ordenar usuarios", description = "Permite buscar a usarios con el metodo Filter SCIM ejemplo: "
            + "/Usuario?filter=[email|id|name|phone|tax_id|created_at]+[co|eq|sw|ew]+[valor] "
            + "Y ordenar alfabeticamente con SortedBy ejemplo: /Usuario?sortedBy=[email|id|name|phone|tax_id|created_at]")
    @ApiResponse(
            responseCode = "200",
            description = "Busqueda realizada correctamente",
            content = @Content(
                    schema = @Schema(implementation = Result.class),
                    examples = @ExampleObject(value = SwaggerExamples.SUCCESS)
            )
    )
    @GetMapping()
    public ResponseEntity<?> GetUsuario(@RequestHeader("Authorization") String authHeader, @RequestParam(required = false) String sortedBy, @RequestParam(required = false) String filter) {

        if (!validar(authHeader)) {
            return ResponseEntity.status(401).body("No autorizado");
        }

        return ResponseEntity.ok(service.getUsuario(sortedBy, filter));
    }

    @Operation(summary = "Eliminar un usuario", description = "Permite eliminar un usuario")
    @ApiResponse(
            responseCode = "200",
            description = "Usuario eliminado correctamente",
            content = @Content(
                    schema = @Schema(implementation = Result.class),
                    examples = @ExampleObject(value = SwaggerExamples.SUCCESS)
            )
    )
    @DeleteMapping("Delete/{id}")
    public ResponseEntity<?> DeleteUsuario(@RequestHeader("Authorization") String authHeader, @PathVariable UUID id) {

        if (!validar(authHeader)) {
            return ResponseEntity.status(401).body("No autorizado");
        }

        boolean eliminar = service.deleteUsuario(id);

        if (eliminar) {
            return ResponseEntity.ok("Usuario eliminado con exito");
        } else {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
    }
    
    @PatchMapping("/Update/{id}")
    public ResponseEntity<?> UpdateUsuario(@RequestHeader("Authorization") String authHeader, @PathVariable UUID id, @Valid @RequestBody Usuario usuario){
        if (!validar(authHeader)) {
            return ResponseEntity.status(401).body("No autorizado");
        }
        
        boolean actualizar = service.updateUsuario(id, usuario);
        
        if (actualizar) {
            return ResponseEntity.ok("Usuario actualizado correctamente");
        } else {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
    }
}
