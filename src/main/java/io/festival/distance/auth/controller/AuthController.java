package io.festival.distance.auth.controller;

import io.festival.distance.auth.dto.LoginDto;
import io.festival.distance.auth.dto.TokenDto;
import io.festival.distance.auth.service.LoginAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api")
public class AuthController {
    private final LoginAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> authorize(@RequestBody LoginDto loginDto) {
        TokenDto tokenDto = authService.login(loginDto);
        return ResponseEntity.ok(tokenDto);
    }
}
