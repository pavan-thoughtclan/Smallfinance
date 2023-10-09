package com.tc.training.smallfinance.controllers;

import com.tc.training.smallfinance.dtos.inputs.LoginInputDto;
import com.tc.training.smallfinance.dtos.outputs.LoginOutputDto;
import com.tc.training.smallfinance.dtos.outputs.UserOutputDto;
import com.tc.training.smallfinance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
//    @PutMapping("/updatePassword")
//    public ResponseEntity updatePassword(@RequestParam String userName, @RequestParam  String password){
//        userService.updatePassword(userName,password);
//        return ResponseEntity.ok("Password Successfully Changed");
//    }

//    @PutMapping("/uploadImage")
//    public ResponseEntity uploadImage(@RequestParam MultipartFile file1,@RequestParam MultipartFile file2,@RequestParam MultipartFile file3, @RequestParam String userName) throws IOException {
//        userService.uploadImage(file1,file2,file3,userName);
//        return ResponseEntity.ok("Images Successfully Saved");
//    }
//
//    @PutMapping("/uploadPic")
//    public String uploadPic(@RequestParam MultipartFile file1) throws IOException {
//        return userService.uploadPic(file1);
//    }
//
//    @GetMapping(value = "/getImages",produces = MediaType.IMAGE_JPEG_VALUE )
//    public @ResponseBody byte[] getImage(@RequestParam String userName){
//        return userService.getImage(userName);
//    }

    @PostMapping("/login")
    public LoginOutputDto login(@RequestBody LoginInputDto loginInputDto){
        return userService.login(loginInputDto);
    }
//    @PostMapping("/resetPassword")
//    public ResponseEntity<String> resetPassword(@RequestParam Long email) {
//        return userService.resetPassword(email);
////        try {
////            String link= FirebaseAuth.getInstance().generatePasswordResetLink(email);
////            emailService.sendMail("Password Reset",link,email);
////            return ResponseEntity.ok("Password reset email sent");
////        } catch (FirebaseAuthException e) {
////            return ResponseEntity.badRequest().body("Failed to send password reset email: " + e.getMessage());
////        }
//    }

    @GetMapping("/get_all")
    @PreAuthorize("hasRole('MANAGER')")
    public List<UserOutputDto> getAllUsers(){
        return userService.getAll();

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public UserOutputDto getById(@PathVariable("id") UUID id){
        return userService.getById(id);
    }
}
