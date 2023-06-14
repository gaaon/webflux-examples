package com.grizz.wooman.webflux.controller.dto;

import java.util.Optional;

public class UserResponse {
    private final String id;
    private final String name;
    private final int age;
    private final Long followCount;
    private final Optional<ProfileImageResponse> image;

    public UserResponse(String id, String name, int age, Long followCount, Optional<ProfileImageResponse> image) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.followCount = followCount;
        this.image = image;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public Long getFollowCount() {
        return this.followCount;
    }

    public Optional<ProfileImageResponse> getImage() {
        return this.image;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserResponse)) return false;
        final UserResponse other = (UserResponse) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        if (this.getAge() != other.getAge()) return false;
        final Object this$followCount = this.getFollowCount();
        final Object other$followCount = other.getFollowCount();
        if (this$followCount == null ? other$followCount != null : !this$followCount.equals(other$followCount))
            return false;
        final Object this$image = this.getImage();
        final Object other$image = other.getImage();
        if (this$image == null ? other$image != null : !this$image.equals(other$image)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserResponse;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        result = result * PRIME + this.getAge();
        final Object $followCount = this.getFollowCount();
        result = result * PRIME + ($followCount == null ? 43 : $followCount.hashCode());
        final Object $image = this.getImage();
        result = result * PRIME + ($image == null ? 43 : $image.hashCode());
        return result;
    }

    public String toString() {
        return "UserResponse(id=" + this.getId() + ", name=" + this.getName() + ", age=" + this.getAge() + ", followCount=" + this.getFollowCount() + ", image=" + this.getImage() + ")";
    }
}