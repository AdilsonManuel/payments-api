package com.azm.payments_api.service;

import com.azm.payments_api.dto.UserRequestDto;
import com.azm.payments_api.dto.UserResponseDto;
import com.azm.payments_api.entity.User;
import com.azm.payments_api.mapper.UserMapper;
import com.azm.payments_api.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService
{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // Criar novo usuário
    public UserResponseDto createUser (UserRequestDto userRequestDto)
    {
        // Se não veio role no request, seta padrão
        if (userRequestDto.getRole() == null)
        {
            userRequestDto.setRole(User.Role.USER);
        }

        User user = userMapper.toEntity(userRequestDto);
        User userSaved = userRepository.save(user);

        return userMapper.toDto(userSaved);
    }

    // Listar todos os usuários
    public List<UserResponseDto> getAllusers ()
    {
        return userRepository.findAll().stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    // Buscar usuário por ID
    public Optional<UserResponseDto> getUserByID (Long pk_users)
    {
        return userRepository.findById(pk_users).map(userMapper::toDto);
    }

    // Actualizar usuário
    public Optional<UserResponseDto> updateUser (Long pk_users, UserRequestDto userRequestDto)
    {
        return userRepository.findById(pk_users).map(existingUser ->
        {
            existingUser.setUserName(userRequestDto.getUserName());
            existingUser.setEmail(userRequestDto.getEmail());
            existingUser.setFullName(userRequestDto.getFullName());
            User updated = userRepository.save(existingUser);

            return userMapper.toDto(updated);
        });
    }

    // Desactivar usuário (soft delete)
    public boolean deactivateUser (Long pk_users)
    {
        return userRepository.findById(pk_users).map(user ->
        {
            user.setIsActive(false);
            userRepository.save(user);
            return true;
        }).orElse(false);
    }

    // Alterar role do usuário
    public Optional<UserResponseDto> updateUserRole (Long pk_users, User.Role newRole)
    {

        if (newRole == null)
        {
            return Optional.empty();
        }
        return userRepository.findById(pk_users).map(existingUser ->
        {
            existingUser.setRole(newRole);

            User userUpdatedRole = userRepository.save(existingUser);

            return userMapper.toDto(userUpdatedRole);

        });
    }

    // Reativar usuário
    public boolean activateUser (Long pk_users)
    {
        return userRepository.findById(pk_users).map(user ->
        {
            user.setIsActive(true);
            userRepository.save(user);

            return true;
        }).orElse(false);
    }

    // Deletar usuário permanentemente
    public boolean deleteUser (Long pk_users)
    {
        return userRepository.findById(pk_users).map(user ->
        {
            userRepository.delete(user);
            return true;
        }).orElse(false);
    }
}
