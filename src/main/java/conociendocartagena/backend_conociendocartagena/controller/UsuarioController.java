package conociendocartagena.backend_conociendocartagena.controller;
import java.util.ArrayList;

// Librerias Spring
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import conociendocartagena.backend_conociendocartagena.DTOs.ResponseDtos;
// Modelos
import conociendocartagena.backend_conociendocartagena.models.Usuario;

// Librerias Sawgger
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Permite gestion los usuarios del sistema")//Titulo
public class UsuarioController {

    // Variable de Array
    private ArrayList<Usuario> listUsuarios= new ArrayList<Usuario>();
    
    //Endpoint de creacion de usuarios
   @RequestMapping(path ="/crear", method=RequestMethod.POST)
   //Documentar el endpoint
   @Operation(summary = "Crear usuario", description = "Crear un nuevo usuario en el sistema")
   public ResponseDtos crear(@RequestBody Usuario usuario) {
        listUsuarios.add(usuario);
       return new ResponseDtos("success", "Registro almacenado", usuario);
   }
   //Endpoint de lista de usuarios
   @RequestMapping(path ="/listar", method=RequestMethod.GET)
   //Documentar el endpoint
   @Operation(summary = "Listar usuario", description = "Listar usuarios en el sistema")
   public ResponseDtos listar() {
        return new ResponseDtos("success", "Listado de usuarios", listUsuarios);
   }
   
   //Endpoint de Consulta de usuarios
   @GetMapping("/consulta/{id}")
   //Documentar el endpoint
   @Operation(summary = "Consultar usuarios por ID", description = "Consultar usuarios por su ID en el sistema")
   public ResponseDtos consulta(@PathVariable int id) {
    for(Usuario usuario : listUsuarios){
        if(usuario.id == id){
            return new ResponseDtos("success", "Usuacio Encontrado", usuario);
        }
    }
        return new ResponseDtos("Warning", "Usuario no Existe", null);
   }

   //Endpoint eliminar usuario
   @RequestMapping(path ="/eliminar/{id}", method=RequestMethod.DELETE)
   //Documentar el endpoint
   @Operation(summary = "Eliminar usuarios con ID", description = "Eliminar usuarios con ID en el sistema")
   public ResponseDtos eliminar(@PathVariable int id) {
    for(Usuario usuario : listUsuarios){
        if(usuario.id == id){
            listUsuarios.remove(usuario);
            return new ResponseDtos("success", "Usuario Eliminado", usuario);
        }
    }
        return new ResponseDtos("Warning", "Usuario no Eliminado", null);
   }

    //Endpoint actualizar usuario
   @PutMapping("/actualiza/{id}")
   //Documentar el endpoint
   @Operation(summary = "Actualiza usuarios con ID", description = "Actualiza usuarios con ID en el sistema")
   public ResponseDtos actualizar(@PathVariable int id, @RequestBody Usuario user) {
    for(int i = 0; i<listUsuarios.size(); i++){
        if(listUsuarios.get(i).id == id){
            listUsuarios.set(i, user);
            return new ResponseDtos("success", "Usuario Actualizado", listUsuarios.get(i) );
        }
    }
        return new ResponseDtos("Warning", "Usuario no Actualizado", null);
   }

}
