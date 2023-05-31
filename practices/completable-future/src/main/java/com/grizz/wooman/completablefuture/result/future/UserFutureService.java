package com.grizz.wooman.completablefuture.result.future;

import com.grizz.wooman.completablefuture.result.common.Article;
import com.grizz.wooman.completablefuture.result.common.Image;
import com.grizz.wooman.completablefuture.result.common.User;
import com.grizz.wooman.completablefuture.result.common.repository.UserEntity;
import com.grizz.wooman.completablefuture.result.future.repository.ArticleFutureRepository;
import com.grizz.wooman.completablefuture.result.future.repository.FollowFutureRepository;
import com.grizz.wooman.completablefuture.result.future.repository.ImageFutureRepository;
import com.grizz.wooman.completablefuture.result.future.repository.UserFutureRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserFutureService {
    private final UserFutureRepository userRepository;
    private final ArticleFutureRepository articleRepository;
    private final ImageFutureRepository imageRepository;
    private final FollowFutureRepository followRepository;

    @SneakyThrows
    public CompletableFuture<Optional<User>> getUserById(String id) {
        return userRepository.findById(id)
                .thenCompose(this::getUser);
    }

    @SneakyThrows
    private CompletableFuture<Optional<User>> getUser(Optional<UserEntity> userOptional) {
        if (userOptional.isEmpty()) return CompletableFuture.completedFuture(Optional.empty());

        var user = userOptional.get();
        var imageFuture = imageRepository.findById(user.getProfileImageId())
                .thenApply(imageEntityOptional ->
                        imageEntityOptional.map(imageEntity -> {
                            return new Image(imageEntity.getId(), imageEntity.getName(), imageEntity.getUrl());
                        })
                );

        var articlesFuture = articleRepository.findAllByUserId(user.getId())
                .thenApply(articleEntites ->
                        articleEntites.stream().map(articleEntity ->
                                        new Article(articleEntity.getId(), articleEntity.getTitle(), articleEntity.getContent()))
                                .collect(Collectors.toList())
                );

        var followCountFuture = followRepository.countByUserId(user.getId());

        return CompletableFuture.allOf(imageFuture, articlesFuture, followCountFuture)
                .thenApply(v -> {
                    try {
                        var image = imageFuture.get();
                        var articles = articlesFuture.get();
                        var followCount = followCountFuture.get();

                        return Optional.of(
                                new User(
                                        user.getId(),
                                        user.getName(),
                                        user.getAge(),
                                        image,
                                        articles,
                                        followCount
                                )
                        );
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
