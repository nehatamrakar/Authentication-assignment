package com.bootcamp.bookshop.user;

import com.bootcamp.bookshop.user.address.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Email is mandatory")
    private String email;
    @NotBlank(message = "Password is mandatory")
    private String password;
    private String token;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();

    public User() {
    }

    private User(String email, String password, Role role, String token) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.token = token;
    }

    public static User create(CreateUserRequest userRequest, String token) {
        String password = "";
        if (!userRequest.getPassword().isEmpty()) {
            password = PASSWORD_ENCODER.encode(userRequest.getPassword());
        }
        return new User(userRequest.getEmail(), password, Role.USER, token);
    }

    public void update(UpdateUserRequest updateUserRequest) {
        firstName = updateUserRequest.getFirstName();
        lastName = updateUserRequest.getLastName();
        mobileNumber = updateUserRequest.getMobileNumber();
        if(updateUserRequest.getAddress() != null) {
            Address address = Address.create(updateUserRequest.getAddress(), this);
            addresses.add(address);
        }
    }
}
