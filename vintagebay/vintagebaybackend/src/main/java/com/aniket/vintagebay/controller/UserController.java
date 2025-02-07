package com.aniket.vintagebay.controller;

import com.aniket.vintagebay.exception.UserAlreadyExistsException;
import com.aniket.vintagebay.model.User;
import com.aniket.vintagebay.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @GetMapping("/all")

    public ResponseEntity<List<User>> getUsers(){

        return new ResponseEntity<>(userService.getUsers(), HttpStatus.FOUND);
    }

    @GetMapping("/{email}")

    public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email){
        try{
            User theUser = userService.getUser(email);
            return ResponseEntity.ok(theUser);
        }catch (UserAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching user");
        }
    }
    @DeleteMapping("/delete/{userId}")

    public ResponseEntity<String> deleteUser(@PathVariable("userId") String email){
        try{
            userService.deleteUser(email);
            return ResponseEntity.ok("User deleted successfully");
        }catch (UserAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user: " + e.getMessage());
        }
    }
}
