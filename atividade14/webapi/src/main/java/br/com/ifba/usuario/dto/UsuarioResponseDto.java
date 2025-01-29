package br.com.ifba.usuario.dto;

import lombok.Data;

@Data
public class UsuarioResponseDto {
    private Long id;
    private String name;
    private String email;
}
