package team.simpleVirtualWallet.beAuth.beAuthService.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class LoginRequestDto {

    @NotNull(message = "username is null.")
    @NotBlank(message = "username is blank.")
    @NotEmpty(message = "username is empty.")
    private String username;

    @NotNull(message = "password is null.")
    @NotBlank(message = "password is blank.")
    @NotEmpty(message = "password is empty.")
    private String password;
}
