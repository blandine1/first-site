package com.site.first.fistsite.sec;

import com.site.first.fistsite.DAO.UserRepository;
import com.site.first.fistsite.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =userRepository.findUserByUsername(username);
        if (user==null){
            throw new UsernameNotFoundException("cet utilisateur est absent de la base");
        }
        return new MyUserDetail(user);
    }
}
