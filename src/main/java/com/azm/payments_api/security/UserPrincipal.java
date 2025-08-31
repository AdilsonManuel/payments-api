package com.azm.payments_api.security;

import com.azm.payments_api.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPrincipal implements UserDetails
{
	private Long pk_user;
	private String userName;
	private String email;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	
	public static UserPrincipal create(User user)
	{
		Collection<GrantedAuthority> authorities1 = Collections.singletonList(
				new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
		);
		
		return new UserPrincipal(
				user.getPk_users(),
				user.getUserName(),
				user.getEmail(),
				user.getPassword(),
				authorities1
		);
	}
	
	@Override
	public String getUsername()
	{
		return "";
	}
	
	@Override
	public boolean isAccountNonExpired()
	{
		return UserDetails.super.isAccountNonExpired();
	}
	
	@Override
	public boolean isAccountNonLocked()
	{
		return UserDetails.super.isAccountNonLocked();
	}
	
	@Override
	public boolean isCredentialsNonExpired()
	{
		return UserDetails.super.isCredentialsNonExpired();
	}
	
	@Override
	public boolean isEnabled()
	{
		return UserDetails.super.isEnabled();
	}
}
