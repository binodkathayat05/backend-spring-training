package com.exam.model;

import lombok.*;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest {
    String username;
    String password;
}
