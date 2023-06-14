package com.grizz.wooman.webflux.controller.dto;

public class SignupUserRequest {
    private String name;
    private Integer age;
    private String password;
    private String profileImageId;

    public SignupUserRequest() {
    }

    public String getName() {
        return this.name;
    }

    public Integer getAge() {
        return this.age;
    }

    public String getPassword() {
        return this.password;
    }

    public String getProfileImageId() {
        return this.profileImageId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfileImageId(String profileImageId) {
        this.profileImageId = profileImageId;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SignupUserRequest)) return false;
        final SignupUserRequest other = (SignupUserRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$age = this.getAge();
        final Object other$age = other.getAge();
        if (this$age == null ? other$age != null : !this$age.equals(other$age)) return false;
        final Object this$password = this.getPassword();
        final Object other$password = other.getPassword();
        if (this$password == null ? other$password != null : !this$password.equals(other$password)) return false;
        final Object this$profileImageId = this.getProfileImageId();
        final Object other$profileImageId = other.getProfileImageId();
        if (this$profileImageId == null ? other$profileImageId != null : !this$profileImageId.equals(other$profileImageId))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SignupUserRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $age = this.getAge();
        result = result * PRIME + ($age == null ? 43 : $age.hashCode());
        final Object $password = this.getPassword();
        result = result * PRIME + ($password == null ? 43 : $password.hashCode());
        final Object $profileImageId = this.getProfileImageId();
        result = result * PRIME + ($profileImageId == null ? 43 : $profileImageId.hashCode());
        return result;
    }

    public String toString() {
        return "SignupUserRequest(name=" + this.getName() + ", age=" + this.getAge() + ", password=" + this.getPassword() + ", profileImageId=" + this.getProfileImageId() + ")";
    }
}
