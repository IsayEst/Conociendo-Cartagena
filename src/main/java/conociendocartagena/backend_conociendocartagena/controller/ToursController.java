package conociendocartagena.backend_conociendocartagena.controller;

import java.util.ArrayList;

// Librerias Spring
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;


// DTOs
import conociendocartagena.backend_conociendocartagena.DTOs.ResponseDtos;

// Modelo
import conociendocartagena.backend_conociendocartagena.models.Tours;

// Librerias Sawgger
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/tours")
@Tag(name =  "Tours", description = "Permite la gestion de los Tours")
public class ToursController {
    
    // Variable de Array
    private ArrayList<Tours> listTours = new ArrayList<Tours>();

    //Endpoint de creacion de tours
    @RequestMapping(path = "/crear", method=RequestMethod.POST)
    // Documentar el endpoint
    @Operation(summary = "Crear Tours", description = "Crear un nuevo Tours en el sistema")
    public ResponseDtos crear(@RequestBody Tours tour){
        listTours.add(tour);
        return new ResponseDtos("success", "Registro almacenado", tour);
    }

    //Endpoint de lista de restaurante
   @RequestMapping(path ="/listar", method=RequestMethod.GET)
   //Documentar el endpoint
   @Operation(summary = "Listar Tours", description = "Listar Tours en el sistema")
   public ResponseDtos listar() {
        return new ResponseDtos("success", "Listado de Tours", listTours);
   }

   //Endpoint de Consulta de tour
   @GetMapping("/consulta/{idTour}")
   //Documentar el endpoint
   @Operation(summary = "Consultar tour por ID", description = "Consultar tour por su ID en el sistema")
   public ResponseDtos consulta(@PathVariable int idTour) {
    for(Tours tuor : listTours){
        if(tuor.idTuor == idTour){
            return new ResponseDtos("success", "tour Encontrado", tuor);
        }
    }
        return new ResponseDtos("Warning", "tour no Existe", null);
   }

   //Endpoint eliminar tour
   @RequestMapping(path ="/eliminar/{idTour}", method=RequestMethod.DELETE)
   //Documentar el endpoint
   @Operation(summary = "Eliminar tour con ID", description = "Eliminar tour con ID en el sistema")
   public ResponseDtos eliminar(@PathVariable int idTour) {
    for(Tours tour : listTours){
        if(tour.idTuor == idTour){
            listTours.remove(tour);
            return new ResponseDtos("success", "tour Eliminado", tour);
        }
    }
        return new ResponseDtos("Warning", "tour no Eliminado", null);
   }

   //Endpoint actualizar tour
   @PutMapping("/actualiza/{idTour}")
   //Documentar el endpoint
   @Operation(summary = "Actualiza tour con ID", description = "Actualiza tour con ID en el sistema")
   public ResponseDtos actualizar(@PathVariable int idTour, @RequestBody Tours tour) {
    for(int i = 0; i<listTours.size(); i++){
        if(listTours.get(i).idTuor == idTour){
            listTours.set(i, tour);
            return new ResponseDtos("success", "tour Actualizado", listTours.get(i) );
        }
    }
        return new ResponseDtos("Warning", "tour no Actualizado", null);
   }
}
