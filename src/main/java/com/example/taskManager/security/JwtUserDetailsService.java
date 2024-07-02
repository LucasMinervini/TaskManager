package com.example.taskManager.security;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.taskManager.model.Role;
import com.example.taskManager.model.User;
import com.example.taskManager.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> userOptional = Optional.of(userRepository.findByUsername(username));

    if (userOptional.isEmpty()) {
        throw new UsernameNotFoundException("User not found with username: " + username);
    }

    User user = userOptional.get();

    // Aquí obtienes los roles del usuario, asumiendo que getRoles() devuelve una colección de GrantedAuthority
    Collection<Role> authorities = user.getRoles().stream()
            .map(role -> role) // Asegúrate de que Role implemente GrantedAuthority
            .collect(Collectors.toList());

    return new org.springframework.security.core.userdetails.User(
        user.getUsername(), 
        user.getPassword(), 
        (Collection<? extends GrantedAuthority>) authorities
    );
    }
}
