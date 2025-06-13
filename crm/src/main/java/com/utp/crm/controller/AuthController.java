package com.utp.crm.controller;


import com.utp.crm.dto.AuthenticationRequest;
import com.utp.crm.jwt.JwtTokenProvider;
import com.utp.crm.model.Empleado;
import com.utp.crm.repository.EmpleadoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author hantsy
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final com.utp.crm.jwt.JwtTokenProvider tokenProvider;

    private final ReactiveAuthenticationManager authenticationManager;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @PostMapping("/login")
    public Mono<ResponseEntity<Map<String, Object>>> login(
            @Valid @RequestBody Mono<AuthenticationRequest> authRequest) {

        return authRequest.flatMap(login ->
                this.authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(login.username(), login.password()))
                        .flatMap(auth ->
                                empleadoRepository.findByEmail(login.username()) // retorna Mono<String>
                                        .map(userId -> {
                                            String jwt = tokenProvider.createToken(auth);

                                            HttpHeaders headers = new HttpHeaders();
                                            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);

                                            Map<String, Object> responseBody = Map.of(
                                                    "access_token", jwt,
                                                    "user_id", userId.getId(),
                                                    "user_role", userId.getRol()
                                            );

                                            return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
                                        })
                        )
        );
    }



}
