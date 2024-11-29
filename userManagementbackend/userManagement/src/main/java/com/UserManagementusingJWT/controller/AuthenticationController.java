package com.UserManagementusingJWT.controller;

import com.UserManagementusingJWT.responsejson.AllUsersResponse;
import com.UserManagementusingJWT.responsejson.AuthenticationResponse;
import com.UserManagementusingJWT.responsejson.RegistrationRequest;
import com.UserManagementusingJWT.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthenticationController {
    private  final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegistrationRequest request){
             if (authService.isEmailExist(request.getEmail())){
                 return ResponseEntity.badRequest().build();
             }
        System.out.println("hello");
        return ResponseEntity.ok(authService.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody RegistrationRequest request){
        System.out.println("hello in login");
        System.out.println(authService.authenticate(request));
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping("/admin/all-users")
    public List<AllUsersResponse> allUserList(){
        return ResponseEntity.ok(authService.findAllUsers()).getBody();
    }

    @GetMapping("/admin/search-user")
    public List<AllUsersResponse>searchUserByName(@RequestParam("searchKey") String searchKey){
        return  ResponseEntity.ok(authService.searchUser(searchKey)).getBody();
    }
    @DeleteMapping("/admin/delete-user")
    public ResponseEntity<Void>deleteUser(@RequestParam("deleteId")String deleteId){
         authService.deleteUser(deleteId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/find-user")
    public AllUsersResponse findUserWithEmail(@RequestParam("userId")String userId){
        AllUsersResponse response =authService.findUserWihEmail(userId);
        return ResponseEntity.ok( response).getBody();
    }
    @PatchMapping("/user-edit-profile")
    public ResponseEntity<Void>editUserDetails(@RequestBody AllUsersResponse userdata){
        authService.editUser(userdata);
        return  ResponseEntity.ok().build();
    }
}
