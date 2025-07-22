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
import conociendocartagena.backend_conociendocartagena.models.Tour;
import conociendocartagena.backend_conociendocartagena.servicios.TourService; // ¡Importar el TourService!

// Librerias Swagger
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/tours")
@Tag(name = "Tours", description = "Permite la gestión de los Tours")
public class TourController {

    // Inyectar el servicio de tours
    @Autowired
    private TourService tourService;

    // Endpoint de creacion de tours
    @PostMapping("/crear") // Uso @PostMapping más conciso
    @Operation(summary = "Crear Tour", description = "Crear un nuevo Tour en el sistema")
    public ResponseEntity<ResponseDtos> crear(@RequestBody Tour tour) {
        try {
            Tour nuevoTour = tourService.crear(tour); // Llamar al servicio
            // Devolver 201 Created si el tour se creó exitosamente
            return new ResponseEntity<>(new ResponseDtos("success", "Tour creado exitosamente", nuevoTour), HttpStatus.CREATED);
        } catch (Exception e) {
            // Capturar cualquier excepción inesperada
            // Devolver 500 Internal Server Error para problemas del servidor
            return new ResponseEntity<>(new ResponseDtos("error", "Error interno del servidor al crear el tour: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint de lista de tours
    @GetMapping("/listar") // Uso @GetMapping más conciso
    @Operation(summary = "Listar Tours", description = "Listar Tours en el sistema")
    public ResponseEntity<ResponseDtos> listar() {
        try {
            List<Tour> tours = tourService.listar(); // Llamar al servicio
            // Devolver 200 OK con la lista de tours
            return new ResponseEntity<>(new ResponseDtos("success", "Listado de Tours", tours), HttpStatus.OK);
        } catch (Exception e) {
            // Manejo de errores genérico para la lista
            return new ResponseEntity<>(new ResponseDtos("error", "Error al listar tours: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint de Consulta de tour por ID
    @GetMapping("/consulta/{idTour}")
    @Operation(summary = "Consultar tour por ID", description = "Consultar tour por su ID en el sistema")
    public ResponseEntity<ResponseDtos> consulta(@PathVariable int idTour) {
        try {
            // Llamar al servicio para consultar el tour por ID
            return tourService.consultar(idTour)
                // Si el tour se encuentra, devolver 200 OK
                .map(tour -> new ResponseEntity<>(new ResponseDtos("success", "Tour Encontrado", tour), HttpStatus.OK))
                // Si el tour no se encuentra, devolver 404 Not Found
                .orElse(new ResponseEntity<>(new ResponseDtos("Warning", "Tour no Existe", null), HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            // Manejo de errores genérico para la consulta
            return new ResponseEntity<>(new ResponseDtos("error", "Error al consultar tour: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint eliminar tour
    @DeleteMapping("/eliminar/{idTour}") // Uso @DeleteMapping más conciso
    @Operation(summary = "Eliminar tour con ID", description = "Eliminar tour con ID en el sistema")
    public ResponseEntity<ResponseDtos> eliminar(@PathVariable int idTour) {
        try {
            // Llamar al servicio para eliminar el tour
            if (tourService.eliminar(idTour)) {
                // Si se eliminó correctamente, devolver 200 OK
                return new ResponseEntity<>(new ResponseDtos("success", "Tour Eliminado", null), HttpStatus.OK);
            } else {
                // Si el tour no se encontró, devolver 404 Not Found
                return new ResponseEntity<>(new ResponseDtos("Warning", "Tour no encontrado para eliminar", null), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Manejo de errores genérico para la eliminación
            return new ResponseEntity<>(new ResponseDtos("error", "Error al eliminar tour: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint actualizar tour
    @PutMapping("/actualiza/{idTour}")
    @Operation(summary = "Actualiza tour con ID", description = "Actualiza tour con ID en el sistema")
    public ResponseEntity<ResponseDtos> actualizar(@PathVariable int idTour, @RequestBody Tour tour) { // Usar Tour como DTO de entrada (o un DTO específico si lo creas)
        try {
            Tour tourActualizado = tourService.actualizar(idTour, tour); // Llamar al servicio
            // Si se actualizó correctamente, devolver 200 OK
            return new ResponseEntity<>(new ResponseDtos("success", "Tour Actualizado", tourActualizado), HttpStatus.OK);
        } catch (RuntimeException e) {
            // Capturar excepciones específicas del servicio (ej. tour no encontrado)
            // Se puede refinar el mensaje para dar un HttpStatus más específico
            if (e.getMessage().contains("no encontrado")) { // Simple check para ejemplo
                 return new ResponseEntity<>(new ResponseDtos("Warning", e.getMessage(), null), HttpStatus.NOT_FOUND);
            }
            // Para otros errores de validación o negocio durante la actualización
            return new ResponseEntity<>(new ResponseDtos("error", e.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Manejo de errores genérico para la actualización
            return new ResponseEntity<>(new ResponseDtos("error", "Error interno del servidor al actualizar el tour: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
