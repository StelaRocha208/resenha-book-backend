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
        // Criando um cliente WebClient para se comunicar com a API
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/users") // URL base do backend
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) // Define o tipo de conteúdo JSON
                .build();

        // Criando um novo usuário
        UsuarioRequestDto usuarioRequestDto = new UsuarioRequestDto();
        usuarioRequestDto.setFirstName("João");
        usuarioRequestDto.setLastName("Silva");
        usuarioRequestDto.setEmail("joao@gmail.com");

        UsuarioResponseDto response = webClient.post()
                .uri("/create") // Chamada para o endpoint de criação de usuário
                .bodyValue(usuarioRequestDto) // Envia os dados do usuário
                .retrieve()
                .bodyToMono(UsuarioResponseDto.class) // Converte a resposta para UsuarioResponseDto
                .block(); // Aguarda a resposta de forma síncrona

        log.info("Usuário Criado: {}", response);

        // Buscar usuário pelo ID
        UsuarioResponseDto usuarioBuscado = webClient.get()
                .uri("/get/{id}", response.getId()) // Endpoint para buscar usuário por ID
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
        usuarioAtualizado.setFirstName("João");
        usuarioAtualizado.setLastName("Atualizado");
        usuarioAtualizado.setEmail("joao.atualizado@gmail.com");

        UsuarioResponseDto updatedResponse = webClient.put()
                .uri("/update/{id}", response.getId()) // Endpoint de atualização
                .bodyValue(usuarioAtualizado)
                .retrieve()
                .bodyToMono(UsuarioResponseDto.class)
                .block();

        log.info("Usuário Atualizado: {}", updatedResponse);

        // Deletar usuário
        webClient.delete()
                .uri("/delete/{id}", response.getId()) // Endpoint de exclusão
                .retrieve()
                .toBodilessEntity()
                .block();

        log.info("Usuário com ID {} deletado.", response.getId());
    }
}
