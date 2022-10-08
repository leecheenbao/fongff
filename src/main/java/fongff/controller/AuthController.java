package fongff.controller;

import fongff.dto.RegisterReqDto;
import fongff.model.User;
import fongff.serviceImpl.UserServiceImpl;
import fongff.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @ApiOperation("新增使用者")
    @PostMapping("/register")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> register(@RequestBody RegisterReqDto registerReqDto) {
        List<String> validateErrors = new ArrayList<>();

        if (userServiceImpl.isUserExist(registerReqDto.getUserName())) {
            validateErrors.add("userName '" + registerReqDto.getUserName() + "' is exist");
        }

        List<String> allUserRoles = Stream.of(User.Role.values()).map(role -> role.toString())
                .collect(Collectors.toList());
        if (!allUserRoles.contains(registerReqDto.getUserRole())) {
            validateErrors.add("userRole '" + registerReqDto.getUserRole() + "' is wrong");
        }

        if (!validateErrors.isEmpty()) {
            Map<String, Object> errorMsg = new LinkedHashMap<>();
            errorMsg.put("validateErrors", validateErrors);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
        }

        User user = new User();
        user.setName(registerReqDto.getUserName());
        user.setPassword(passwordEncoder.encode(registerReqDto.getUserPassword()));
        user.setRole(User.Role.valueOf(registerReqDto.getUserRole()));
        userServiceImpl.addUser(user);

        Map<String, Object> respResult = new LinkedHashMap<>();
        respResult.put("userName", user.getName());
        respResult.put("userRoles", user.getRole());
        return ResponseEntity.ok(respResult);
    }

    @ApiOperation("編輯使用者")
    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> updateUser(@RequestBody RegisterReqDto registerReqDto) {
        List<String> validateErrors = new ArrayList<>();

        if (!userServiceImpl.isUserExist(registerReqDto.getUserName())) {
            validateErrors.add("userName '" + registerReqDto.getUserName() + "' is not exist");
        }

        List<String> allUserRoles = Stream.of(User.Role.values()).map(role -> role.toString())
                .collect(Collectors.toList());
        if (!allUserRoles.contains(registerReqDto.getUserRole())) {
            validateErrors.add("userRole '" + registerReqDto.getUserRole() + "' is wrong");
        }

        if (!validateErrors.isEmpty()) {
            Map<String, Object> errorMsg = new LinkedHashMap<>();
            errorMsg.put("validateErrors", validateErrors);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
        }

        User user = new User();
        user.setName(registerReqDto.getUserName());
        user.setPassword(passwordEncoder.encode(registerReqDto.getUserPassword()));
        user.setRole(User.Role.valueOf(registerReqDto.getUserRole()));
        userServiceImpl.addUser(user);

        Map<String, Object> respResult = new LinkedHashMap<>();
        respResult.put("userName", user.getName());
        respResult.put("userRoles", user.getRole());
        respResult.put("message", "userInfo is update");

        return ResponseEntity.ok(respResult);
    }

    @ApiOperation("登入")
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam String userName, @RequestParam String userPassword) {
        Authentication authAfterSuccessLogin = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
        SecurityContextHolder.getContext().setAuthentication(authAfterSuccessLogin);

        List<String> userRoles = authAfterSuccessLogin.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        Map<String, Object> respResult = new LinkedHashMap<>();
        respResult.put("userName", userName);
        respResult.put("userRoles", userRoles);
        respResult.put("token", jwtUtil.createToken(userName, userRoles));

        return ResponseEntity.ok(respResult);
    }

    @ApiOperation("登出")
    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext();

        Map<String, Object> respResult = new LinkedHashMap<>();
        respResult.put("message", "logout");
        return ResponseEntity.ok(respResult);
    }
}
