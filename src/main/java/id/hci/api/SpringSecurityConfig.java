package id.hci.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.hci.api.helper.Tools;
import id.hci.api.helper.auth.CustomUserDetailsService;
import id.hci.api.helper.auth.JwtConfigurer;
import id.hci.api.helper.auth.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return Tools.getMd5(rawPassword.toString());
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return Tools.getMd5(rawPassword.toString()).equalsIgnoreCase(encodedPassword);
            }
        };
    }

    private AccessDeniedHandler acessDeniedHandler() {
        return new AccessDeniedHandler() {

            @Override
            public void handle(HttpServletRequest request,
                               HttpServletResponse response,
                               AccessDeniedException accessDeniedException) throws IOException, ServletException {
                ObjectMapper objectMapper = new ObjectMapper();
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());

                Map<String,Object> data = new HashMap<>();
                data.put("error", "Access denied, invalid token.");
                data.put("status", false);
                response.getWriter().write(objectMapper.writeValueAsString(data));
            }
        };
    }

    private AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request,
                                 HttpServletResponse response,
                                 AuthenticationException authException) throws IOException, ServletException {
                ObjectMapper objectMapper = new ObjectMapper();
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());

                Map<String,Object> data = new HashMap<>();
                data.put("error", "Access denied");
                data.put("status", false);
                response.getWriter().write(objectMapper.writeValueAsString(data));
            }
        };
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
            .httpBasic()
                .disable()
            .csrf()
                .disable()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                    .antMatchers("/auth/register").permitAll()
                    .antMatchers("/auth/signin").permitAll()
                    .antMatchers("/auth/password-reset-send").permitAll()
                    .antMatchers(HttpMethod.GET, "/t/**").permitAll()
                    .anyRequest().authenticated()
            .and()
                .exceptionHandling()
                    .accessDeniedHandler(acessDeniedHandler())
                    .authenticationEntryPoint(authenticationEntryPoint())
            .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
        //@formatter:on
    }
}
