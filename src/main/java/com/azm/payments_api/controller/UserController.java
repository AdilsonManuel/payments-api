package com.azm.payments_api.controller;

import com.azm.payments_api.dto.UserRequestDto;
import com.azm.payments_api.dto.UserResponseDto;
import com.azm.payments_api.entity.User;
import com.azm.payments_api.service.UserService;
import java.net.URI;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
public class UserController
{

    private final UserService userService;

    // Criar usuário
    @PostMapping("/")
    public ResponseEntity<UserResponseDto> createUser (@RequestBody UserRequestDto userRequestDto)
    {
        UserResponseDto userResponseDto = userService.createUser(userRequestDto);
        return ResponseEntity.created(URI.create("/api/users/" + userResponseDto.getPk_users())).body(userResponseDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserResponseDto>> getAllUsers ()
    {
        return ResponseEntity.ok(userService.getAllusers());
    }

    // Buscar usuário por ID
    @GetMapping("/{pk_users}")
    public ResponseEntity<UserResponseDto> getUserByPk (@PathVariable Long pk_users)
    {
        return userService.getUserByID(pk_users).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Actualizar usuário
    @PutMapping("/{pk_users}")
    public ResponseEntity<UserResponseDto> updateUser (@PathVariable Long pk_users, @RequestBody UserRequestDto userRequestDto)
    {
        return userService.updateUser(pk_users, userRequestDto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Desactivar usuário
    @PatchMapping("/{pk_users}/desactivate")
    public ResponseEntity<Void> desactivateUser (@PathVariable Long pk_users)
    {
        boolean desactivated = userService.deactivateUser(pk_users);
        return desactivated ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    //Reactivar usuário
    @PatchMapping("/{pk_users}/activate")
    public ResponseEntity<Void> activateUser (@PathVariable Long pk_users)
    {
        return userService.activateUser(pk_users) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Alterar role
    @PatchMapping("/{pk_users}/role")
    public ResponseEntity<UserResponseDto> updateRole (@PathVariable Long pk_users, @RequestParam String role)
    {
        try
        {
            User.Role parsedRole = User.Role.valueOf(role.toUpperCase());
            return userService.updateUserRole(pk_users, parsedRole).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        }
        catch (IllegalArgumentException e)
        {
            return ResponseEntity.badRequest().build();
        }
    }

    // Deletar usuário permanentemente
    @DeleteMapping("/{pk_users}")
    public ResponseEntity<Void> deleteUser (@PathVariable Long pk_users)
    {
        boolean deleted = userService.deleteUser(pk_users);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
