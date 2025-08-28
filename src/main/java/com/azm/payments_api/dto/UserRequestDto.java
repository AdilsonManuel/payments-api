package com.azm.payments_api.dto;

import com.azm.payments_api.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto
{

    @NotBlank
    @Size(min = 3, max = 50)
    private String userName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(max = 100)
    private String fullName;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    // Novo campo opcional para cadastrar role
    private User.Role role = User.Role.USER; // valor padrão USER
}
