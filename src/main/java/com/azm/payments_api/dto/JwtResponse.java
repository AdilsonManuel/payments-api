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
	private String type = "Bearer";
	private Long pk_user;
	private String userName;
	private String email;
	private List<String> roles;
}
