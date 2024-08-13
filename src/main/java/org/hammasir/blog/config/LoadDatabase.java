package org.hammasir.blog.config;

import org.hammasir.blog.entity.Post;
import org.hammasir.blog.entity.Role;
import org.hammasir.blog.entity.User;
import org.hammasir.blog.repository.PostRepository;
import org.hammasir.blog.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PostRepository postRepository) {
        return args -> {
            User hadi = User.builder()
                    .name("Hadi")
                    .username("hadi_user")
                    .password("password1")
                    .role(Role.USER)
                    .build();

            User kambiz = User.builder()
                    .name("Kambiz")
                    .username("kambiz_user")
                    .password("password2")
                    .role(Role.ADMIN)
                    .build();

            log.info("Preloading {}", userRepository.save(hadi));
            log.info("Preloading {}", userRepository.save(kambiz));

            // Preloading posts using @Builder
            Post post1 = Post.builder()
                    .title("First Post")
                    .content("This is the content of the first post.")
                    .author(hadi)
                    .build();

            Post post2 = Post.builder()
                    .title("Second Post")
                    .content("This is the content of the second post.")
                    .author(kambiz)
                    .build();

            Post post3 = Post.builder()
                    .title("Third Post")
                    .content("This is the content of the third post.")
                    .author(hadi)
                    .build();

            log.info("Preloading {}", postRepository.save(post1));
            log.info("Preloading {}", postRepository.save(post2));
            log.info("Preloading {}", postRepository.save(post3));
        };
    }
}
