package br.com.ifba.usuario.controller;

import br.com.ifba.usuario.dto.UsuarioRequestDto;
import br.com.ifba.usuario.dto.UsuarioResponseDto;
import br.com.ifba.usuario.service.UsuarioIService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*") // Habilita CORS para o frontend
@RestController
@RequestMapping("/users")
public class UsuarioController {

    private final UsuarioIService usuarioService;

    public UsuarioController(UsuarioIService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> createUser(@Valid @RequestBody UsuarioRequestDto usuarioRequestDto) {
        UsuarioResponseDto createdUser = usuarioService.createUser(usuarioRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> getAllUsers(Pageable pageable) {
        Page<UsuarioResponseDto> users = usuarioService.getAllUsers(pageable);
        return ResponseEntity.ok(users.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> getUserById(@PathVariable Long id) {
        UsuarioResponseDto user = usuarioService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> updateUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioRequestDto updatedUserDTO) {
        UsuarioResponseDto updatedUser = usuarioService.updateUsuario(id, updatedUserDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
}



