package com.gestionturnos.turnos.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {  // Implementa UserDetails para integración directa

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)  // Asegura unicidad y no nulo
    private String username;

    @Column(nullable = false)  // Obligatorio
    private String password;

    @Column(nullable = false)  // Obligatorio
    private String rol;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @Override  // Requerido por UserDetails
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    @Override  // Requerido por UserDetails
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    // Métodos requeridos por UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + rol)  // Formato requerido por Spring Security
        );
    }

    @Override
    public boolean isAccountNonExpired() { return true; }  // Cuenta nunca expira

    @Override
    public boolean isAccountNonLocked() { return true; }  // Cuenta nunca bloqueada

    @Override
    public boolean isCredentialsNonExpired() { return true; }  // Credenciales nunca expiran

    @Override
    public boolean isEnabled() { return true; }  // Cuenta siempre activa
}
