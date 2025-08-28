package com.azm.payments_api.mapper;

import com.azm.payments_api.dto.UserRequestDto;
import com.azm.payments_api.dto.UserResponseDto;
import com.azm.payments_api.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-28T20:49:09+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Ubuntu)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setUserName( dto.getUserName() );
        user.setEmail( dto.getEmail() );
        user.setPassword( dto.getPassword() );
        user.setFullName( dto.getFullName() );
        user.setRole( dto.getRole() );

        user.setIsActive( true );

        return user;
    }

    @Override
    public UserResponseDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setPk_users( user.getPk_users() );
        userResponseDto.setUserName( user.getUserName() );
        userResponseDto.setFullName( user.getFullName() );
        userResponseDto.setEmail( user.getEmail() );
        userResponseDto.setIsActive( user.getIsActive() );
        if ( user.getRole() != null ) {
            userResponseDto.setRole( user.getRole().name() );
        }
        userResponseDto.setCreatedAt( user.getCreatedAt() );
        userResponseDto.setUpdatedAt( user.getUpdatedAt() );

        return userResponseDto;
    }
}
