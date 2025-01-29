package br.com.ifba.usuario.controller;

import br.com.ifba.usuario.dto.UsuarioRequestDto;
import br.com.ifba.usuario.dto.UsuarioResponseDto;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.infrastructure.exception.ResourceNotFoundException;
import br.com.ifba.usuario.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    public UsuarioController(UsuarioRepository usuarioRepository, ModelMapper modelMapper) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }

    // Criar um novo usuário
    @PostMapping("/create")
    public ResponseEntity<UsuarioResponseDto> createUser(@RequestBody UsuarioRequestDto usuarioRequestDto) {
        // Converter DTO para entidade
        Usuario usuario = modelMapper.map(usuarioRequestDto, Usuario.class);

        // Salvar no banco de dados
        Usuario savedUser = usuarioRepository.save(usuario);

        // Converter entidade para DTO de resposta
        UsuarioResponseDto responseDTO = modelMapper.map(savedUser, UsuarioResponseDto.class);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    // Obter todos os usuários
    @GetMapping("/all")
    public ResponseEntity<List<UsuarioResponseDto>> getAllUsers() {
        // Buscar todos os usuários e converter para uma lista de DTOs
        List<UsuarioResponseDto> users = usuarioRepository.findAll()
                .stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioResponseDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Obter um usuário por ID
    @GetMapping("/get/{id}")
    public ResponseEntity<UsuarioResponseDto> getUserById(@PathVariable Long id) {
        // Buscar o usuário por ID e lançar exceção se não for encontrado
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found"));

        // Converter para DTO de resposta
        UsuarioResponseDto responseDTO = modelMapper.map(usuario, UsuarioResponseDto.class);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    // Atualizar um usuário por ID
    @PutMapping("/update/{id}")
    public ResponseEntity<UsuarioResponseDto> updateUsuario(
            @PathVariable Long id,
            @RequestBody UsuarioRequestDto updatedUserDTO) {

        // Buscar o usuário por ID e lançar exceção se não for encontrado
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found"));

        // Atualizar os campos do usuário
        usuario.setName(updatedUserDTO.getName());
        usuario.setEmail(updatedUserDTO.getEmail());

        // Salvar as alterações no banco de dados
        Usuario updatedUser = usuarioRepository.save(usuario);

        // Converter para DTO de resposta
        UsuarioResponseDto responseDto = modelMapper.map(updatedUser, UsuarioResponseDto.class);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // Excluir um usuário por ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        // Verificar se o usuário existe antes de deletar
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("User with ID " + id + " not found");
        }

        // Deletar o usuário
        usuarioRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

