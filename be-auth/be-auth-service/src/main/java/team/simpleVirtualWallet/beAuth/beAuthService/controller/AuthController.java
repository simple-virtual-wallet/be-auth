package team.simpleVirtualWallet.beAuth.beAuthService.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.simpleVirtualWallet.beAuth.beAuthService.dto.LoginRequestDto;
import team.simpleVirtualWallet.beAuth.beAuthService.dto.LoginResponseDto;
import team.simpleVirtualWallet.beAuth.beAuthService.service.AuthService;

import javax.validation.Valid;

@RestController
@Slf4j
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("logins")
    public ResponseEntity<LoginResponseDto> logins(@Valid @RequestBody LoginRequestDto req) {

        log.info("login: {}", req);
        var tokens = authService.login(req.getUsername(), req.getPassword());

        return new ResponseEntity<>(new LoginResponseDto(tokens.getAccessToken(), tokens.getRenewToken()), HttpStatus.OK);
    }

}
