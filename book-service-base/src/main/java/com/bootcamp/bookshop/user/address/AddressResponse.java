package com.bootcamp.bookshop.user.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressResponse {
    private Long id;
    @NotBlank
    private String lineNoOne;
    private String lineNoTwo;
    @NotBlank
    private String city;
    private String state;
    @NotBlank
    private String pinCode;
    @NotBlank
    private String country;
}
