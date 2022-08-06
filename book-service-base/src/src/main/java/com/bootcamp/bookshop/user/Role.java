package com.bootcamp.bookshop.user;

public enum Role {
    USER;

    String authority() {
        return "ROLE_" + this.name();
    }
}
