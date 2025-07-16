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
import conociendocartagena.backend_conociendocartagena.models.Sitios;

// Librerias Sawgger
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/sitio")
@Tag(name =  "Sitios", description = "Permite la gestion de los Sitios Turisticos")
public class SitiosController {
    
    // Variable de Array
    private ArrayList<Sitios> listSitios = new ArrayList<Sitios>();

    //Endpoint de creacion de Sitios
    @RequestMapping(path = "/crear", method=RequestMethod.POST)
    // Documentar el endpoint
    @Operation(summary = "Crear Sitios", description = "Crear un nuevo Sitios en el sistema")
    public ResponseDtos crear(@RequestBody Sitios sitio){
        listSitios.add(sitio);
        return new ResponseDtos("success", "Registro almacenado", sitio);
    }

    //Endpoint de lista de Sitios turisticos
   @RequestMapping(path ="/listar", method=RequestMethod.GET)
   //Documentar el endpoint
   @Operation(summary = "Listar Sitios turisticos", description = "Listar Sitios turisticos en el sistema")
   public ResponseDtos listar() {
        return new ResponseDtos("success", "Listado de Tuors", listSitios);
   }

   ///Endpoint de Consulta de Sitios turisticos
   @GetMapping("/consulta/{idSitios}")
   //Documentar el endpoint
   @Operation(summary = "Consultar tuor por ID", description = "Consultar sitios turisticos por su ID en el sistema")
   public ResponseDtos consulta(@PathVariable int idSitios) {
    for(Sitios sitio : listSitios){
        if(sitio.getIdSitios() == idSitios){
            return new ResponseDtos("success", "Sitio turistico Encontrado", sitio);
        }
    }
        return new ResponseDtos("Warning", "Sitio turistico no Existe", null);
   }

   //Endpoint eliminar Sitio turistico
   @RequestMapping(path ="/eliminar/{idSitios}", method=RequestMethod.DELETE)
   //Documentar el endpoint
   @Operation(summary = "Eliminar Tuor con ID", description = "Eliminar sitios turisticos con ID en el sistema")
   public ResponseDtos eliminar(@PathVariable int idSitios) {
    for(Sitios sitio : listSitios){
        if(sitio.getIdSitios() == idSitios){
            listSitios.remove(sitio);
            return new ResponseDtos("success", "sitios turisticos Eliminado", sitio);
        }
    }
        return new ResponseDtos("Warning", "sitios turisticos no Eliminado", null);
   }

   //Endpoint actualizar Sitio turistico
   @PutMapping("/actualiza/{idSitios}")
   //Documentar el endpoint
   @Operation(summary = "Actualiza tuor con ID", description = "Actualiza Sitio turistico con ID en el sistema")
   public ResponseDtos actualizar(@PathVariable int idSitios, @RequestBody Sitios sitio) {
    for(int i = 0; i<listSitios.size(); i++){
        if(listSitios.get(i).getIdSitios() == idSitios){
            listSitios.set(i, sitio);
            return new ResponseDtos("success", "Sitio turistico Actualizado", listSitios.get(i) );
        }
    }
        return new ResponseDtos("Warning", "Sitio turistico no Actualizado", null);
   }
}
