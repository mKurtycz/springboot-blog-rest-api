package priv.mKurtycz.blog.springbootblogrestapi.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import priv.mKurtycz.blog.springbootblogrestapi.entity.Role;
import priv.mKurtycz.blog.springbootblogrestapi.entity.User;
import priv.mKurtycz.blog.springbootblogrestapi.exception.BlogAPIException;
import priv.mKurtycz.blog.springbootblogrestapi.payload.LoginDTO;
import priv.mKurtycz.blog.springbootblogrestapi.payload.RegisterDTO;
import priv.mKurtycz.blog.springbootblogrestapi.repository.RoleRepository;
import priv.mKurtycz.blog.springbootblogrestapi.repository.UserRepository;
import priv.mKurtycz.blog.springbootblogrestapi.security.JwtTokenProvider;
import priv.mKurtycz.blog.springbootblogrestapi.service.AuthService;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Override
    public String login(LoginDTO loginDTO) {
        Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generateToken(authentication);

        return token;
    }

    @Override
    public String register(RegisterDTO registerDTO) {
        // validation - check for username exists
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username is already used!");
        }
        else if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email is already used!");
        }

        User user = modelMapper.map(registerDTO, User.class);
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();

        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        return "User Registered successfully!";
    }
}
