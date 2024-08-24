package com.codewithaashu.task_manager.Payload;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private Long id;
    @NotBlank(message = "name is required field")
    @Size(min = 3, max = 15, message = "Name must be 3 to 15 characetes long.")
    private String name;
    @NotBlank(message = "title is required field")
    private String title;
    @NotBlank(message = "role is required field")
    private String role;
    @NotBlank(message = "email is required field")
    @Email(message = "Invalid email.")
    private String email;
    @NotBlank(message = "password is required field")
    @Size(min = 8, max = 12, message = "Password must be 8 to 15 characters long")
    private String password;
    private Boolean isAdmin;
    private Boolean isActive;
}
