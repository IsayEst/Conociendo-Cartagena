package conociendocartagena.backend_conociendocartagena.controller;

import java.util.ArrayList;

// Librerias Spring
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// DTOs
import conociendocartagena.backend_conociendocartagena.DTOs.ResponseDtos;
// Modelo
import conociendocartagena.backend_conociendocartagena.models.Restaurante;

// Librerias Sawgger
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;




@RestController
@RequestMapping("/restaurantes")
@Tag(name = "Restaurantes", description = "Permite la gention de restaurantes")
public class RestauranteController {
    
    // Variable de Array
    private ArrayList<Restaurante> listRestaurantes = new ArrayList<Restaurante>();

    //Endpoint de creacion de restaurantes
   @RequestMapping(path ="/crear", method=RequestMethod.POST)
   //Documentar el endpoint
   @Operation(summary = "Crear restaurante", description = "Crear un nuevo restaurante en el sistema")
   public ResponseDtos crear(@RequestBody Restaurante restaurante) {
        listRestaurantes.add(restaurante);
       return new ResponseDtos("success", "Registro almacenado", restaurante);
   }

   //Endpoint de lista de restaurante
   @RequestMapping(path ="/listar", method=RequestMethod.GET)
   //Documentar el endpoint
   @Operation(summary = "Listar restaurante", description = "Listar restaurantes en el sistema")
   public ResponseDtos listar() {
        return new ResponseDtos("success", "Listado de restaurantes", listRestaurantes);
   }

   //Endpoint de Consulta de restaurante
   @GetMapping("/consulta/{idRestaurante}")
   //Documentar el endpoint
   @Operation(summary = "Consultar restaurante por ID", description = "Consultar restaurante por su ID en el sistema")
   public ResponseDtos consulta(@PathVariable int idRestaurante) {
    for(Restaurante restaurante : listRestaurantes){
        if(restaurante.idRestaurante == idRestaurante){
            return new ResponseDtos("success", "Restaurante Encontrado", restaurante);
        }
    }
        return new ResponseDtos("Warning", "restaurante no Existe", null);
   }

   //Endpoint eliminar restaurante
   @RequestMapping(path ="/eliminar/{idRestaurante}", method=RequestMethod.DELETE)
   //Documentar el endpoint
   @Operation(summary = "Eliminar restaurantes con ID", description = "Eliminar restaurantes con ID en el sistema")
   public ResponseDtos eliminar(@PathVariable int idRestaurante) {
    for(Restaurante restaurante : listRestaurantes){
        if(restaurante.idRestaurante == idRestaurante){
            listRestaurantes.remove(restaurante);
            return new ResponseDtos("success", "restaurante Eliminado", restaurante);
        }
    }
        return new ResponseDtos("Warning", "restaurante no Eliminado", null);
   }

   //Endpoint actualizar restaurante
   @PutMapping("/actualiza/{idRestaurante}")
   //Documentar el endpoint
   @Operation(summary = "Actualiza restaurantes con ID", description = "Actualiza restaurantes con ID en el sistema")
   public ResponseDtos actualizar(@PathVariable int idRestaurante, @RequestBody Restaurante restaurant) {
    for(int i = 0; i<listRestaurantes.size(); i++){
        if(listRestaurantes.get(i).idRestaurante == idRestaurante){
            listRestaurantes.set(i, restaurant);
            return new ResponseDtos("success", "Restaurante Actualizado", listRestaurantes.get(i) );
        }
    }
        return new ResponseDtos("Warning", "Restaurante no Actualizado", null);
   }

}
