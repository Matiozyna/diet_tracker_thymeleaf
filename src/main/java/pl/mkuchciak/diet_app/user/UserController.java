package pl.mkuchciak.diet_app.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        return userService.getUserDtoById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto){
        return userService.saveUser(userDto).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    ResponseEntity<UserDto> replaceUser(@RequestBody UserDto userDto, @PathVariable Long id){
        Optional<UserDto> replacedUser = userService.replaceUser(userDto, id);
        if(replacedUser.isEmpty()){
            return ResponseEntity.notFound().build();
        }else
            return ResponseEntity.ok(replacedUser.get());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> replaceUser(@PathVariable Long id){
        return userService.deleteUser(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
