package org.example.tranfercar.service;


import org.example.tranfercar.DTO.LoginRequest;
import org.example.tranfercar.DTO.UserRegistrationRequest;
import org.example.tranfercar.Result;
import org.example.tranfercar.domain.User;
import org.example.tranfercar.repository.UserRepository;
import org.example.tranfercar.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public Result<User> registerUser(UserRegistrationRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            return Result.failure("User with that e-mail already exists!");
        } else if (userRepository.existsByPhone(request.phone())) {
            return Result.failure("User with that phone number already exists!");
        } else {
            User user = new User();
            user.setEmail(request.email());
            user.setName(request.name());
            user.setPhone(request.phone());
            user.setPassword(hashPassword(request.password()));

            userRepository.save(user);
            return Result.success(user);
        }
    }

    public String hashPassword(String password) {
        return encoder.encode(password);
    }

    public Result<User> login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email());
        if (user == null) {
            return Result.failure("User with that e-mail do not exist!");
        } else if (!encoder.matches(request.password(), user.getPassword()))  {
            return Result.failure("Incorrect password!");
        } else {
            return Result.success(user);
        }
    }
}
