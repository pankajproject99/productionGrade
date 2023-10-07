package com.example.productionGrade.security.service.impl;

import com.example.productionGrade.exception.BadRequestException;
import com.example.productionGrade.security.dao.request.SignUpRequest;
import com.example.productionGrade.security.dao.request.SigninRequest;
import com.example.productionGrade.security.dao.response.JwtAuthenticationResponse;
import com.example.productionGrade.security.entities.Role;
import com.example.productionGrade.security.entities.User;
import com.example.productionGrade.security.repository.RoleRepository;
import com.example.productionGrade.security.repository.UserRepository;
import com.example.productionGrade.security.service.AuthenticationService;
import com.example.productionGrade.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        //initialize role
        initDBRole();

        //Default on signup give USER role
        Role role = roleRepository.findByName(request.getRole())
                .orElseThrow(() -> new BadRequestException("Invalid role."));
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        // Used var keyword and lombok builder
        var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .roles(roleSet).build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

//    @Override
//    public User1 signup1(SignUpRequest request) {
//        // add roles if doesn't exist for first time
//        initDBRole();
//
//        Optional<Role1> role1 = role1Repository.findByName("USER");
//        Set<Role1> role1Set = new HashSet<>();
//        role1Set.add(role1.get());
//
//        // Used var keyword and lombok builder
//        var user = User1.builder().username(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
//                .roles1(role1Set).build();
//        user1Repository.save(user);
//        return user;
//    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    private void initDBRole() {
        // add roles if doesn't exist for first time
        Optional<Role> roleUserOptional = roleRepository.findByName("USER");
        Optional<Role> roleAdminOptional = roleRepository.findByName("ADMIN");
        Optional<Role> roleInvestorOptional = roleRepository.findByName("INVESTOR");

        if (!roleUserOptional.isPresent()) {
            Role role = new Role();
            role.setName("USER");
            roleRepository.save(role);
        }
        if (!roleAdminOptional.isPresent()) {
            Role role = new Role();
            role.setName("ADMIN");
            roleRepository.save(role);
        }
        if (!roleInvestorOptional.isPresent()) {
            Role role = new Role();
            role.setName("INVESTOR");
            roleRepository.save(role);
        }
    }
}