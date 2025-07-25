// src/main/java/conociendocartagena/backend_conociendocartagena/config/SecurityConfig.java

/*package conociendocartagena.backend_conociendocartagena.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // Añade esta importación
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain; // Añade esta importación

@Configuration
@EnableWebSecurity // Habilita la configuración de seguridad web de Spring Security
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuración de la cadena de filtros de seguridad HTTP
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilita CSRF (Cross-Site Request Forgery) para APIs sin sesión
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll() // Permite el acceso sin autenticación a CUALQUIER solicitud
            )
            .formLogin(form -> form.disable()) // Deshabilita la autenticación por formulario (para que no aparezca la página de login)
            .httpBasic(httpBasic -> httpBasic.disable()); // Deshabilita la autenticación Basic HTTP

        return http.build();
    }
}*/
package conociendocartagena.backend_conociendocartagena.config;

// AÑADIR estas importaciones:

import conociendocartagena.backend_conociendocartagena.servicios.UserDetailsServiceImpl; // Usa tu paquete de servicios

import org.springframework.beans.factory.annotation.Autowired; // Asegúrate de tener esta
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager; // Añadir
import org.springframework.security.authentication.dao.DaoAuthenticationProvider; // Añadir
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration; // Añadir
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; 
import org.springframework.security.config.http.SessionCreationPolicy; // Añadir
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain; 
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // Añadir


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Inyecta tu UserDetailsService y tu filtro JWT
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Necesitas exponer el AuthenticationManager, incluso si no lo usas directamente en tu AuthController
    // Es usado internamente por el DaoAuthenticationProvider que estamos configurando.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Configura el DaoAuthenticationProvider para que Spring Security sepa cómo cargar usuarios
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    // CAMBIO DE ORDEN:
    provider.setPasswordEncoder(passwordEncoder());    // Esta línea va primero ahora
    provider.setUserDetailsService(userDetailsService); // Y esta va después
    return provider;
    }

    // Configuración de la cadena de filtros de seguridad HTTP
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilita CSRF (común para APIs REST sin sesiones)
            .authorizeHttpRequests(authorize -> authorize
                // Permite el acceso sin autenticación a tus rutas de login, registro y Swagger
                .requestMatchers(
                    "/auth/login",
                    "/auth/logout", 
                    "/auth/register",
                    "/auth/refresh", 
                    "/swagger-ui/**", 
                    "/v3/api-docs/**", 
                    "/v3/api-docs").permitAll() // Agrega /v3/api-docs para que Swagger funcione
                // Todas las demás solicitudes (ej. /usuarios, /productos) REQUIEREN autenticación
                .anyRequest().authenticated() 
            )
            .sessionManagement(session -> session
                // CRUCIAL para JWT: indica que la aplicación NO usará sesiones HTTP (es "stateless")
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            // Agrega tu DaoAuthenticationProvider a Spring Security
            .authenticationProvider(daoAuthenticationProvider()) 
            // Inserta tu filtro JWT ANTES del filtro de autenticación de usuario/contraseña de Spring Security.
            // Esto asegura que tu JWT se procese primero en cada solicitud.
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}