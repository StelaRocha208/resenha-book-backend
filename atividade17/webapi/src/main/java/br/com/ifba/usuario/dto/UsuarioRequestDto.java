package br.com.ifba.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class UsuarioRequestDto {

    @NotBlank(message = "O primeiro nome é obrigatório")
    private String firstName;

    @NotBlank(message = "O sobrenome é obrigatório")
    private String lastName;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Formato de e-mail inválido")
    private String email;

    @NotNull(message = "A data de nascimento é obrigatória")
    private LocalDate birthDate;
}


