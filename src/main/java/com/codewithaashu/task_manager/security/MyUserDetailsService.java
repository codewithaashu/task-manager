package com.codewithaashu.task_manager.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codewithaashu.task_manager.Entity.User;
import com.codewithaashu.task_manager.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // get user by username. here username is email
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        // userDetails is also an interface
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.singleton(new SimpleGrantedAuthority("User"));
            }

            @Override
            public String getPassword() {

                return user.getPassword();
            }

            @Override
            public String getUsername() {
                return user.getEmail();
            }
        };
    }
}
