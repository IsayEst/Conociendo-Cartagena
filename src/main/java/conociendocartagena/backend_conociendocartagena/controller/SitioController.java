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
import conociendocartagena.backend_conociendocartagena.models.Sitio;
import conociendocartagena.backend_conociendocartagena.servicios.SitioService; // ¡Importar el SitioService!

// Librerias Swagger
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/sitio")
@Tag(name = "Sitios", description = "Permite la gestión de los Sitios Turísticos")
public class SitioController {

    // Inyectar el servicio de sitios
    @Autowired
    private SitioService sitioService;

    // Endpoint de creacion de Sitios
    @PostMapping("/crear") // Uso @PostMapping más conciso
    @Operation(summary = "Crear Sitio", description = "Crear un nuevo Sitio en el sistema")
    public ResponseEntity<ResponseDtos> crear(@RequestBody Sitio sitio) {
        try {
            Sitio nuevoSitio = sitioService.crear(sitio); // Llamar al servicio
            // Devolver 201 Created si el sitio se creó exitosamente
            return new ResponseEntity<>(new ResponseDtos("success", "Sitio creado exitosamente", nuevoSitio), HttpStatus.CREATED);
        } catch (Exception e) {
            // Capturar cualquier excepción inesperada
            // Devolver 500 Internal Server Error para problemas del servidor
            return new ResponseEntity<>(new ResponseDtos("error", "Error interno del servidor al crear el sitio: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint de lista de Sitios turisticos
    @GetMapping("/listar") // Uso @GetMapping más conciso
    @Operation(summary = "Listar Sitios turisticos", description = "Listar Sitios turisticos en el sistema")
    public ResponseEntity<ResponseDtos> listar() {
        try {
            List<Sitio> sitios = sitioService.listar(); // Llamar al servicio
            // Devolver 200 OK con la lista de sitios
            return new ResponseEntity<>(new ResponseDtos("success", "Listado de Sitios", sitios), HttpStatus.OK);
        } catch (Exception e) {
            // Manejo de errores genérico para la lista
            return new ResponseEntity<>(new ResponseDtos("error", "Error al listar sitios: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint de Consulta de Sitios turisticos
    @GetMapping("/consulta/{idSitio}") // Cambiado a idSitio para consistencia
    @Operation(summary = "Consultar sitio por ID", description = "Consultar sitios turisticos por su ID en el sistema")
    public ResponseEntity<ResponseDtos> consulta(@PathVariable int idSitio) { // Cambiado a idSitio
        try {
            // Llamar al servicio para consultar el sitio por ID
            return sitioService.consultar(idSitio)
                // Si el sitio se encuentra, devolver 200 OK
                .map(sitio -> new ResponseEntity<>(new ResponseDtos("success", "Sitio turistico Encontrado", sitio), HttpStatus.OK))
                // Si el sitio no se encuentra, devolver 404 Not Found
                .orElse(new ResponseEntity<>(new ResponseDtos("Warning", "Sitio turistico no Existe", null), HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            // Manejo de errores genérico para la consulta
            return new ResponseEntity<>(new ResponseDtos("error", "Error al consultar sitio: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint eliminar Sitio turistico
    @DeleteMapping("/eliminar/{idSitio}") // Cambiado a idSitio para consistencia
    @Operation(summary = "Eliminar Sitio con ID", description = "Eliminar sitios turisticos con ID en el sistema")
    public ResponseEntity<ResponseDtos> eliminar(@PathVariable int idSitio) { // Cambiado a idSitio
        try {
            // Llamar al servicio para eliminar el sitio
            if (sitioService.eliminar(idSitio)) {
                // Si se eliminó correctamente, devolver 200 OK
                return new ResponseEntity<>(new ResponseDtos("success", "Sitio turistico Eliminado", null), HttpStatus.OK);
            } else {
                // Si el sitio no se encontró, devolver 404 Not Found
                return new ResponseEntity<>(new ResponseDtos("Warning", "Sitio turistico no encontrado para eliminar", null), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Manejo de errores genérico para la eliminación
            return new ResponseEntity<>(new ResponseDtos("error", "Error al eliminar sitio: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint actualizar Sitio turistico
    @PutMapping("/actualiza/{idSitio}") // Cambiado a idSitio para consistencia
    @Operation(summary = "Actualiza Sitio con ID", description = "Actualiza Sitio turistico con ID en el sistema")
    public ResponseEntity<ResponseDtos> actualizar(@PathVariable int idSitio, @RequestBody Sitio sitio) { // Cambiado a idSitio
        try {
            Sitio sitioActualizado = sitioService.actualizar(idSitio, sitio); // Llamar al servicio
            // Si se actualizó correctamente, devolver 200 OK
            return new ResponseEntity<>(new ResponseDtos("success", "Sitio turistico Actualizado", sitioActualizado), HttpStatus.OK);
        } catch (RuntimeException e) {
            // Capturar excepciones específicas del servicio (ej. sitio no encontrado)
            // Se puede refinar el mensaje para dar un HttpStatus más específico
            if (e.getMessage().contains("no encontrado")) { // Simple check para ejemplo
                 return new ResponseEntity<>(new ResponseDtos("Warning", e.getMessage(), null), HttpStatus.NOT_FOUND);
            }
            // Para otros errores de validación o negocio durante la actualización
            return new ResponseEntity<>(new ResponseDtos("error", e.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Manejo de errores genérico para la actualización
            return new ResponseEntity<>(new ResponseDtos("error", "Error interno del servidor al actualizar el sitio: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
