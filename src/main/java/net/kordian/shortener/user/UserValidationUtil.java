package net.kordian.shortener.user;

public class UserValidationUtil {
    public static void validateUserDTO(UserDTO userDTO) {
        if (userDTO.getFirstName() == null || userDTO.getFirstName().isEmpty()) {
            throw new UserValidationException("First name is required");
        }
        if (userDTO.getLastName() == null || userDTO.getLastName().isEmpty()) {
            throw new UserValidationException("Last name is required");
        }
        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            throw new UserValidationException("Email is required");
        }
    }
}
