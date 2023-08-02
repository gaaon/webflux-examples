package com.grizz.wooman.test.app.repository.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@AllArgsConstructor
@Table("USERS")
@Data
public class UserEntity {
    @Id
    private Long id;
    private final String name;
    private final Integer age;
    private final String profileImageId;
    private final String password;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @PersistenceCreator
    public UserEntity(Long id, String name, Integer age,
                      String profileImageId, String password) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.profileImageId = profileImageId;
        this.password = password;
    }

    public UserEntity(String name, Integer age,
                      String profileImageId, String password) {
        this(null, name, age, profileImageId, password);
    }
}
