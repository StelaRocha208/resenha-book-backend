package br.com.ifba.usuario.service;

import br.com.ifba.usuario.dto.UsuarioRequestDto;
import br.com.ifba.usuario.dto.UsuarioResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioIService {
    UsuarioResponseDto createUser(UsuarioRequestDto usuarioRequestDto);
    Page<UsuarioResponseDto> getAllUsers(Pageable pageable);
    UsuarioResponseDto getUserById(Long id);
    UsuarioResponseDto updateUsuario(Long id, UsuarioRequestDto updatedUserDTO);
    void deleteUsuario(Long id);
}
