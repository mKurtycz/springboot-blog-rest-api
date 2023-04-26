package priv.mKurtycz.blog.springbootblogrestapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import priv.mKurtycz.blog.springbootblogrestapi.payload.JWTAuthResponse;
import priv.mKurtycz.blog.springbootblogrestapi.payload.LoginDTO;
import priv.mKurtycz.blog.springbootblogrestapi.payload.RegisterDTO;
import priv.mKurtycz.blog.springbootblogrestapi.service.AuthService;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDTO loginDTO) {
        String token = authService.login(loginDTO);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        String response = authService.register(registerDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
