package com.frankmoley.landon.security.helloservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableWebFluxSecurity
public class HelloServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloServiceApplication.class, args);
	}

	@Bean
	public MapReactiveUserDetailsService userDetailsService(){
		List<UserDetails> userDetails = new ArrayList<>();
		userDetails.add(User.withDefaultPasswordEncoder()
		.username("fpmoles").password("password").roles("USER", "ADMIN").build());
		userDetails.add(User.withDefaultPasswordEncoder()
		.username("jdoe").password("foobar").roles("USER").build());
		return new MapReactiveUserDetailsService(userDetails);
	}

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http){
		http.authorizeExchange().pathMatchers("/hello").permitAll()
				.anyExchange().hasRole("ADMIN")
				.and().httpBasic();
		return http.build();
	}
}
