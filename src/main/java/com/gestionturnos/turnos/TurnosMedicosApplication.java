package com.gestionturnos.turnos;

import com.gestionturnos.turnos.model.Usuario;
import com.gestionturnos.turnos.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Locale;

@SpringBootApplication
public class TurnosMedicosApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(TurnosMedicosApplication.class, args);
	}

	@Bean
	CommandLineRunner crearAdminInicial(UsuarioRepository usuarioRepo, PasswordEncoder encoder) {
		return args -> {
			if (usuarioRepo.findByUsername("admin").isEmpty()) {
				Usuario admin = new Usuario();
				admin.setUsername("admin");
				admin.setPassword(encoder.encode("123")); // Contraseña: 123
				admin.setRol("ADMIN");
				usuarioRepo.save(admin);
				System.out.println("=== USUARIO ADMIN CREADO ===");
				System.out.println("Usuario: admin");
				System.out.println("Contraseña: 123");
			}
		};
	}

	// === Configuración de i18n (internacionalización) ===

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(new Locale("es")); // Español por defecto
		return slr;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}
}
