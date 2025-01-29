package br.com.ifba.usuario.controller;

import br.com.ifba.usuario.dto.UsuarioRequestDto;
import br.com.ifba.usuario.dto.UsuarioResponseDto;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.infrastructure.exception.ResourceNotFoundException;
import br.com.ifba.usuario.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    // Injeção de dependências via construtor
    public UsuarioController(UsuarioRepository usuarioRepository, ModelMapper modelMapper) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }

    // Endpoint para criar um novo usuário
    @Transactional // Garante que a operação será atômica
    @PostMapping("/create")
    public ResponseEntity<UsuarioResponseDto> createUser(@Valid @RequestBody UsuarioRequestDto usuarioRequestDto) {
        // Converte DTO para entidade
        Usuario usuario = modelMapper.map(usuarioRequestDto, Usuario.class);

        // Salva o usuário no banco de dados
        Usuario savedUser = usuarioRepository.save(usuario);

        // Converte entidade para DTO de resposta
        UsuarioResponseDto responseDTO = modelMapper.map(savedUser, UsuarioResponseDto.class);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    // Endpoint para listar todos os usuários
    @GetMapping("/all")
    public ResponseEntity<List<UsuarioResponseDto>> getAllUsers() {
        // Busca todos os usuários e converte para DTOs
        List<UsuarioResponseDto> users = usuarioRepository.findAll()
                .stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioResponseDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Endpoint para buscar um usuário pelo ID
    @GetMapping("/get/{id}")
    public ResponseEntity<UsuarioResponseDto> getUserById(@PathVariable Long id) {
        // Busca o usuário ou lança uma exceção caso não encontre
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com ID " + id + " não encontrado"));

        // Converte para DTO de resposta
        UsuarioResponseDto responseDTO = modelMapper.map(usuario, UsuarioResponseDto.class);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    // Endpoint para atualizar um usuário
    @Transactional // Garante que a operação será atômica
    @PutMapping("/update/{id}")
    public ResponseEntity<UsuarioResponseDto> updateUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDto updatedUserDTO) {

        // Busca o usuário ou lança uma exceção caso não encontre
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com ID " + id + " não encontrado"));

        // Atualiza os campos do usuário
        usuario.setName(updatedUserDTO.getName());
        usuario.setEmail(updatedUserDTO.getEmail());

        // Salva as alterações no banco de dados
        Usuario updatedUser = usuarioRepository.save(usuario);

        // Converte para DTO de resposta
        UsuarioResponseDto responseDto = modelMapper.map(updatedUser, UsuarioResponseDto.class);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // Endpoint para excluir um usuário pelo ID
    @Transactional // Garante que a operação será atômica
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        // Verifica se o usuário existe antes de tentar deletá-lo
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário com ID " + id + " não encontrado");
        }

        // Deleta o usuário
        usuarioRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


