package conociendocartagena.backend_conociendocartagena.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Usuario {
    public int id;
    private String nombres;
    private String apellidos;
    private String username;
    public String telefono;
    public String email;
    public String tipo;


    @JsonIgnore
    private String password;

    public Usuario() {}

    public String getNombres() {
        return nombres;
    }
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    
    
}
