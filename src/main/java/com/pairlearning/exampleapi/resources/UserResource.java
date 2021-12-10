package com.pairlearning.exampleapi.resources;

import com.pairlearning.exampleapi.domain.User;
import com.pairlearning.exampleapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> userMap){
       String email = (String) userMap.get("email");
       String password = (String) userMap.get("password");
       User user = userService.validateUser(email, password);
       Map<String, String> map = new HashMap<>();
       map.put("message","loggedIn successfully");
       return new ResponseEntity<>(map, HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, Object> userMap){
        String email= (String) userMap.get("email");
        String password= (String) userMap.get("password");
        User user = userService.registerUser(email, password);
        Map<String, String> map = new HashMap<>();
        map.put("message", "successfully registered");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

     @PutMapping("/update")
    public ResponseEntity<Map<String, String>> updatePassword(@RequestBody Map<String, Object> userMap){
         String email = (String) userMap.get("email");
         String password = (String) userMap.get("password");
         String newpassword = (String) userMap.get("newpassword");
         userService.updatePassword(email, password,newpassword);
         Map<String, String> map = new HashMap<>();
         map.put("message", "password updated");
         return new ResponseEntity<>(map, HttpStatus.OK);
     }

     @GetMapping("/getall")
     public ResponseEntity<List<User>> getAllUsers(HttpServletRequest request){
        List<User> users = userService.fetchAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
     }

     @DeleteMapping("/delete")
     public ResponseEntity<Map<String, Boolean>> deleteUser(HttpServletRequest request) {
         String email = (String) request.getAttribute("email");
         String password = (String) request.getAttribute("password");
         userService.deleteUser(email, password);
         Map<String, Boolean> map = new HashMap<>();
         map.put("success", true);
         return new ResponseEntity<>(map, HttpStatus.OK);
     }

}
