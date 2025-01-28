package br.com.ifba.usuario.entity;

import jakarta.persistence.*;
import lombok.*;
import br.com.ifba.infrastructure.entity.PersistenceEntity;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;
}

