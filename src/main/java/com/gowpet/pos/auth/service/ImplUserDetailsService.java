package com.gowpet.pos.auth.service;

import com.gowpet.pos.user.service.FindByUsernameService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
class ImplUserDetailsService implements UserDetailsService {
    private final FindByUsernameService svc;

    ImplUserDetailsService(FindByUsernameService svc) {
        this.svc = svc;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = svc.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s is not found", username)));

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(new SimpleGrantedAuthority("USER"))
                .build();
    }
}
