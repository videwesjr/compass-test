package com.compass.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
        private int contactId;
        private String firstName;
        private String lastName;
        private String email;
        private String zipCode;
        private String address;
}
