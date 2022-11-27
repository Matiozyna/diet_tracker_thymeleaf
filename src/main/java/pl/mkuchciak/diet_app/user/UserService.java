package pl.mkuchciak.diet_app.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mkuchciak.diet_app.meal.MealRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    UserRepository userRepository;
    UserDtoConverter userDtoConverter;
    MealRepository mealRepository;

    public UserService(UserRepository userRepository, UserDtoConverter userDtoConverter, MealRepository mealRepository) {
        this.userRepository = userRepository;
        this.userDtoConverter = userDtoConverter;
        this.mealRepository = mealRepository;
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public Optional<UserDto> getUserDtoById(Long id){
        return userRepository.findById(id).map(userDtoConverter::convertToDto);
    }

    public List<UserDto> getAllUsers(){
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users.stream().map(user -> userDtoConverter.convertToDto(user)).toList();
    }

    public Optional<UserDto> replaceUser(UserDto userDto, Long id){
        if(userRepository.existsById(id)){
            return Optional.empty();
        }
        userDto.setId(id);
        return convertAndSave(userDto);
    }

    public Optional<UserDto> saveUser(UserDto userDto) {
        System.out.println(userDto.getId());
        if(userDto.getId() != null && userRepository.existsById(userDto.getId())){
            return Optional.empty();
        }
        return convertAndSave(userDto);
    }

    private Optional<UserDto> convertAndSave(UserDto userDto) {
        User user = userDtoConverter.convertToEntity(userDto);
        User savedUser = userRepository.save(user);
        return Optional.of(userDtoConverter.convertToDto(savedUser));
    }

    @Transactional
    public Optional<UserDto> deleteUser(Long id){

        if(userRepository.existsById(id)){
            User user = userRepository.findById(id).orElseThrow();

            mealRepository.deleteAllByUser(user);
            userRepository.deleteById(id);
            return Optional.of(userDtoConverter.convertToDto(user));
        }else
            return Optional.empty();

    }
}
