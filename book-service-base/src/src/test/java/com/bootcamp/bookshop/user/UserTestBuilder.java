package com.bootcamp.bookshop.user;

import com.bootcamp.bookshop.user.User.UserBuilder;

import java.util.ArrayList;

public class UserTestBuilder {
    private final UserBuilder userBuilder;

    public UserTestBuilder() {
        userBuilder = User.builder()
                .email("testemail@test.com")
                .role(Role.USER)
                .addresses(new ArrayList<>())
                .password(User.PASSWORD_ENCODER.encode("foobar"));
    }

    public static CreateUserRequest buildCreateUserRequest() {
        return new CreateUserRequest("testemail@test.com", "foobar");
    }

    public User build() {
        return userBuilder.build();
    }

    public UserTestBuilder withEmail(String email) {
        userBuilder.email(email);
        return this;
    }

    public UserTestBuilder withId(long id) {
        userBuilder.id(id);
        return this;
    }
}
