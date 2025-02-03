package br.com.ifba.usuario.repository;

import br.com.ifba.usuario.entity.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Should save a user in the database")
    public void shouldSaveUser() {
        Usuario usuario = new Usuario();
        usuario.setName("Maria");
        usuario.setEmail("maria@gmail.com");
        usuario.setPassword("123456");

        Usuario savedUser = usuarioRepository.save(usuario);

        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getPassword()).isEqualTo("123456");
    }

    @Test
    @DisplayName("Should find a user by ID and verify the password")
    public void shouldFindUserById() {
        Usuario usuario = new Usuario();
        usuario.setName("Carlos");
        usuario.setEmail("carlos@gmail.com");
        usuario.setPassword("securePassword");
        usuario = usuarioRepository.save(usuario);

        Optional<Usuario> foundUser = usuarioRepository.findById(usuario.getId());

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getPassword()).isEqualTo("securePassword");
    }

    @Test
    @DisplayName("Should return true when user exists by ID")
    public void shouldReturnTrueWhenUserExistsById() {
        Usuario usuario = new Usuario();
        usuario.setName("Ana");
        usuario.setEmail("ana@gmail.com");
        usuario.setPassword("password123");
        usuario = usuarioRepository.save(usuario);

        boolean exists = usuarioRepository.existsById(usuario.getId());

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should return false when user does not exist by ID")
    public void shouldReturnFalseWhenUserDoesNotExistById() {
        boolean exists = usuarioRepository.existsById(999L); // ID que não existe

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Should delete a user")
    public void shouldDeleteUser() {
        Usuario usuario = new Usuario();
        usuario.setName("João");
        usuario.setEmail("joao@gmail.com");
        usuario.setPassword("secret123");
        usuario = usuarioRepository.save(usuario);

        // Verificar se o usuário foi salvo
        assertThat(usuarioRepository.existsById(usuario.getId())).isTrue();

        // Deletar o usuário
        usuarioRepository.deleteById(usuario.getId());

        // Verificar se o usuário foi deletado
        assertThat(usuarioRepository.existsById(usuario.getId())).isFalse();
    }

    @Test
    @DisplayName("Should return empty when finding a non-existing user")
    public void shouldReturnEmptyWhenFindingNonExistingUser() {
        Optional<Usuario> foundUser = usuarioRepository.findById(999L); // ID que não existe

        assertThat(foundUser).isNotPresent();
    }

    @Test
    @DisplayName("Should update a user's name")
    public void shouldUpdateUserName() {
        Usuario usuario = new Usuario();
        usuario.setName("Lucas");
        usuario.setEmail("lucas@gmail.com");
        usuario.setPassword("passwordUpdate");
        usuario = usuarioRepository.save(usuario);

        // Atualizando o nome
        usuario.setName("Lucas Updated");
        usuarioRepository.save(usuario);

        // Verificando se o nome foi atualizado
        Optional<Usuario> updatedUser = usuarioRepository.findById(usuario.getId());
        assertThat(updatedUser).isPresent();
        assertThat(updatedUser.get().getName()).isEqualTo("Lucas Updated");
    }
}



