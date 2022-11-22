package pl.mkuchciak.diet_app.user;

import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }
}
