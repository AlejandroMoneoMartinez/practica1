package com.example.practica1.service;

import com.example.practica1.entity.User;
import com.example.practica1.entity.UserRole;
import com.example.practica1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserSecurityService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userDb = userRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found"));
        List<GrantedAuthority> authorityList = buildAuthorities(userDb.getUserRoleList());
        return buildUser(userDb, authorityList);
    }

    private org.springframework.security.core.userdetails.User buildUser(User user, List<GrantedAuthority> authorityList){
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.isEnabled(),
                true, true, true, authorityList);
    }

    private List<GrantedAuthority> buildAuthorities (List<UserRole> userRoles){
        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
        userRoles.forEach(ur -> grantedAuthoritySet.add(new SimpleGrantedAuthority(ur.getRole().getName())));
        return new ArrayList<>(grantedAuthoritySet);
    }
}
