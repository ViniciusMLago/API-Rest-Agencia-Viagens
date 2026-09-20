package agencia_viagens.security;

import agencia_viagens.entity.Usuario;
import agencia_viagens.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired 
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/destinos/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/destinos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/destinos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/destinos/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/destinos/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    // Este CommandLineRunner cria os usuários no banco automaticamente ao iniciar a aplicação
    @Bean
    public CommandLineRunner popularUsuarios(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Verifica se o admin já existe para não duplicar
            if (usuarioRepository.findByUsername("admin").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("123456")); // Criptografa corretamente a senha "123456"
                admin.setRole("ADMIN");
                usuarioRepository.save(admin);
                System.out.println(">>> Usuário ADMIN criado com sucesso!");
            }

            // Verifica se o usuário comum já existe
            if (usuarioRepository.findByUsername("usuario_comum").isEmpty()) {
                Usuario user = new Usuario();
                user.setUsername("usuario_comum");
                user.setPassword(passwordEncoder.encode("123456")); // Criptografa corretamente a senha "123456"
                user.setRole("USER");
                usuarioRepository.save(user);
                System.out.println(">>> Usuário USER criado com sucesso!");
            }
        };
    }    
}
