package team.simpleVirtualWallet.beAuth.beAuthService.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team.simpleVirtualWallet.beAuth.beAuthService.exception.LoginException;
import team.simpleVirtualWallet.beAuth.beAuthService.service.grpc.UserService;
import team.simpleVirtualWallet.beUser.service.grpc.UserGrpc;

import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserService userService;

    @Value("${jwt.secret}")
    private String secret;

    public static final long JWT_ACCESS_EXP = 1*24*60*60;
    public static final long JWT_REFRESH_EXP = 30*24*60*60;

    @Override
    public JwtToken login(String username, String password) {

        var verifyUserRes = userService.verifyUser(UserGrpc.VerifyUserReq.newBuilder()
                        .setAccount(username)
                        .setPassword(password)
                        .build());

        var userId = verifyUserRes.getUser().getId();

        UUID accessUuid = UUID.randomUUID();
        UUID refreshUuid = UUID.randomUUID();
        var issuedAt = new Date(System.currentTimeMillis());
        var accessExpiration = new Date(System.currentTimeMillis() + JWT_ACCESS_EXP*1000L);
        var refreshExpiration = new Date(System.currentTimeMillis() + JWT_REFRESH_EXP*1000L);

        // cache session key
        //
        //

        var claims = new HashMap<String, Object>();
        claims.put("userId", userId);

        return new JwtToken(
                Jwts.builder()
                    .setClaims(claims)
                    .setSubject(username)
                    .setIssuedAt(accessExpiration)
                    .setExpiration(refreshExpiration)
                    .setId(accessUuid.toString())
                    .signWith(SignatureAlgorithm.HS256, secret)
                    .compact(),
                Jwts.builder()
                    .setClaims(claims)
                    .setSubject(username)
                    .setIssuedAt(accessExpiration)
                    .setExpiration(refreshExpiration)
                    .setId(refreshUuid.toString())
                    .signWith(SignatureAlgorithm.HS256, secret)
                    .compact());
    }

    @Override
    public Map<String, Object> checkToken(String token) {

        var claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

        // check cache session key
        //
        //

        if (claims.getExpiration().before(new Date())){
            throw new LoginException(LoginException.LoginExceptionType.Expired, "token expired");
        }

        var parsedClaims = new HashMap<String, Object>();

        for (Map.Entry<String,Object> entry : claims.entrySet()) {
            parsedClaims.put(entry.getKey(), entry.getValue());
        }

        return parsedClaims;
    }
}
