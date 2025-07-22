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

// DTOs
import conociendocartagena.backend_conociendocartagena.DTOs.ResponseDtos;
// Modelo
import conociendocartagena.backend_conociendocartagena.models.Restaurante;
import conociendocartagena.backend_conociendocartagena.servicios.RestauranteService; // ¡Importar el RestauranteService!

// Librerias Swagger
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/restaurantes")
@Tag(name = "Restaurantes", description = "Permite la gestión de restaurantes")
public class RestauranteController {

    // Inyectar el servicio de restaurantes
    @Autowired
    private RestauranteService restauranteService;

    // Endpoint de creacion de restaurantes
    @PostMapping("/crear") // Uso @PostMapping más conciso
    @Operation(summary = "Crear restaurante", description = "Crear un nuevo restaurante en el sistema")
    public ResponseEntity<ResponseDtos> crear(@RequestBody Restaurante restaurante) {
        try {
            Restaurante nuevoRestaurante = restauranteService.crear(restaurante); // Llamar al servicio
            // Devolver 201 Created si el restaurante se creó exitosamente
            return new ResponseEntity<>(new ResponseDtos("success", "Restaurante creado exitosamente", nuevoRestaurante), HttpStatus.CREATED);
        } catch (Exception e) {
            // Capturar cualquier excepción inesperada
            // Devolver 500 Internal Server Error para problemas del servidor
            return new ResponseEntity<>(new ResponseDtos("error", "Error interno del servidor al crear el restaurante: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint de lista de restaurantes
    @GetMapping("/listar") // Uso @GetMapping más conciso
    @Operation(summary = "Listar restaurante", description = "Listar restaurantes en el sistema")
    public ResponseEntity<ResponseDtos> listar() {
        try {
            List<Restaurante> restaurantes = restauranteService.listar(); // Llamar al servicio
            // Devolver 200 OK con la lista de restaurantes
            return new ResponseEntity<>(new ResponseDtos("success", "Listado de restaurantes", restaurantes), HttpStatus.OK);
        } catch (Exception e) {
            // Manejo de errores genérico para la lista
            return new ResponseEntity<>(new ResponseDtos("error", "Error al listar restaurantes: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint de Consulta de restaurante por ID
    @GetMapping("/consulta/{idRestaurante}")
    @Operation(summary = "Consultar restaurante por ID", description = "Consultar restaurante por su ID en el sistema")
    public ResponseEntity<ResponseDtos> consulta(@PathVariable int idRestaurante) {
        try {
            // Llamar al servicio para consultar el restaurante por ID
            return restauranteService.consultar(idRestaurante)
                // Si el restaurante se encuentra, devolver 200 OK
                .map(restaurante -> new ResponseEntity<>(new ResponseDtos("success", "Restaurante Encontrado", restaurante), HttpStatus.OK))
                // Si el restaurante no se encuentra, devolver 404 Not Found
                .orElse(new ResponseEntity<>(new ResponseDtos("Warning", "Restaurante no Existe", null), HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            // Manejo de errores genérico para la consulta
            return new ResponseEntity<>(new ResponseDtos("error", "Error al consultar restaurante: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint eliminar restaurante
    @DeleteMapping("/eliminar/{idRestaurante}") // Uso @DeleteMapping más conciso
    @Operation(summary = "Eliminar restaurantes con ID", description = "Eliminar restaurantes con ID en el sistema")
    public ResponseEntity<ResponseDtos> eliminar(@PathVariable int idRestaurante) {
        try {
            // Llamar al servicio para eliminar el restaurante
            if (restauranteService.eliminar(idRestaurante)) {
                // Si se eliminó correctamente, devolver 200 OK
                return new ResponseEntity<>(new ResponseDtos("success", "Restaurante Eliminado", null), HttpStatus.OK);
            } else {
                // Si el restaurante no se encontró, devolver 404 Not Found
                return new ResponseEntity<>(new ResponseDtos("Warning", "Restaurante no encontrado para eliminar", null), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Manejo de errores genérico para la eliminación
            return new ResponseEntity<>(new ResponseDtos("error", "Error al eliminar restaurante: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint actualizar restaurante
    @PutMapping("/actualiza/{idRestaurante}")
    @Operation(summary = "Actualiza restaurantes con ID", description = "Actualiza restaurantes con ID en el sistema")
    public ResponseEntity<ResponseDtos> actualizar(@PathVariable int idRestaurante, @RequestBody Restaurante restaurant) { // Usar Restaurante como DTO de entrada (o un DTO específico si lo creas)
        try {
            Restaurante restauranteActualizado = restauranteService.actualizar(idRestaurante, restaurant); // Llamar al servicio
            // Si se actualizó correctamente, devolver 200 OK
            return new ResponseEntity<>(new ResponseDtos("success", "Restaurante Actualizado", restauranteActualizado), HttpStatus.OK);
        } catch (RuntimeException e) {
            // Capturar excepciones específicas del servicio (ej. restaurante no encontrado)
            // Se puede refinar el mensaje para dar un HttpStatus más específico
            if (e.getMessage().contains("no encontrado")) { // Simple check para ejemplo
                 return new ResponseEntity<>(new ResponseDtos("Warning", e.getMessage(), null), HttpStatus.NOT_FOUND);
            }
            // Para otros errores de validación o negocio durante la actualización
            return new ResponseEntity<>(new ResponseDtos("error", e.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Manejo de errores genérico para la actualización
            return new ResponseEntity<>(new ResponseDtos("error", "Error interno del servidor al actualizar el restaurante: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}