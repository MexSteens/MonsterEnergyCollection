package fontys.s3.MonsterEnergyCollection.Presentation;

import fontys.s3.MonsterEnergyCollection.Business.UserCrud;
import fontys.s3.MonsterEnergyCollection.Business.Entity.UserEntity;
import fontys.s3.MonsterEnergyCollection.Business.IUserDAO;
import fontys.s3.MonsterEnergyCollection.DataTransferObject.UserDTO;
import fontys.s3.MonsterEnergyCollection.Domain.SearchCriteria;
import fontys.s3.MonsterEnergyCollection.Security.IsAuthenticated.IsAuthenticated;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserCrud userCrud;

    private final IUserDAO api;

    public UserController(UserCrud userCrud, IUserDAO iUserDAO){
        this.userCrud = userCrud;
        this.api = iUserDAO;
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @GetMapping("/user")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        try {
            List<UserEntity> userEntities = userCrud.GetAllUsers();
            if (userEntities.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            for (UserEntity user : userEntities){
                user.setPassword(null);
            }
            return new ResponseEntity<>(userEntities, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @GetMapping("/users")
    @ResponseBody
    public List<UserEntity> findAll(@RequestParam(value = "search", required = false) String search) {
        List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                params.add(new SearchCriteria(matcher.group(1),
                        matcher.group(2), matcher.group(3)));
            }
        }
        return api.searchUser(params);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable("id") long id) {
        UserEntity userData = userCrud.GetUserById(id).orElse(null);
        if (userData != null) {
            userData.setPassword(null);
            return new ResponseEntity<>(userData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/user")
    public ResponseEntity<UserEntity> createUser(@Valid @RequestBody UserDTO userDTO, Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        try {
            UserEntity _userEntity = new UserEntity(userDTO.getName(), userDTO.getUsername());
            userCrud.CreateUser(new UserEntity(userDTO.getName(), userDTO.getUsername(), userDTO.getPassword()));
            return new ResponseEntity<>(_userEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @IsAuthenticated
//    @PutMapping("/user/{id}")
//    public ResponseEntity<UserEntity> updateUser(@PathVariable("id") long id, @RequestBody UserEntity userEntity) {
//        return new ResponseEntity<>(userCrud.UpdateUser(id, userEntity), HttpStatus.OK);
//    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @PutMapping("/user/role/{id}")
    public ResponseEntity<UserEntity> updateUserRole(@PathVariable("id") long id, @RequestBody String role) {
        ResponseEntity<UserEntity> rep = new ResponseEntity<>(userCrud.UpdateUserRole(id, role), HttpStatus.OK);
        return rep;
    }
}
