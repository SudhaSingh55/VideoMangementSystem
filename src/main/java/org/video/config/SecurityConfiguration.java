package org.video.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       /* return http.authorizeHttpRequests(request -> request.requestMatchers(new AntPathRequestMatcher("/videos/**"))
                        .permitAll())
                .authorizeHttpRequests(request -> request.requestMatchers(new AntPathRequestMatcher("/videos/**"))
                        .hasRole("ADMIN")
                        .anyRequest()
                        .authenticated())
                .httpBasic(Customizer.withDefaults())
                .build();

        return http.httpBasic().and().authorizeRequests().
                antMatchers(HttpMethod.POST, "/videos").hasRole("ADMIN").
                antMatchers(HttpMethod.PUT, "/videos/**").hasRole("ADMIN").
                antMatchers(HttpMethod.DELETE, "/videos/**").hasRole("ADMIN").
                antMatchers(HttpMethod.GET, "/videos/**").permitAll().
                and().
                csrf().disable().build();*/

        /*http.csrf(csrf -> csrf.disable())
                .authorizeRequests().
                antMatchers(HttpMethod.POST, "/videos").hasRole("ADMIN").
                antMatchers(HttpMethod.PUT, "/videos/**").hasRole("ADMIN").
                antMatchers(HttpMethod.DELETE, "/videos/**").hasRole("ADMIN").
                antMatchers(HttpMethod.GET, "/videos/**").permitAll().and()
                .httpBasic(withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();*/

        http.csrf(csrf -> csrf.disable())
                .authorizeRequests(authorize -> authorize
                        .antMatchers(HttpMethod.POST, "/videos").authenticated()
                        .antMatchers(HttpMethod.PUT, "/videos/**").authenticated()
                        .antMatchers(HttpMethod.DELETE, "/videos/**").authenticated()
                        .antMatchers(HttpMethod.GET, "/videos/video-process/**").authenticated()
                )
                .authorizeRequests().antMatchers(HttpMethod.GET, "/videos/**").permitAll().and()
                .httpBasic(withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}
