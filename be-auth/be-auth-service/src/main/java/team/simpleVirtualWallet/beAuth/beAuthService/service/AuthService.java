package team.simpleVirtualWallet.beAuth.beAuthService.service;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

public interface AuthService {

    @Data
    @AllArgsConstructor
    class JwtToken {
        private String accessToken;
        private String renewToken;
    }

    JwtToken login(String username, String password);

    Map<String, Object> checkToken(String token);
}
