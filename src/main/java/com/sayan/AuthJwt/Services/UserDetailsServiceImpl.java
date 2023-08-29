package com.sayan.AuthJwt.Services;

import com.sayan.AuthJwt.DTO.CreateReq;
import com.sayan.AuthJwt.Model.AppUsers;
import com.sayan.AuthJwt.Repository.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AppUserRepo appUserRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUsers fetchedUser = appUserRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + "not found"));

        var authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(fetchedUser.getRole()));

        return new User(fetchedUser.getUsername(), fetchedUser.getPassword(),authorities);
    }
    public AppUsers createUser(CreateReq createReq){
        String username = createReq.getUsername();
        String password = createReq.getPassword();
        String role = createReq.getRole();

        boolean isExists = appUserRepo.existsByUsername(username);

        if(isExists){
            throw new RuntimeException("user already exists");
        }
        AppUsers appUsers = new AppUsers();
        appUsers.setUsername(username);
        appUsers.setPassword(passwordEncoder.encode(password));
        appUsers.setRole(role);
        return appUserRepo.save(appUsers);

    }
}
