package com.example.mayo.journey.service.impl;

import com.example.mayo.journey.domain.jdbc.User;
import com.example.mayo.journey.repository.jdbc.UserRepository;
import com.example.mayo.journey.support.MayoUserDetails;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ApiUserDetailsService implements UserDetailsService {

    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserById(Long.parseLong(username));

        if (Boolean.TRUE.equals(user.getBlocked())) {
            throw new UsernameNotFoundException("msg");
        }

        String id = user.getId().toString();
        String password = user.getPassword();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
        return new MayoUserDetails(id, password, List.of(grantedAuthority));
    }
}
