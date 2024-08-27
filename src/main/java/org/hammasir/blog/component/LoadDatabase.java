package org.hammasir.blog.component;

import org.hammasir.blog.dto.UserDto;
import org.hammasir.blog.entity.Post;
import org.hammasir.blog.entity.Role;
import org.hammasir.blog.entity.User;
import org.hammasir.blog.repository.PostRepository;
import org.hammasir.blog.repository.UserRepository;
import org.hammasir.blog.service.UserService;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

//    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PostRepository postRepository, UserService userService) {
        return args -> {
            // Create users
            User hadi = userService.add(
                    UserDto.builder()
                            .name("hadi")
                            .username("hadi")
                            .password("password")
                            .role(Role.USER)
                            .build()
            );
            User kambiz = userService.add(
                    UserDto.builder()
                            .name("kambiz")
                            .username("kambiz1400")
                            .password("pass")
                            .role(Role.ADMIN)
                            .build()
            );

            log.info("Preloading {}", hadi);
            log.info("Preloading {}", kambiz);

            Point mashhad=createPoint(59.6048042, 36.298192);
            Point tabriz=createPoint(46.2868574, 38.0775595);
            Point neyshaboor=createPoint(58.7927684, 36.209834);
            Point tehran=createPoint(51.4179604,35.6884244);


            // Preload posts using @Builder
            Post post1 = Post.builder()
                    .title("First Post by Hadi")
                    .content("This is the content of the first post by Hadi.")
                    .author(hadi)
                    .createdDate(LocalDateTime.of(2023, 5, 10, 10, 30))
                    .updatedDate(LocalDateTime.of(2023, 5, 10, 12, 45))
                    .location(mashhad)
                    .likes(10)
                    .build();

            Post post2 = Post.builder()
                    .title("Second Post by Kambiz")
                    .content("This is the content of the first post by Kambiz.")
                    .author(kambiz)
                    .createdDate(LocalDateTime.of(2023, 6, 15, 9, 15))
                    .updatedDate(LocalDateTime.of(2023, 6, 15, 10, 20))
                    .location(neyshaboor)
                    .likes(25)
                    .build();

            Post post3 = Post.builder()
                    .title("Third Post by Hadi")
                    .content("This is another post by Hadi, sharing some insights.")
                    .author(hadi)
                    .createdDate(LocalDateTime.of(2023, 7, 20, 11, 0))
                    .updatedDate(LocalDateTime.of(2023, 7, 20, 11, 50))
                    .location(tabriz)

                    .likes(15)
                    .build();

            Post post4 = Post.builder()
                    .title("Fourth Post by Kambiz")
                    .content("Kambiz is writing another post on a different topic.")
                    .author(kambiz)
                    .createdDate(LocalDateTime.of(2023, 8, 5, 14, 30))
                    .updatedDate(LocalDateTime.of(2023, 8, 5, 15, 30))
                    .location(mashhad)

                    .likes(30)
                    .build();

            Post post5 = Post.builder()
                    .title("Fifth Post by Hadi")
                    .content("Hadi continues with yet another interesting post.")
                    .author(hadi)
                    .createdDate(LocalDateTime.of(2023, 9, 1, 16, 45))
                    .updatedDate(LocalDateTime.of(2023, 9, 1, 17, 20))
                    .location(tabriz)
                    .likes(20)
                    .build();

            Post post6 = Post.builder()
                    .title("Sixth Post by Kambiz")
                    .content("Kambiz is sharing his thoughts on recent developments.")
                    .author(kambiz)
                    .createdDate(LocalDateTime.of(2023, 9, 10, 8, 10))
                    .updatedDate(LocalDateTime.of(2023, 9, 10, 9, 0))
                    .location(tehran)
                    .likes(35)
                    .build();

            // Save posts to the repository
            postRepository.saveAll(List.of(post1, post2, post3, post4, post5, post6));

            log.info("Preloaded posts for Hadi and Kambiz.");
        };
    }

    public Point createPoint(double x,double y){
        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate coord = new Coordinate(x, y);
        Point point = geometryFactory.createPoint(coord);
        return point;
    }

}
