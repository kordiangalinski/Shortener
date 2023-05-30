package net.kordian.shortener.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO createUser(UserDTO userDTO) {
        UserValidationUtil.validateUserDTO(userDTO);
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());

        user = userRepository.save(user);

        return convertToUserDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    public Page<UserDTO> getAllUsersPaged(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(this::convertToUserDTO);
    }

    public UserDTO getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return convertToUserDTO(user);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public UserDTO updateUser(Integer id, UserDTO updatedUserDTO) {
        UserValidationUtil.validateUserDTO(updatedUserDTO);
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        existingUser.setFirstName(updatedUserDTO.getFirstName());
        existingUser.setLastName(updatedUserDTO.getLastName());
        existingUser.setEmail(updatedUserDTO.getEmail());
        existingUser.setPassword(updatedUserDTO.getPassword());
        existingUser.setRole(updatedUserDTO.getRole());

        existingUser = userRepository.save(existingUser);

        return convertToUserDTO(existingUser);
    }

    private UserDTO convertToUserDTO(User user) {
        return new UserDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
    }
}
