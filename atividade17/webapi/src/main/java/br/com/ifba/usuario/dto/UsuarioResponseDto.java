package br.com.ifba.usuario.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UsuarioResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
}
