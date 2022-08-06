package com.bootcamp.bookshop.user.address;

import com.bootcamp.bookshop.user.User;
import com.bootcamp.bookshop.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/addresses")
public class AddressController {
    @Autowired
    private AddressService addressService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<AddressResponse> create(@RequestBody CreateAddressRequest createRequest, Principal principal) {
        User user = userService.findByEmail(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Address address = addressService.create(createRequest, user);
        AddressResponse addressResponse = address.toResponse();
        return new ResponseEntity<>(addressResponse, HttpStatus.CREATED);
    }
}
