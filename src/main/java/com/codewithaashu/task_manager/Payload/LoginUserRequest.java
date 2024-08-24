package com.codewithaashu.task_manager.Payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginUserRequest {
    @NotBlank(message = "email is required field")
    @Email(message = "Invalid email.")
    private String email;
    @NotBlank(message = "password is required field")
    @Size(min = 8, max = 12, message = "Password must be 8 to 15 characters long")
    private String password;

}
