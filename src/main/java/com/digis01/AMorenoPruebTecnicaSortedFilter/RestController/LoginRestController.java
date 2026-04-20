package com.digis01.AMorenoPruebTecnicaSortedFilter.RestController;

import com.digis01.AMorenoPruebTecnicaSortedFilter.Model.LoginRequest;
import com.digis01.AMorenoPruebTecnicaSortedFilter.Service.AuthService;
import com.digis01.AMorenoPruebTecnicaSortedFilter.Util.SwaggerExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.naming.spi.DirStateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Login")
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
public class LoginRestController {
    
    @Autowired
    public AuthService auth;
    
    @Operation(summary = "Loguear usuario", description = "Permite dar acceso al sistema a un usuario que ya este agregado, usando su taxId y su contraseña")
    @ApiResponse(
            responseCode = "200",
            description = "Usuario logueado correctamente",
            content = @Content(
                    schema = @Schema(implementation = DirStateFactory.Result.class),
                    examples = @ExampleObject(value = SwaggerExamples.SUCCESS)
            )
    )
    @PostMapping()
    public ResponseEntity<String> login(@RequestBody LoginRequest request){
        String token = auth.login(request.getTaxId(), request.getPassword());
        
        if (token != null) {
            return ResponseEntity.ok(token);
        }else {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }
    }
}
