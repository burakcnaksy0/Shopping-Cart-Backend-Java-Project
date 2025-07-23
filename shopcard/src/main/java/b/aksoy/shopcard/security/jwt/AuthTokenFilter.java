package b.aksoy.shopcard.security.jwt;

import b.aksoy.shopcard.security.user.ShopUserDetailsService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Bu, her HTTP isteğinde sadece bir kez çalışmasını garanti eder
public class AuthTokenFilter extends OncePerRequestFilter { 
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private ShopUserDetailsService shopUserDetailsService;

    // Veya constructor injection:
    public AuthTokenFilter(JwtUtils jwtUtils, ShopUserDetailsService shopUserDetailsService) {
        this.jwtUtils = jwtUtils;
        this.shopUserDetailsService = shopUserDetailsService;
    }

    public AuthTokenFilter() {

    }

    // Her HTTP isteğinde çalışan ana metod
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (StringUtils.hasText(jwt) && jwtUtils.validateToken(jwt)) { // Token geçerli mi?
                String username = jwtUtils.getUsernameFromToken(jwt);
                UserDetails userDetails = shopUserDetailsService.loadUserByUsername(username);
                // Authentication token oluşturma
                var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                // Security Context'e auth token'ı ekleme
                // Bu, Spring Security'ın oturum yönetimi için kullanılır
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage() + " : Invalid or expired token , you may login and try again");
            return;
        }catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(e.getMessage());
            return;
        }
        filterChain.doFilter(request, response);

    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
