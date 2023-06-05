package team.simpleVirtualWallet.beAuth.beAuthService.exception;

import lombok.Setter;

import java.sql.Timestamp;

public class LoginException extends RuntimeException {
    public static enum LoginExceptionType {
        None,
        Unknown,
        WrongUsernameOrPassword,
        Expired,
        WrongJti
    }

    private LoginExceptionType type;

    @Setter
    private String domain = "";

    private Timestamp timestamp;
    public LoginException(LoginExceptionType type, String message) {
        super(message);
        this.type = type;
        timestamp = new Timestamp(System.currentTimeMillis());
    }
}
