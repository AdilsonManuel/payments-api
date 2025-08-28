package com.azm.payments_api.mapper;

import com.azm.payments_api.dto.UserRequestDto;
import com.azm.payments_api.dto.UserResponseDto;
import com.azm.payments_api.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper
{
    // Converter DTO de request para Entity

    @Mapping(target = "pk_users", ignore = true)   // id é auto-gerado
    @Mapping(target = "accounts", ignore = true)   // evitar ciclos
    @Mapping(target = "createdAt", ignore = true)  // preenchido pelo sistema
    @Mapping(target = "updatedAt", ignore = true)  // preenchido pelo sistema
    @Mapping(target = "isActive", constant = "true")
    // novo usuário começa activo
    User toEntity (UserRequestDto dto);

    // Converter Entity para DTO de resposta
    @Mapping(source = "pk_users", target = "pk_users")
    @Mapping(source = "userName", target = "userName")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "isActive", target = "isActive")
    @Mapping(source = "role", target = "role")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    UserResponseDto toDto (User user);

    // Conversão de String para Enum Role
    default User.Role parseRole (String role)
    {
        if (role == null || role.isBlank())
        {
            return User.Role.USER;
        }
        try
        {
            return User.Role.valueOf(role.toUpperCase());
        }
        catch (IllegalArgumentException e)
        {
            return User.Role.USER;
        }
    }

}
