package com.example.mayo.journey.support;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class MayoUserDetails extends User {

    public MayoUserDetails(String id, String password, Collection<? extends GrantedAuthority> authorities) {
        super(id, password, authorities);
    }

    public Long getId() {
        return Long.parseLong(this.getUsername());
    }
}
