package com.UserManagementusingJWT.service;

import com.UserManagementusingJWT.model.Role;
import com.UserManagementusingJWT.model.User;
import com.UserManagementusingJWT.repository.UserRepository;
import com.UserManagementusingJWT.responsejson.AllUsersResponse;
import com.UserManagementusingJWT.responsejson.AuthenticationResponse;
import com.UserManagementusingJWT.responsejson.RegistrationRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private  final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private  final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }
    //checking the email is already taken by another users
    public  boolean isEmailExist(String email){
                return userRepository.existsByEmail(email);
    }
    // finding the all users in database
    public List<AllUsersResponse> findAllUsers(){
        List<AllUsersResponse>responses=new ArrayList<>();
        List<User>users=userRepository.findAll();
        for( User user:users){
            AllUsersResponse response=new AllUsersResponse();
            response.setId(user.getId());
            response.setEmail(user.getEmail());
            response.setName(user.getName());
            response.setRole(user.getRole());
            responses.add(response);
        }
        return responses;
    }
    //finding a specified user for editing
    public AllUsersResponse findUserWihEmail(String email){
        AllUsersResponse response =new AllUsersResponse();
        User user=new User();
              user=userRepository.findByEmail(email).orElseThrow();
        response.setName(user.getName());
        response.setRole(user.getRole());
        response.setEmail(user.getEmail());
        return response;
    }

   //edite user data
    public void editUser(AllUsersResponse userInfo){
        User user=userRepository.findByEmail(userInfo.getEmail()).orElseThrow();
        user.setName(userInfo.getName());
        user.setRole(userInfo.getRole());
        userRepository.save(user);
    }

  //searching the user given key
    public List<AllUsersResponse>searchUser(String key){
        List<AllUsersResponse>responses=new ArrayList<>();
        List<User>users=userRepository.searchUsers(key);
        for( User user:users){
            AllUsersResponse response=new AllUsersResponse();
            response.setId(user.getId());
            response.setEmail(user.getEmail());
            response.setName(user.getName());
            response.setRole(user.getRole());
            responses.add(response);
        }
        return responses;
    }
 @Transactional
    public void deleteUser(String email){
        userRepository.deleteByEmail(email);
    }

    //registering user in user entity
    public AuthenticationResponse register(RegistrationRequest request){
        User user =new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(Role.USER);
        //saving the user in database
        user.setPassword(passwordEncoder.encode(request.getPassword()));
       //creating jwt token
        user=userRepository.save(user);
        String token =jwtService.generateToken(user);
        return new AuthenticationResponse(token,user.getRole(),user.getName(), user.getEmail());
    }
     // validating the user login
    public AuthenticationResponse authenticate(RegistrationRequest request){
        //verifying user given details using authenticationManager
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        //finding the user and creating JWT token for validation
        User user =userRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.generateToken(user);
     return  new AuthenticationResponse(token,user.getRole(),user.getName(),user.getEmail());
    }
}
