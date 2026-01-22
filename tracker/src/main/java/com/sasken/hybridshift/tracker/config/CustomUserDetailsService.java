package com.sasken.hybridshift.tracker.config;

import com.sasken.hybridshift.tracker.entity.AppUser;
import com.sasken.hybridshift.tracker.repository.AppUserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public CustomUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return new User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role ->
                                new SimpleGrantedAuthority(role.getName()))
                        .toList()
        );
    }
}
