package pl.mkuchciak.diet_app.user;

import org.springframework.stereotype.Service;

@Service
public class UserDtoConverter {

    UserRepository userRepository;

    public UserDtoConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto convertToDto(User user){
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getAge()
        );
    }

    public User convertToEntity(UserDto userDto){
        User user = new User();
        if(userDto.getId()!=null){
            user.setId(userDto.getId());
        }
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setAge(userDto.getAge());
        user.setEmail(user.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return user;
    }
}
