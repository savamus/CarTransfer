package org.example.tranfercar.contollers;


import io.jsonwebtoken.Jwt;
import org.example.tranfercar.DTO.LoginRequest;
import org.example.tranfercar.DTO.UserRegistrationRequest;
import org.example.tranfercar.Result;
import org.example.tranfercar.domain.User;
import org.example.tranfercar.security.JwtUtil;
import org.example.tranfercar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
public class UserContoller {

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UserService userService;

    @PostMapping("/register/user")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest request) {

        Result<User> result = userService.registerUser(request);
        if (!result.success()) {
            return ResponseEntity.badRequest().body(result.error());
        }
        return ResponseEntity.ok(result.data());

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Result<User> result = userService.login(request);
        if (!result.success()) {
            return ResponseEntity.badRequest().body(result.error());
        }

        String accessToken = jwtUtil.generateToken(request.email());
        String refreshToken = jwtUtil.generateRefreshToken(request.email());

        ResponseCookie accessCookie = ResponseCookie.from("access_token", accessToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofMinutes(15))
                .sameSite("Strict")
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofDays(7))
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(result.data());
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@CookieValue(name = "refresh_token", required = false) String refreshToken) {
        if (refreshToken == null || !jwtUtil.validateJwtToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }

        String username = jwtUtil.getUsernameFromToken(refreshToken);
        String newAccessToken = jwtUtil.generateToken(username);

        ResponseCookie newAccessCookie = ResponseCookie.from("access_token", newAccessToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofMinutes(15))
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, newAccessCookie.toString())
                .body("Access token refreshed");
    }


}
