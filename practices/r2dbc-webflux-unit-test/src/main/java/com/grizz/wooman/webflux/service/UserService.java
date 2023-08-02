package com.grizz.wooman.webflux.service;

import com.grizz.wooman.webflux.common.EmptyImage;
import com.grizz.wooman.webflux.common.Image;
import com.grizz.wooman.webflux.common.User;
import com.grizz.wooman.webflux.common.repository.AuthEntity;
import com.grizz.wooman.webflux.common.repository.UserEntity;
import com.grizz.wooman.webflux.repository.UserR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final WebClient imageWebClient;
    private final UserR2dbcRepository userRepository;
    private final R2dbcEntityTemplate entityTemplate;

    public Mono<User> findById(String userId) {
        return userRepository.findById(new Long(userId))
                .flatMap(userEntity -> {
                    String imageId = userEntity.getProfileImageId();

                    Map<String, String> uriVariableMap = Map.of("imageId", imageId);
                    return imageWebClient.get()
                            .uri("/api/images/{imageId}", uriVariableMap)
                            .retrieve()
                            .toEntity(ImageResponse.class)
                            .map(resp -> resp.getBody())
                            .map(imageResp -> new Image(
                                    imageResp.getId(),
                                    imageResp.getName(),
                                    imageResp.getUrl()
                            )).switchIfEmpty(Mono.just(new EmptyImage()))
                            .map(image -> {
                                Optional<Image> profileImage = Optional.empty();
                                if (!(image instanceof EmptyImage)) {
                                    profileImage = Optional.of(image);
                                }
                                return map(userEntity, profileImage);
                            });
                });
    }

    @Transactional
    public Mono<User> createUser(
            String name, Integer age,
            String password, String profileImageId) {

        var newUser = new UserEntity(
                name,
                age,
                profileImageId,
                password
        );

        return userRepository.save(newUser)
                .flatMap(userEntity -> {
                    String token = new TokenGenerator().execute();
                    AuthEntity auth = new AuthEntity(userEntity.getId(), token);

                    return entityTemplate.insert(auth)
                            .map(authEntity -> userEntity);
                })
                .map(userEntity ->
                    map(userEntity, Optional.of(new EmptyImage()))
                );
    }

    private User map(UserEntity userEntity, Optional<Image> profileImage) {
        return new User(
                userEntity.getId().toString(),
                userEntity.getName(),
                userEntity.getAge(),
                profileImage,
                List.of(),
                0L
        );
    }
}
