package net.kordian.shortener.auth;

<<<<<<< Updated upstream
public record AuthenticationRequest(String email, String password) {
=======
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationRequest {
    private String email;
    private String password;
>>>>>>> Stashed changes
}
