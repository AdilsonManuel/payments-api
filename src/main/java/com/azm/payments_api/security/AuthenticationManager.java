package com.azm.payments_api.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public interface AuthenticationManager
{
	Authentication authentication(Authentication authentication) throws AuthenticationException;
}
