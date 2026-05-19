package com.navashu.controller;

import com.navashu.model.*;
import com.navashu.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Sign Up Endpoint
     * POST /api/auth/signup
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        ApiResponse response = authService.signUp(signUpRequest);
        
        if (response.getSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Sign In Endpoint
     * POST /api/auth/signin
     */
    @PostMapping("/signin")
    public ResponseEntity<ApiResponse> signIn(@Valid @RequestBody SignInRequest signInRequest) {
        ApiResponse response = authService.signIn(signInRequest);
        
        if (response.getSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Forgot Password Endpoint
     * POST /api/auth/forgot-password
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        ApiResponse response = authService.forgotPassword(forgotPasswordRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Reset Password Endpoint
     * POST /api/auth/reset-password
     */
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        ApiResponse response = authService.resetPassword(resetPasswordRequest);
        
        if (response.getSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Change Password Endpoint
     * PUT /api/auth/change-password/{userId}
     */
    @PutMapping("/change-password/{userId}")
    public ResponseEntity<ApiResponse> changePassword(
            @PathVariable Long userId,
            @Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        ApiResponse response = authService.changePassword(userId, changePasswordRequest);
        
        if (response.getSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Health Check Endpoint
     * GET /api/auth/health
     */
    @GetMapping("/health")
    public ResponseEntity<ApiResponse> health() {
        return new ResponseEntity<>(
                new ApiResponse(true, "Authentication service is running"),
                HttpStatus.OK
        );
    }
}

