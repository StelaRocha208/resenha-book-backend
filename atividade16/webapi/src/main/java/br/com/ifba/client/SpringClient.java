package br.com.ifba.client;

import br.com.ifba.usuario.dto.UsuarioRequestDto;
import br.com.ifba.usuario.dto.UsuarioResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.data.domain.Page;

@Log4j2
public class SpringClient {

    public static void main(String[] args) {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/users")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        // Criando um novo usuário
        UsuarioRequestDto usuarioRequestDto = new UsuarioRequestDto();
        usuarioRequestDto.setName("João");
        usuarioRequestDto.setEmail("joao@gmail.com");

        UsuarioResponseDto response = webClient.post()
                .uri("/create")
                .bodyValue(usuarioRequestDto)
                .retrieve()
                .bodyToMono(UsuarioResponseDto.class)
                .block();

        log.info("Usuário Criado: {}", response);

        // Buscar usuário pelo ID
        UsuarioResponseDto usuarioBuscado = webClient.get()
                .uri("/get/{id}", response.getId())
                .retrieve()
                .bodyToMono(UsuarioResponseDto.class)
                .block();

        log.info("Usuário Encontrado: {}", usuarioBuscado);

        // Listar todos os usuários (paginação)
        Page<UsuarioResponseDto> allUsers = webClient.get()
                .uri("/all?page=0&size=10") // Pegando os primeiros 10 usuários
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Page<UsuarioResponseDto>>() {})
                .block();

        log.info("Lista de Usuários: {}", allUsers);

        // Atualizar usuário
        UsuarioRequestDto usuarioAtualizado = new UsuarioRequestDto();
        usuarioAtualizado.setName("João Atualizado");
        usuarioAtualizado.setEmail("joao.atualizado@gmail.com");

        UsuarioResponseDto updatedResponse = webClient.put()
                .uri("/update/{id}", response.getId())
                .bodyValue(usuarioAtualizado)
                .retrieve()
                .bodyToMono(UsuarioResponseDto.class)
                .block();

        log.info("Usuário Atualizado: {}", updatedResponse);

        // Deletar usuário
        webClient.delete()
                .uri("/delete/{id}", response.getId())
                .retrieve()
                .toBodilessEntity()
                .block();

        log.info("Usuário com ID {} deletado.", response.getId());
    }
}