package com.bootcamp.bookshop.user.address;

public class AddressTestBuilder {
    private final Address.AddressBuilder addressBuilder;

    public AddressTestBuilder() {
        this.addressBuilder = Address.builder()
                .lineNoOne("4 Privet Drive")
                .lineNoTwo("Little Whinging")
                .city("Godstone")
                .pinCode("A22 001")
                .country("Surrey");
    }

    public Address build() {
        return addressBuilder.build();
    }
}
