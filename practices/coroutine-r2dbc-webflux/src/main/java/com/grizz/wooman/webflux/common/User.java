package com.grizz.wooman.webflux.common;

import java.util.List;
import java.util.Optional;

public class User {
    private final String id;
    private final String name;
    private final int age;
    private final Optional<Image> profileImage;
    private final List<Article> articleList;
    private final Long followCount;

    public User(String id, String name, int age, Optional<Image> profileImage, List<Article> articleList, Long followCount) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.profileImage = profileImage;
        this.articleList = articleList;
        this.followCount = followCount;
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

    public Optional<Image> getProfileImage() {
        return this.profileImage;
    }

    public List<Article> getArticleList() {
        return this.articleList;
    }

    public Long getFollowCount() {
        return this.followCount;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof User)) return false;
        final User other = (User) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        if (this.getAge() != other.getAge()) return false;
        final Object this$profileImage = this.getProfileImage();
        final Object other$profileImage = other.getProfileImage();
        if (this$profileImage == null ? other$profileImage != null : !this$profileImage.equals(other$profileImage))
            return false;
        final Object this$articleList = this.getArticleList();
        final Object other$articleList = other.getArticleList();
        if (this$articleList == null ? other$articleList != null : !this$articleList.equals(other$articleList))
            return false;
        final Object this$followCount = this.getFollowCount();
        final Object other$followCount = other.getFollowCount();
        if (this$followCount == null ? other$followCount != null : !this$followCount.equals(other$followCount))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof User;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        result = result * PRIME + this.getAge();
        final Object $profileImage = this.getProfileImage();
        result = result * PRIME + ($profileImage == null ? 43 : $profileImage.hashCode());
        final Object $articleList = this.getArticleList();
        result = result * PRIME + ($articleList == null ? 43 : $articleList.hashCode());
        final Object $followCount = this.getFollowCount();
        result = result * PRIME + ($followCount == null ? 43 : $followCount.hashCode());
        return result;
    }

    public String toString() {
        return "User(id=" + this.getId() + ", name=" + this.getName() + ", age=" + this.getAge() + ", profileImage=" + this.getProfileImage() + ", articleList=" + this.getArticleList() + ", followCount=" + this.getFollowCount() + ")";
    }
}
