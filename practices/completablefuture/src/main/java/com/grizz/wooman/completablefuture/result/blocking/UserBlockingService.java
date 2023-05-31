package com.grizz.wooman.completablefuture.result.blocking;

import com.grizz.wooman.completablefuture.result.blocking.repository.ArticleRepository;
import com.grizz.wooman.completablefuture.result.blocking.repository.FollowRepository;
import com.grizz.wooman.completablefuture.result.blocking.repository.ImageRepository;
import com.grizz.wooman.completablefuture.result.blocking.repository.UserRepository;
import com.grizz.wooman.completablefuture.result.common.Article;
import com.grizz.wooman.completablefuture.result.common.Image;
import com.grizz.wooman.completablefuture.result.common.User;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserBlockingService {
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final ImageRepository imageRepository;
    private final FollowRepository followRepository;

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id)
                .map(user -> {
                    var image = imageRepository.findById(user.getProfileImageId())
                            .map(imageEntity -> {
                                return new Image(imageEntity.getId(), imageEntity.getName(), imageEntity.getUrl());
                            });

                    var articles = articleRepository.findAllByUserId(user.getId())
                            .stream().map(articleEntity ->
                                    new Article(articleEntity.getId(), articleEntity.getTitle(), articleEntity.getContent()))
                            .collect(Collectors.toList());

                    var followCount = followRepository.countByUserId(user.getId());

                    return new User(
                            user.getId(),
                            user.getName(),
                            user.getAge(),
                            image,
                            articles,
                            followCount
                    );
                });
    }
}
