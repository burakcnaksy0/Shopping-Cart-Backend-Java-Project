package b.aksoy.shopcard.security.config;

import b.aksoy.shopcard.security.jwt.AuthTokenFilter;
import b.aksoy.shopcard.security.jwt.JwtAuthEntryPoint;
import b.aksoy.shopcard.security.user.ShopUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
@EnableMethodSecurity(prePostEnabled = true) // Method seviyesinde güvenlik annotation'larını (@PreAuthorize, @PostAuthorize) aktif eder
public class ShopConfig {
    private final ShopUserDetailsService userDetailsService; // kullanıcı detaylarını yöneten sınıf
    private final JwtAuthEntryPoint jwtAuthEntryPoint;  // yetkisiz erişimleri yöneten sınıf

    // Giriş yapmış kullanıcıların erişebileceği URL'ler (sepet işlemleri)
    private static final List<String> SECURED_URLS = List.of("/api/v1/cart/**", "/api/v1/cartItems/**");
    // Herkese açık URL'ler (giriş/kayıt işlemleri) Giriş yapmamış kullanıcıların erişebileceği URL'ler (giriş yapma, kayıt olma)
    private static final List<String> UNSECURED_URLS = List.of("/api/v1/auth/**");

    @Bean
    public ModelMapper modelMapper() {return new ModelMapper();}

    @Bean
    public PasswordEncoder passwordEncoder() { // şifreleri güvenli bir şekilde encode eden sınıf
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthTokenFilter authTokenFilter() { // JWT token kontrolü yapan filter'ı Spring context'ine ekler.
        return new AuthTokenFilter();
    }
    // Spring Security'ın kimlik doğrulama yöneticisini oluşturur.
    // Kullanıcı giriş işlemlerini yöneten manager'ı yapılandırır. Login işlemlerinde kullanılır.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        var daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    // Spring Security'ın güvenlik filtre zincirini oluşturur.
    // JWT token kontrolü ve yetkisiz erişimleri yöneten sınıf
    // Tüm güvenlik kurallarını tanımlar.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer :: disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(UNSECURED_URLS.toArray(new String[0])).permitAll()
                        .requestMatchers(SECURED_URLS.toArray(String[] :: new)).authenticated()
                        .anyRequest().authenticated());

        http.authenticationProvider(daoAuthenticationProvider());
        http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
