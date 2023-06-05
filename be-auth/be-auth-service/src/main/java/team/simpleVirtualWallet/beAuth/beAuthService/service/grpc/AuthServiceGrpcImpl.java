package team.simpleVirtualWallet.beAuth.beAuthService.service.grpc;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import team.simpleVirtualWallet.beAuth.beAuthService.exception.CheckTokenException;
import team.simpleVirtualWallet.beAuth.beAuthService.service.AuthService;


//https://yidongnan.github.io/grpc-spring-boot-starter/zh-CN/server/getting-started.html
@GrpcService
@Slf4j
public class AuthServiceGrpcImpl extends AuthServiceGrpc.AuthServiceImplBase {

    @Autowired
    private AuthService authService;

    public void login(AuthGrpc.LoginReq req,
                      StreamObserver<AuthGrpc.LoginRes> responseObserver) {

        log.info("login: {}", req);
        var tokens = authService.login(
                req.getAccount(),
                req.getPassword()
        );

        AuthGrpc.LoginRes res = AuthGrpc.LoginRes.newBuilder()
                .setAccessToken(tokens.getAccessToken())
                .setRenewToken(tokens.getRenewToken())
                .build();

        log.info("login: return {}", res);

        // 调用onNext()方法来通知gRPC框架把reply 从server端 发送回 client端
        responseObserver.onNext(res);

        // 表示完成调用
        responseObserver.onCompleted();
    }

    /**
     */
    public void checkToken(AuthGrpc.CheckTokenReq req,
                           StreamObserver<AuthGrpc.CheckTokenRes> responseObserver) {
        log.info("checkToken: {}", req);
        var claims = authService.checkToken(
                req.getAccessToken()
        );
        AuthGrpc.CheckTokenRes res;

        try {
            String subject = (String) claims.get("sub");
            Integer userId = (Integer) claims.get("userId");

            res = AuthGrpc.CheckTokenRes.newBuilder()
                    .setAccount(subject)
                    .setId(userId)
                    .build();
        } catch (RuntimeException e) {
            log.error("checkToken: failed to get sub and id from claims: [{}] {}", e, claims);
            throw new CheckTokenException(
                    CheckTokenException.CheckTokenExceptionType.FailedToFetchCertainClaim,
                    "checkToken: failed to get sub and id from claims");
        }

        log.info("login: return {}", res);

        // 调用onNext()方法来通知gRPC框架把reply 从server端 发送回 client端
        responseObserver.onNext(res);

        // 表示完成调用
        responseObserver.onCompleted();
    }
}



