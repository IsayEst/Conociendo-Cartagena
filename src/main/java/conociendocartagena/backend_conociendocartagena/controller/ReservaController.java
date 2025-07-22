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

import conociendocartagena.backend_conociendocartagena.DTOs.ReservaCreationDto;
import conociendocartagena.backend_conociendocartagena.DTOs.ReservaUpdateDto; // Importar el DTO de actualización
import conociendocartagena.backend_conociendocartagena.DTOs.ResponseDtos;
import conociendocartagena.backend_conociendocartagena.models.Reserva;
import conociendocartagena.backend_conociendocartagena.servicios.ReservaService; // ¡Importar el servicio!

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/reserva")
@Tag(name = "Reservas", description = "Permite la gestion de reservas")
public class ReservaController {

    // Inyectar el servicio de reservas
    @Autowired
    private ReservaService reservaService;

    // Endpoint de creacion de reserva
    @PostMapping("/crear") // Uso @PostMapping más conciso
    @Operation(summary = "Crear reserva", description = "Crear una nueva reserva en el sistema")
    public ResponseEntity<ResponseDtos> crear(@RequestBody ReservaCreationDto reservaDto) { // Cambiado a reservaDto
        try {
            Reserva nuevaReserva = reservaService.crear(reservaDto); // Llamar al servicio
            // Devolver 201 Created si la reserva se creó exitosamente
            return new ResponseEntity<>(new ResponseDtos("success", "Reserva creada exitosamente", nuevaReserva), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Capturar excepciones lanzadas por el servicio (ej. usuario/recurso no encontrado, capacidad llena)
            // Devolver 400 Bad Request para errores de validación o negocio
            return new ResponseEntity<>(new ResponseDtos("error", e.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Capturar cualquier otra excepción inesperada
            // Devolver 500 Internal Server Error para problemas del servidor
            return new ResponseEntity<>(new ResponseDtos("error", "Error interno del servidor al crear la reserva: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint de lista de reserva
    @GetMapping("/listar") // Uso @GetMapping más conciso
    @Operation(summary = "Listar reserva", description = "Listar reserva en el sistema")
    public ResponseEntity<ResponseDtos> listar() {
        try {
            List<Reserva> reservas = reservaService.listar(); // Llamar al servicio
            // Devolver 200 OK con la lista de reservas
            return new ResponseEntity<>(new ResponseDtos("success", "Listado de reservas", reservas), HttpStatus.OK);
        } catch (Exception e) {
            // Manejo de errores genérico para la lista
            return new ResponseEntity<>(new ResponseDtos("error", "Error al listar reservas: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint de Consulta de registro por ID
    @GetMapping("/consulta/{id}")
    @Operation(summary = "Consultar registros por ID", description = "Consultar reserva por su ID en el sistema")
    public ResponseEntity<ResponseDtos> consulta(@PathVariable int id) {
        try {
            // Llamar al servicio para consultar la reserva por ID
            return reservaService.consultar(id)
                // Si la reserva se encuentra, devolver 200 OK
                .map(reserva -> new ResponseEntity<>(new ResponseDtos("success", "Reserva Encontrada", reserva), HttpStatus.OK))
                // Si la reserva no se encuentra, devolver 404 Not Found
                .orElse(new ResponseEntity<>(new ResponseDtos("Warning", "Reserva no Existe", null), HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            // Manejo de errores genérico para la consulta
            return new ResponseEntity<>(new ResponseDtos("error", "Error al consultar reserva: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint eliminar reserva
    @DeleteMapping("/eliminar/{id}") // Uso @DeleteMapping más conciso
    @Operation(summary = "Eliminar reserva con ID", description = "Eliminar reserva con ID en el sistema")
    public ResponseEntity<ResponseDtos> eliminar(@PathVariable int id) {
        try {
            // Llamar al servicio para eliminar la reserva
            if (reservaService.eliminar(id)) {
                // Si se eliminó correctamente, devolver 200 OK
                return new ResponseEntity<>(new ResponseDtos("success", "Reserva Eliminada", null), HttpStatus.OK);
            } else {
                // Si la reserva no se encontró, devolver 404 Not Found
                return new ResponseEntity<>(new ResponseDtos("Warning", "Reserva no encontrada para eliminar", null), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Manejo de errores genérico para la eliminación
            return new ResponseEntity<>(new ResponseDtos("error", "Error al eliminar reserva: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint actualizar reserva
    @PutMapping("/actualiza/{id}")
    @Operation(summary = "Actualiza reserva con ID", description = "Actualiza reserva con ID en el sistema")
    public ResponseEntity<ResponseDtos> actualizar(@PathVariable int id, @RequestBody ReservaUpdateDto reservaDto) { // Usar ReservaUpdateDto
        try {
            // Llamar al servicio para actualizar la reserva
            Reserva reservaActualizada = reservaService.actualizar(id, reservaDto);
            // Si se actualizó correctamente, devolver 200 OK
            return new ResponseEntity<>(new ResponseDtos("success", "Reserva Actualizada", reservaActualizada), HttpStatus.OK);
        } catch (RuntimeException e) {
            // Capturar excepciones específicas del servicio (ej. reserva no encontrada)
            // Se puede refinar el mensaje para dar un HttpStatus más específico
            if (e.getMessage().contains("no encontrada")) {
                 return new ResponseEntity<>(new ResponseDtos("Warning", e.getMessage(), null), HttpStatus.NOT_FOUND);
            }
            // Para otros errores de validación o negocio durante la actualización
            return new ResponseEntity<>(new ResponseDtos("error", e.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Manejo de errores genérico para la actualización
            return new ResponseEntity<>(new ResponseDtos("error", "Error interno del servidor al actualizar la reserva: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}