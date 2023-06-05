package com.grizz.wooman.reactor.reactor;

import com.grizz.wooman.reactor.common.Article;
import com.grizz.wooman.reactor.common.EmptyImage;
import com.grizz.wooman.reactor.common.Image;
import com.grizz.wooman.reactor.common.User;
import com.grizz.wooman.reactor.common.repository.UserEntity;
import com.grizz.wooman.reactor.reactor.repository.ArticleReactorRepository;
import com.grizz.wooman.reactor.reactor.repository.FollowReactorRepository;
import com.grizz.wooman.reactor.reactor.repository.ImageReactorRepository;
import com.grizz.wooman.reactor.reactor.repository.UserReactorRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class UserReactorService {
    private final UserReactorRepository userRepository;
    private final ArticleReactorRepository articleRepository;
    private final ImageReactorRepository imageRepository;
    private final FollowReactorRepository followRepository;

    @SneakyThrows
    public Mono<User> getUserById(String id) {
        return userRepository.findById(id)
                .flatMap(this::getUser);
    }

    @SneakyThrows
    private Mono<User> getUser(UserEntity userEntity) {
        Context context = Context.of("user", userEntity);

        var imageMono = imageRepository.findWithContext()
                .map(imageEntity ->
                        new Image(imageEntity.getId(), imageEntity.getName(), imageEntity.getUrl())
                ).onErrorReturn(new EmptyImage())
                .contextWrite(context);

        var articlesMono = articleRepository.findAllWithContext()
                .skip(5)
                .take(2)
                .map(articleEntity ->
                        new Article(articleEntity.getId(), articleEntity.getTitle(), articleEntity.getContent())
                ).collectList()
                .contextWrite(context);

        var followCountMono = followRepository.countWithContext()
                .contextWrite(context);

        return Mono.zip(imageMono, articlesMono, followCountMono)
                .map(resultTuple -> {
                    Image image = resultTuple.getT1();
                    List<Article> articles = resultTuple.getT2();
                    Long followCount = resultTuple.getT3();

                    Optional<Image> imageOptional = Optional.empty();
                    if (!(image instanceof EmptyImage)) {
                        imageOptional = Optional.of(image);
                    }

                    return new User(
                            userEntity.getId(),
                            userEntity.getName(),
                            userEntity.getAge(),
                            imageOptional,
                            articles,
                            followCount
                    );
                });
    }
}
