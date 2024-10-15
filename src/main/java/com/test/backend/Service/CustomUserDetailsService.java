package com.test.backend.Service;

import com.test.backend.Model.User;
import com.test.backend.Repository.UserR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;



import java.util.Collections;


@Service
public class CustomUserDetailsService implements UserDetailsService {

private final UserR userRepository;

    @Autowired
    public CustomUserDetailsService(UserR userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if (user==null) {
            throw  new UsernameNotFoundException("User not found");

        } else {
            //Get role
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword() , Collections.singletonList(authority));
        }


    }
}
