package org.example.medialibrary.service;

import org.example.medialibrary.entity.AppUser;
import org.example.medialibrary.entity.Role;
import org.example.medialibrary.repository.AppUserRepository;
import org.example.medialibrary.repository.RoleRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;



@Service
public class AppUserService implements UserDetailsService {

    @Autowired
    private AppUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

//    @Transactional
//    public AppUser registerUser(AppUser user) {
//        if (userRepository.existsByUsername(user.getUsername())) {
//            throw new IllegalStateException("Username already taken");
//        }
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.getRoles().add(new Role("ROLE_USER"));
//        return userRepository.save(user);
//    }

    @Transactional
    public AppUser registerUser(AppUser user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalStateException("Username already taken");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Найти или создать роль "ROLE_USER"
        Role roleUser = roleRepository.findByAuthority("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));

        user.getRoles().add(roleUser); // Добавляем роль
        return userRepository.save(user);
    }
}

