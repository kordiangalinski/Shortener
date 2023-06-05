package net.kordian.shortener.auth;

<<<<<<< Updated upstream
public record AuthenticationResponse(String token) {
=======
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthenticationResponse {
    private String token;
>>>>>>> Stashed changes
}
