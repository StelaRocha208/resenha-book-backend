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

@RestController
@RequestMapping("/users")
public class UsuarioController {

    private final UsuarioIService usuarioService;

    public UsuarioController(UsuarioIService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/create")
    public ResponseEntity<UsuarioResponseDto> createUser(@Valid @RequestBody UsuarioRequestDto usuarioRequestDto) {
        return new ResponseEntity<>(usuarioService.createUser(usuarioRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<UsuarioResponseDto>> getAllUsers(Pageable pageable) {
        return new ResponseEntity<>(usuarioService.getAllUsers(pageable), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UsuarioResponseDto> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(usuarioService.getUserById(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UsuarioResponseDto> updateUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioRequestDto updatedUserDTO) {
        return new ResponseEntity<>(usuarioService.updateUsuario(id, updatedUserDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


