package conociendocartagena.backend_conociendocartagena.controller;

import java.util.List; // Necesario para la respuesta del servicio

import org.springframework.beans.factory.annotation.Autowired; // Necesario para inyectar el servicio
import org.springframework.http.HttpStatus; // Para respuestas HTTP más específicas
import org.springframework.http.ResponseEntity; // Para respuestas HTTP más específicas
import org.springframework.web.bind.annotation.DeleteMapping; // Usar @DeleteMapping
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping; // Usar @PostMapping
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import conociendocartagena.backend_conociendocartagena.DTOs.ResponseDtos;
// Modelos
import conociendocartagena.backend_conociendocartagena.models.Usuario;
import conociendocartagena.backend_conociendocartagena.servicios.UsuarioService; // ¡Importar el UsuarioService!

// Librerias Sawgger
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Permite gestionar los usuarios del sistema") // Título
public class UsuarioController {

    // Inyectar el servicio de usuarios
    @Autowired
    private UsuarioService usuarioService;

    // Endpoint para la creación de usuarios
    @PostMapping("/crear") // Uso @PostMapping más conciso
    @Operation(summary = "Crear usuario", description = "Crear un nuevo usuario en el sistema")
    public ResponseEntity<ResponseDtos> crear(@RequestBody Usuario usuario) {
        try {
            Usuario nuevoUsuario = usuarioService.crear(usuario); // Llamar al servicio
            // Devolver 201 Created si el usuario se creó exitosamente
            return new ResponseEntity<>(new ResponseDtos("success", "Usuario creado exitosamente", nuevoUsuario), HttpStatus.CREATED);
        } catch (Exception e) {
            // Capturar cualquier excepción inesperada
            // Devolver 500 Internal Server Error para problemas del servidor
            return new ResponseEntity<>(new ResponseDtos("error", "Error interno del servidor al crear el usuario: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para la lista de usuarios
    @GetMapping("/listar") // Uso @GetMapping más conciso
    @Operation(summary = "Listar usuarios", description = "Listar usuarios en el sistema")
    public ResponseEntity<ResponseDtos> listar() {
        try {
            List<Usuario> usuarios = usuarioService.listar(); // Llamar al servicio
            // Devolver 200 OK con la lista de usuarios
            return new ResponseEntity<>(new ResponseDtos("success", "Listado de usuarios", usuarios), HttpStatus.OK);
        } catch (Exception e) {
            // Manejo de errores genérico para la lista
            return new ResponseEntity<>(new ResponseDtos("error", "Error al listar usuarios: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para la consulta de usuarios por ID
    @GetMapping("/consulta/{id}")
    @Operation(summary = "Consultar usuarios por ID", description = "Consultar usuarios por su ID en el sistema")
    public ResponseEntity<ResponseDtos> consulta(@PathVariable int id) {
        try {
            // Llamar al servicio para consultar el usuario por ID
            return usuarioService.consultar(id)
                // Si el usuario se encuentra, devolver 200 OK
                .map(usuario -> new ResponseEntity<>(new ResponseDtos("success", "Usuario Encontrado", usuario), HttpStatus.OK))
                // Si el usuario no se encuentra, devolver 404 Not Found
                .orElse(new ResponseEntity<>(new ResponseDtos("Warning", "Usuario no Existe", null), HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            // Manejo de errores genérico para la consulta
            return new ResponseEntity<>(new ResponseDtos("error", "Error al consultar usuario: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para eliminar usuario
    @DeleteMapping("/eliminar/{id}") // Uso @DeleteMapping más conciso
    @Operation(summary = "Eliminar usuarios con ID", description = "Eliminar usuarios con ID en el sistema")
    public ResponseEntity<ResponseDtos> eliminar(@PathVariable int id) {
        try {
            // Llamar al servicio para eliminar el usuario
            if (usuarioService.eliminar(id)) {
                // Si se eliminó correctamente, devolver 200 OK
                return new ResponseEntity<>(new ResponseDtos("success", "Usuario Eliminado", null), HttpStatus.OK);
            } else {
                // Si el usuario no se encontró, devolver 404 Not Found
                return new ResponseEntity<>(new ResponseDtos("Warning", "Usuario no encontrado para eliminar", null), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Manejo de errores genérico para la eliminación
            return new ResponseEntity<>(new ResponseDtos("error", "Error al eliminar usuario: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para actualizar usuario
    @PutMapping("/actualiza/{id}")
    @Operation(summary = "Actualiza usuarios con ID", description = "Actualiza usuarios con ID en el sistema")
    public ResponseEntity<ResponseDtos> actualizar(@PathVariable int id, @RequestBody Usuario user) { // Usar Usuario como DTO de entrada (o un DTO específico si lo creas)
        try {
            Usuario usuarioActualizado = usuarioService.actualizar(id, user); // Llamar al servicio
            // Si se actualizó correctamente, devolver 200 OK
            return new ResponseEntity<>(new ResponseDtos("success", "Usuario Actualizado", usuarioActualizado), HttpStatus.OK);
        } catch (RuntimeException e) {
            // Capturar excepciones específicas del servicio (ej. usuario no encontrado)
            // Se puede refinar el mensaje para dar un HttpStatus más específico
            if (e.getMessage().contains("no encontrado")) { // Simple check para ejemplo
                 return new ResponseEntity<>(new ResponseDtos("Warning", e.getMessage(), null), HttpStatus.NOT_FOUND);
            }
            // Para otros errores de validación o negocio durante la actualización
            return new ResponseEntity<>(new ResponseDtos("error", e.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Manejo de errores genérico para la actualización
            return new ResponseEntity<>(new ResponseDtos("error", "Error interno del servidor al actualizar el usuario: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}