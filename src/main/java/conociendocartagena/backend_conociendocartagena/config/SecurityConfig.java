// src/main/java/conociendocartagena/backend_conociendocartagena/config/SecurityConfig.java

package conociendocartagena.backend_conociendocartagena.config;

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
}