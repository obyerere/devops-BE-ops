package com.grokonez.jwtauthentication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grokonez.jwtauthentication.message.response.ApiResponse;
import com.grokonez.jwtauthentication.model.*;
import com.grokonez.jwtauthentication.repository.RoleRepository;
import com.grokonez.jwtauthentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/registered-client")
public class RegisteredUser {

  @Autowired UserRepository userRepository;

  @Autowired RoleRepository roleRepository;

  @GetMapping("/{id}")
  public ApiResponse<UserDto> getOneUser(@PathVariable int id) {
    return new ApiResponse<>(
        HttpStatus.OK.value(),
        "User fetched successfully.",
        userRepository.findByid(Long.valueOf(id)));
  }

  @PutMapping("/{id}")
  public ApiResponse<User> update(@PathVariable int id, @RequestBody String userDto)
      throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    UpdateUserDto updateUserDto = mapper.readValue(userDto, UpdateUserDto.class);
    try {
       User user = userRepository.findByid(Long.valueOf(id)).get();
      User updateUser = new User();
      //User deleteUserRecord = userRepository.findByUsername(user.getUsername()).get();
   //   updateUser = deleteUserRecord;
        Set<String> strRoles = updateUserDto.getRoles();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(adminRole);

                    break;
                case "pm":
                    Role pmRole = roleRepository.findByName(RoleName.ROLE_PM)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(pmRole);

                    break;
                default:
                    Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(userRole);
            }
        });
      updateUser.setUsername(updateUserDto.getUsername());
      updateUser.setName(updateUserDto.getName());
      updateUser.setEmail(updateUserDto.getEmail());
      updateUser.setPassword(updateUserDto.getPassword());
      updateUser.setTenant(updateUserDto.getTenant());
      updateUser.setRoles(roles);
      userRepository.delete(user);
      userRepository.save(updateUser);

      return new ApiResponse<>(HttpStatus.OK.value(), "user saved", updateUser);
    } catch (Exception e) {
      return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "user not saved", null);
    }
  }

  @DeleteMapping("/{id}")
  public ApiResponse<Void> delete(@PathVariable int id) {
    userRepository.deleteById(Long.valueOf(id));
    return new ApiResponse<>(HttpStatus.OK.value(), "user deleted", null);
  }
}
