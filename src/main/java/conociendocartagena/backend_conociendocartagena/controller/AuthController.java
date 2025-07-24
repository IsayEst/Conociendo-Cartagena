package conociendocartagena.backend_conociendocartagena.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import conociendocartagena.backend_conociendocartagena.DTOs.ResponseDtos;
import conociendocartagena.backend_conociendocartagena.models.Usuario;
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

    @RequestMapping(path = "login", method=RequestMethod.POST)
    @Operation(summary = "Autenticación de usuario", description = "Verifica las credenciales del usuario y devuelve un mensaje de éxito o error.")
    public ResponseDtos login(@RequestParam String username, @RequestParam String password) {
        boolean status = false;// Inicialmente asumimos que las credenciales son incorrectas
        // Itera sobre la lista de todos los usuarios
        for(Usuario usuario : usuarioService.listar()) {
            // usuario.getPassword() es el hash BCrypt almacenado en la BD
            if(usuario.getUsername().equals(username)) {
                // Verifica si la contraseña coincide
               if (passwordEncoder.matches(password, usuario.getPassword())) {
                   status = true;// Si coinciden las credenciales es true
                   break;// Salir del bucle, ya encontramos el usuario y la contraseña correcta
               }
            }
            
        }
        return new ResponseDtos(status ? "success" : "error", status ? "Usuario autenticado correctamente" : "Credenciales incorrectas", status);
    }
    
    @RequestMapping(path = "logout", method=RequestMethod.POST)
    @Operation(summary = "Cierre de sesión", description = "Cierra la sesión del usuario autenticado.")
    public ResponseDtos logout(@RequestParam String username) {
        // Aquí podrías implementar la lógica para cerrar la sesión del usuario
        // Por ejemplo, invalidar el token JWT o eliminar la sesión del usuario
        return new ResponseDtos("success", "Usuario cerrado sesión correctamente", true);
    }
    @RequestMapping(path = "register", method=RequestMethod.POST)
    @Operation(summary = "Registro de usuario", description = "Registra un nuevo usuario en el sistema.")
    public ResponseDtos register(@RequestParam String username, @RequestParam String password) {
        // Aquí podrías implementar la lógica para registrar un nuevo usuario
        // Por ejemplo, verificar si el usuario ya existe y luego guardar el nuevo usuario
        return new ResponseDtos("success", "Usuario registrado correctamente", true);
    }
    

    
}
