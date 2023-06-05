package team.simpleVirtualWallet.beAuth.beAuthService.service.grpc;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import team.simpleVirtualWallet.beUser.service.grpc.UserGrpc;
import team.simpleVirtualWallet.beUser.service.grpc.UserServiceGrpc;

@Service
@Slf4j
public class UserService {

    @GrpcClient("userService")
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    public UserGrpc.CreateUserRes createUser(UserGrpc.CreateUserReq request) {
        log.info("createUser: {}", request);
        var result = userServiceBlockingStub.createUser(request);
        log.info("createUser: return {}", result);
        return result;
    }

    public UserGrpc.GetUserRes getUser(UserGrpc.GetUserReq request) {
        log.info("getUser: {}", request);
        var result = userServiceBlockingStub.getUser(request);
        log.info("getUser: return {}", result);
        return result;
    }

    public UserGrpc.VerifyUserRes verifyUser(UserGrpc.VerifyUserReq request) {
        log.info("verifyUser: {}", request);
        var result = userServiceBlockingStub.verifyUser(request);
        log.info("verifyUser: return {}", result);
        return result;
    }

}
