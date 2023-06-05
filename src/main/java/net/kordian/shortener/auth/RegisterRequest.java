package net.kordian.shortener.auth;

<<<<<<< Updated upstream
public record RegisterRequest(String firstName, String lastName, String email, String password) {
=======
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
>>>>>>> Stashed changes
}
