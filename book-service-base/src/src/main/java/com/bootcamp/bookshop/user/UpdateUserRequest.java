package com.bootcamp.bookshop.user;

import com.bootcamp.bookshop.user.address.CreateAddressRequest;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
public class UpdateUserRequest {
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private CreateAddressRequest address;
}
