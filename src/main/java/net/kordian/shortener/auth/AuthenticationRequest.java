package net.kordian.shortener.auth;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationRequest {
    private String email;
    private String password;
}
