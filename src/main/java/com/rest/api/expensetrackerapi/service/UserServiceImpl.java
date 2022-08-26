package com.rest.api.expensetrackerapi.service;

import com.rest.api.expensetrackerapi.entity.User;
import com.rest.api.expensetrackerapi.entity.UserModel;
import com.rest.api.expensetrackerapi.error.ItemAlreadyExitException;
import com.rest.api.expensetrackerapi.error.ResoureNotFoundException;
import com.rest.api.expensetrackerapi.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    UserRepository repo;

    @Override
    public User createUser(UserModel user) {

        if (repo.existsByEmail(user.getEmail())){
            throw new ItemAlreadyExitException("User is already registered with the email " + user.getEmail());
        }
        User newUser = new User();
        BeanUtils.copyProperties(user, newUser);
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return repo.save(newUser);

    }

    @Override
    public User read() {
        Long userId = getLoggedInUser().getId();
        return repo.findById(userId).orElseThrow(()->
                new ResoureNotFoundException("User not found for the id "));
    }

    @Override
    public User update(UserModel user) {
        User existinguser = read();
        existinguser.setName(user.getName() != null ? user.getName() :  existinguser.getName());
        existinguser.setEmail(user.getEmail() != null ? user.getEmail() :  existinguser.getEmail());
        existinguser.setPassword(user.getPassword() != null ? bcryptEncoder.encode(user.getPassword()) :  existinguser.getPassword());
        existinguser.setAge(user.getAge() != null ? user.getAge() :  existinguser.getAge());

        return repo.save( existinguser);
    }

    @Override
    public void delete() {
        User user = read();
        repo.delete(user);
    }

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return repo.findByEmail(email).orElseThrow(()->
                new UsernameNotFoundException("User not found for the email" +email));
    }
}
