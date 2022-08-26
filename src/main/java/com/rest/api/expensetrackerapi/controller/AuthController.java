package com.rest.api.expensetrackerapi.controller;

import com.rest.api.expensetrackerapi.entity.JwtResponse;
import com.rest.api.expensetrackerapi.entity.User;
import com.rest.api.expensetrackerapi.entity.UserModel;
import com.rest.api.expensetrackerapi.entity.LoginModel;
import com.rest.api.expensetrackerapi.security.CustomUserDetailsService;
import com.rest.api.expensetrackerapi.service.UserService;
import com.rest.api.expensetrackerapi.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginModel login) throws Exception {

        authentication(login.getEmail(), login.getPassword());

        //we need to generate jwt token
       final UserDetails userDetails = userDetailsService.loadUserByUsername(login.getEmail());

       final String token = jwtTokenUtil.generateToken(userDetails);

        return  new ResponseEntity<JwtResponse>(new JwtResponse(token), HttpStatus.OK);
    }

    private void authentication(String email, String password) throws Exception {

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        }catch (DisabledException e){
            throw new Exception("user disabled");

        }catch (BadCredentialsException e){
            throw new Exception("Bad credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> Save(@Valid @RequestBody UserModel user){
        return new ResponseEntity<User>(userService.createUser(user), HttpStatus.CREATED);
    }
}
