package team.simpleVirtualWallet.beAuth.beAuthService.exception;

import lombok.Setter;

import java.sql.Timestamp;

public class CheckTokenException extends RuntimeException {
    public static enum CheckTokenExceptionType {
        None,
        Unknown,
        FailedToFetchCertainClaim,
    }

    private CheckTokenException.CheckTokenExceptionType type;

    @Setter
    private String domain = "";

    private Timestamp timestamp;
    public CheckTokenException(CheckTokenException.CheckTokenExceptionType type, String message) {
        super(message);
        this.type = type;
        timestamp = new Timestamp(System.currentTimeMillis());
    }
}
