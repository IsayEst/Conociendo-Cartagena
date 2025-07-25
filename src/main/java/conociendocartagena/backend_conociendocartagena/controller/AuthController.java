package conociendocartagena.backend_conociendocartagena.controller;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import conociendocartagena.backend_conociendocartagena.DTOs.ResponseDtos;
import conociendocartagena.backend_conociendocartagena.config.JwtUtil;
import conociendocartagena.backend_conociendocartagena.models.RefreshToken;
import conociendocartagena.backend_conociendocartagena.models.Usuario;
import conociendocartagena.backend_conociendocartagena.servicios.RefreshTokenService;
import conociendocartagena.backend_conociendocartagena.servicios.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/auth")
@Tag(name = "autenticacion", description = "Verificacion de usuarios")
public class AuthController {

      // ¡Inyecta PasswordEncoder! Esto es esencial.
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtUtil jwt;

    @RequestMapping(path = "login", method=RequestMethod.POST)
    @Operation(summary = "Autenticación de usuario", description = "Verifica las credenciales del usuario y devuelve un mensaje de éxito o error.")
    public ResponseDtos login(@RequestParam String username, @RequestParam String password) {
        boolean status = false;// Inicialmente asumimos que las credenciales son incorrectas
        String token = "";// Inicialmente no hay token
        String refresh = "";// Inicialmente no hay token de refresco
        // Itera sobre la lista de todos los usuarios
        for(Usuario usuario : usuarioService.listar()) {
            // usuario.getPassword() es el hash BCrypt almacenado en la BD
            if(usuario.getUsername().equals(username)) {
                // Verifica si la contraseña coincide
               if (passwordEncoder.matches(password, usuario.getPassword())) {
                   status = true;// Si coinciden las credenciales es true
                   token = jwt.generateToken(usuario.getUsername(), usuario.getNombre(), usuario.getApellido());// Genera el token JWT
                   refresh = refreshTokenService.createRefreshToken(usuario.getUsername()).getToken(); // Genera el token de refresco
                   break;// Salir del bucle, ya encontramos el usuario y la contraseña correcta
               }
            }
        }
        Map<String, String> tokens = new HashMap<>();
        tokens.put("token", token);
        tokens.put("refreshToken", refresh);

        return new ResponseDtos(status ? "success" : "error", status ? "Usuario autenticado correctamente" : "Credenciales incorrectas", tokens);
    }
    
    @RequestMapping(path = "logout", method=RequestMethod.POST)
    @Operation(summary = "Cierre de sesión", description = "Cierra la sesión del usuario autenticado.")
    public ResponseDtos logout(@RequestParam String username) {
        try {
            // CORRECCIÓN AQUÍ: Usar deleteByUsername
            refreshTokenService.deleteByUsuario(username); 
            return new ResponseDtos("success", "Usuario cerrado sesión correctamente. Refresh Token(s) invalidado(s).", true);
        } catch (Exception e) {
            System.err.println("Error durante el logout para usuario " + username + ": " + e.getMessage());
            return new ResponseDtos("error", "Error al cerrar sesión: " + e.getMessage(), false);
        } 
    }
     // --- Endpoint 'refresh' corregido ---
@RequestMapping(path = "refresh", method=RequestMethod.POST) // O @PostMapping("refresh")
@Operation(summary = "Refrescar token", description = "Obtiene un nuevo Access Token usando un Refresh Token válido.")
// Si este endpoint no requiere Access Token previo, asegúrate que esté en permitAll() en SecurityConfig
public ResponseDtos refresh(@RequestParam String refreshToken) { // O @RequestBody RefreshTokenRequest request
    try {
        // Verifica el Refresh Token y obtiene el usuario asociado
        Optional<RefreshToken> refreshTokenOptional = refreshTokenService.findByToken(refreshToken);
        if (refreshTokenOptional.isEmpty()) {
            return new ResponseDtos("error", "Refresh token no encontrado", null);
        }
        RefreshToken refreshTokenObj = refreshTokenService.verifyExpiration(refreshTokenOptional.get());
        Usuario usuario = refreshTokenObj.getUsuario();
        
        // Genera un nuevo Access Token
        String newAccessToken = jwt.generateToken(usuario.getUsername(), usuario.getNombre(), usuario.getApellido());
        
        Map<String, String> responseData = new HashMap<>();
                    responseData.put("accessToken", newAccessToken);


        // Devuelve el nuevo Access Token
        return new ResponseDtos("success", "Access Token renovado correctamente", responseData);
    } catch (RuntimeException e) {
        return new ResponseDtos("error", "Error al refrescar token: " + e.getMessage(), null);
    }
    }

}  

    

