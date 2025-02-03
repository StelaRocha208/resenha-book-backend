package br.com.ifba.usuario.service;

import br.com.ifba.usuario.dto.UsuarioRequestDto;
import br.com.ifba.usuario.dto.UsuarioResponseDto;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.infrastructure.exception.ResourceNotFoundException;
import br.com.ifba.usuario.repository.UsuarioRepository;
import br.com.ifba.usuario.service.UsuarioIService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService implements UsuarioIService {

    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, ModelMapper modelMapper) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public UsuarioResponseDto createUser(UsuarioRequestDto usuarioRequestDto) {
        Usuario usuario = modelMapper.map(usuarioRequestDto, Usuario.class);
        Usuario savedUser = usuarioRepository.save(usuario);
        return modelMapper.map(savedUser, UsuarioResponseDto.class);
    }

    @Override
    public Page<UsuarioResponseDto> getAllUsers(Pageable pageable) {
        return usuarioRepository.findAll(pageable)
                .map(usuario -> modelMapper.map(usuario, UsuarioResponseDto.class));
    }

    @Override
    public UsuarioResponseDto getUserById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com ID " + id + " não encontrado"));
        return modelMapper.map(usuario, UsuarioResponseDto.class);
    }

    @Override
    @Transactional
    public UsuarioResponseDto updateUsuario(Long id, UsuarioRequestDto updatedUserDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com ID " + id + " não encontrado"));

        usuario.setName(updatedUserDTO.getName());
        usuario.setEmail(updatedUserDTO.getEmail());

        Usuario updatedUser = usuarioRepository.save(usuario);
        return modelMapper.map(updatedUser, UsuarioResponseDto.class);
    }

    @Override
    @Transactional
    public void deleteUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário com ID " + id + " não encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}
