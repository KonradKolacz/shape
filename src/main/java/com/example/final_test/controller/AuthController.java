package com.example.final_test.controller;

import com.example.final_test.jwt.JwtRequest;
import com.example.final_test.jwt.JwtResponse;
import com.example.final_test.jwt.JwtTokenUtil;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authRequest) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        return new ResponseEntity<>(new JwtResponse(jwtTokenUtil.generateToken(authRequest.getUsername())), HttpStatus.OK);
    }

    @GetMapping(value = "/refresh-token")
    public ResponseEntity<JwtResponse> refreshToken(HttpServletRequest request) {
        DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");
        Map<String, Object> expectedMap = jwtTokenUtil.getMapFromJwtClaims(claims);
        if (expectedMap.size() == 0) {
            throw new RuntimeException("No claims!");
        }
        return ResponseEntity.ok(new JwtResponse(jwtTokenUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString())));
    }


}
