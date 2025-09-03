package com.azm.payments_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse
{
    private String token;
    private Long pk_user;
    private String type = "Bearer";
    private String userName;
    private String email;
    private List<String> roles;

    public JwtResponse(String jwt, Long pkUser, String username, String email, List<String> roles)
    {
        this.token = jwt;
        this.pk_user = pkUser;
        this.userName = username;
        this.email = email;
        this.roles = roles;
    }
}
