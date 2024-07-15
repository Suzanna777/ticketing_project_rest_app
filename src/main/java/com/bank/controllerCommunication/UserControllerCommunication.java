package com.bank.controllerCommunication;

import com.bank.dtoData.ResponseWrapper;
import com.bank.dtoData.UserDatadto;
import com.bank.serviceMethod.UserServiceMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/user")
public class UserControllerCommunication {









    private final UserServiceMethod userServiceMethod;

    public UserControllerCommunication(UserServiceMethod userServiceMethod) {
        this.userServiceMethod = userServiceMethod;
    }


    @GetMapping
    public ResponseEntity<ResponseWrapper> getUsers(){
        List<UserDatadto> userDTOList = userServiceMethod.listAllUsers();
        return ResponseEntity.ok(new ResponseWrapper("Users are successfully retrieves",userDTOList, HttpStatus.OK));
    }

    @GetMapping("/{username}")
    public ResponseEntity<ResponseWrapper> getUserByUserName(@PathVariable("username") String userName){
        UserDatadto user = userServiceMethod.findByUserName(userName);
        return ResponseEntity.ok(new ResponseWrapper("User is successfully retrieved",user, HttpStatus.OK));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserDatadto user){
        userServiceMethod.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("User is successfully created",HttpStatus.CREATED));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper> updateUser(@RequestBody UserDatadto user){
        userServiceMethod.update(user);
        return ResponseEntity.ok(new ResponseWrapper("User is successfully updated",HttpStatus.OK));

    }

    @DeleteMapping("/{username}")
    public ResponseEntity<ResponseWrapper> deleteUser(@PathVariable("username") String userName){
        userServiceMethod.delete(userName);
        return ResponseEntity.ok(new ResponseWrapper("User is successfully deleted",HttpStatus.OK));

    }




}

