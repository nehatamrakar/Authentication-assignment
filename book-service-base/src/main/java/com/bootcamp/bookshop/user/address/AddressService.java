package com.bootcamp.bookshop.user.address;

import com.bootcamp.bookshop.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public Address create(@Valid CreateAddressRequest createRequest, User user) {
        Address address = Address.create(createRequest, user);
        return addressRepository.save(address);
    }
}
