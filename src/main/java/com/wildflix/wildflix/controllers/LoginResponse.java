package com.wildflix.wildflix.controllers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class LoginResponse {

    private String jwt;
}
