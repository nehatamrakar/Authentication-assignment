package com.bootcamp.bookshop.user;

import com.bootcamp.bookshop.user.address.Address;
import com.bootcamp.bookshop.user.address.CreateAddressRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private Validator validator;

    @InjectMocks
    private UserService userService = new UserService();

    @Test
    void shouldCreateUserWithValidInputs() throws InvalidEmailException {
        CreateUserRequest userCredentials = new CreateUserRequestTestBuilder().build();
        User user = new UserTestBuilder().withEmail(userCredentials.getEmail()).build();
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.create(userCredentials);

        ArgumentCaptor<User> argCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(argCaptor.capture());
        assertEquals(userCredentials.getEmail(), argCaptor.getValue().getEmail());
        assertEquals(user.getId(), createdUser.getId());
        assertEquals(user.getEmail(), createdUser.getEmail());
        assertEquals(Role.USER, createdUser.getRole());
    }

    @Test
    void shouldNotCreateUserWhenUserWithSameEmailAlreadyExists() {
        CreateUserRequest userRequest = new CreateUserRequestTestBuilder().build();
        when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(Optional.of(new User()));
        userRepository.save(User.create(userRequest, "abcs"));

        InvalidEmailException createUserException = assertThrows(InvalidEmailException.class,
                () -> userService.create(userRequest));
        assertEquals("User with same email already created", createUserException.getMessage());
    }

    @Test
    void shouldNotCreateUserWhenInputIsInvalid() {
        CreateUserRequest invalidRequest = new CreateUserRequestTestBuilder().withEmptyEmail().build();
        when(validator.validate(any(User.class))).thenThrow(ConstraintViolationException.class);

        assertThrows(ConstraintViolationException.class, () -> userService.create(invalidRequest));
    }

    @Test
    void shouldReturnUserForGivenValidEmail() {
        String validEmailId = "testemail@test.com";
        User user = new UserTestBuilder().build();
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        assertEquals(user.getEmail(), userService.loadUserByUsername(validEmailId).getUsername());
    }

    @Test
    void shouldReturnUserNotFoundExceptionForGivenInValidEmail() {
        String invalidEmailId = "testemail@test";
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(invalidEmailId));
    }

    @Test
    void shouldUpdateFirstNameAndLastNameAndMobileNumberWhenProvided() throws UserNotFoundException {
        UpdateUserRequest updateUserRequest = UpdateUserRequest.builder()
                .firstName("John")
                .lastName("Wick")
                .mobileNumber("1122334455")
                .build();
        User user = new UserTestBuilder().withId(1L).build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.update(1L, updateUserRequest);

        ArgumentCaptor<User> argCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(argCaptor.capture());
        assertEquals(updateUserRequest.getFirstName(), argCaptor.getValue().getFirstName());
        assertEquals(updateUserRequest.getLastName(), argCaptor.getValue().getLastName());
        assertEquals(updateUserRequest.getMobileNumber(), argCaptor.getValue().getMobileNumber());
    }

    @Test
    void shouldAddAddressWhenProvided() throws UserNotFoundException {
        UpdateUserRequest updateUserRequest = UpdateUserRequest.builder()
                .address(CreateAddressRequest.builder().lineNoOne("line one").build())
                .build();
        User user = new UserTestBuilder().withId(1L).build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.update(1L, updateUserRequest);

        ArgumentCaptor<User> argCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(argCaptor.capture());
        List<Address> addresses = argCaptor.getValue().getAddresses();
        assertEquals(1, addresses.size());
        assertEquals("line one", addresses.get(0).getLineNoOne());
    }
}