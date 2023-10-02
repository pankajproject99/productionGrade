package com.example.productionGrade.security.service;

import com.example.productionGrade.security.dao.request.SignUpRequest;
import com.example.productionGrade.security.dao.request.SigninRequest;
import com.example.productionGrade.security.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}
