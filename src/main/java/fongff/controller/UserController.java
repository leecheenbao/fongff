package fongff.controller;

import fongff.model.User;
import fongff.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/myInfo")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Map<String, User> myInfo(Principal principal) {
        String myName = principal.getName();
        User user = userService.findUser(myName);

        Map<String, User> respResult = new LinkedHashMap<>();
        respResult.put("myInfo", user);
        return respResult;
    }

    @GetMapping("/findUser/{userName}")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, User> findUser(@PathVariable String userName) {
        User user = userService.findUser(userName);

        Map<String, User> respResult = new LinkedHashMap<>();
        respResult.put("user", user);
        return respResult;
    }

    @GetMapping("/findAllUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, List<User>> findAllUsers() {
        List<User> allUsers = userService.findAllUsers();

        Map<String, List<User>> respResult = new LinkedHashMap<>();
        respResult.put("allUsers", allUsers);
        return respResult;
    }

}
