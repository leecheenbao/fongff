package fongff.security;

import fongff.model.User;
import fongff.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetialsServiceImpl implements UserDetailsService {

    @Autowired
    private UserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername: " + username);
        User user = userService.findUser(username);
        System.out.println("auth: " + user.getRole());
        System.out.println(user.toString());

        // 1. 查詢用戶是否存在 ?
        if (user.getName().isEmpty()) {
            throw new UsernameNotFoundException("Not found!");
        }

        // 2. 取得相關資料並進行密碼比對
        String password = user.getPassword();
        User.Role authority = user.getRole();
        return new org.springframework.security.core.userdetails.User(username,
                password,
                AuthorityUtils.commaSeparatedStringToAuthorityList(String.valueOf(authority)));
    }

}
