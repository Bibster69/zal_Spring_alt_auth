package com.example.demo.controllers;

import com.example.demo.DTO.AuthResponseDTO;
import com.example.demo.DTO.LoginDTO;
import com.example.demo.DTO.RegDTO;
import com.example.demo.models.CustomUser;
import com.example.demo.models.Role;
import com.example.demo.repos.CustomUserRepository;
import com.example.demo.repos.RoleRepository;
import com.example.demo.security.TokenGenerator;
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
@RequestMapping("/auth/")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private CustomUserRepository customUserRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private TokenGenerator tokenGenerator;

    public AuthController(AuthenticationManager authenticationManager,
                          CustomUserRepository customUserRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          TokenGenerator tokenGenerator) {
        this.authenticationManager = authenticationManager;
        this.customUserRepository = customUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenGenerator = tokenGenerator;
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegDTO regDTO) {
        if (customUserRepository.existsByUsername(regDTO.getUsername())){
            return new ResponseEntity<>("Username taken", HttpStatus.BAD_REQUEST);
        }

        CustomUser user = new CustomUser();
        user.setUsername(regDTO.getUsername());
        user.setPassword(passwordEncoder.encode(regDTO.getPassword()));

        Role roles = roleRepository.findByName("user").get();
        user.setRoles(Collections.singletonList(roles));

        customUserRepository.save(user);

        return ResponseEntity.ok("Registration succesfull");
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }

}
