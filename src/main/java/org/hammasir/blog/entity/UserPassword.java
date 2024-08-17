package org.hammasir.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_password")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    transient private String password;

    @OneToOne(mappedBy = "password")
    private User user;
}
