package com.rest.api.expensetrackerapi.controller;

import com.rest.api.expensetrackerapi.entity.User;
import com.rest.api.expensetrackerapi.entity.UserModel;
import com.rest.api.expensetrackerapi.error.ResoureNotFoundException;
import com.rest.api.expensetrackerapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/profile")
    public ResponseEntity<User> get(@PathVariable long id){
        return new ResponseEntity<User>(userService.read(), HttpStatus.OK);
    }

    @PutMapping("/profile")
    public ResponseEntity<User> update(@RequestBody UserModel user){
        User muser = userService.update(user);

        return new ResponseEntity<User>(muser, HttpStatus.OK);
    }

    @DeleteMapping("/deactivate")
    public ResponseEntity<HttpStatus> delete() throws ResoureNotFoundException{
        userService.delete();
        return  new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }
}
