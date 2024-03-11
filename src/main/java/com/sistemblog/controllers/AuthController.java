package com.sistemblog.controllers;

import com.sistemblog.models.dao.RolDao;
import com.sistemblog.models.dao.UsuarioDao;
import com.sistemblog.models.dto.LoginDto;
import com.sistemblog.models.dto.PublicationDto;
import com.sistemblog.models.dto.RegisterDto;
import com.sistemblog.models.entities.Rol;
import com.sistemblog.models.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private RolDao rolDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(), loginDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("Ha iniciado sesion con exito", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registrarUsuario(@RequestBody RegisterDto registerDto) {
        if (usuarioDao.existByUsername(registerDto.getUsername()) > 0) {
            return new ResponseEntity<>("Ese nombre de usuario ya existe", HttpStatus.BAD_REQUEST);
        }
        if (usuarioDao.existByEmail(registerDto.getEmail()) > 0) {
            return new ResponseEntity<>("Ese email de usuario ya existe", HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = Usuario.builder()
                .nombre(registerDto.getNombre())
                .username(registerDto.getUsername())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .build();
        Rol roles = rolDao.findByNombre("ROLE_ADMIN").get();
        usuario.setRoles(Collections.singleton(roles));

        usuarioDao.save(usuario);

        return new ResponseEntity<>("Usuario Registrdo Exitosamente", HttpStatus.OK);
    }

}
