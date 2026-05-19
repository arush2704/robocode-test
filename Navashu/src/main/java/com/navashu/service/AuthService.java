package com.navashu.service;

import com.navashu.entity.User;
import com.navashu.model.*;
import com.navashu.repository.UserRepository;
import com.navashu.util.EmailService;
import com.navashu.util.JwtUtil;
import com.navashu.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordUtil passwordUtil;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailService emailService;

    /**
     * Sign up a new user
     */
    public ApiResponse signUp(SignUpRequest signUpRequest) {
        try {
            // Validate passwords match
            if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
                return new ApiResponse(false, "Passwords do not match");
            }

            // Check if email already exists
            if (userRepository.existsByEmail(signUpRequest.getEmail())) {
                return new ApiResponse(false, "Email already registered");
            }

            // Create new user
            User user = new User();
            user.setFirstName(signUpRequest.getFirstName());
            user.setLastName(signUpRequest.getLastName());
            user.setEmail(signUpRequest.getEmail());
            user.setPhoneNumber(signUpRequest.getPhoneNumber());
            user.setPassword(passwordUtil.encodePassword(signUpRequest.getPassword()));
            user.setIsActive(true);
            user.setIsEmailVerified(false);

            User savedUser = userRepository.save(user);

            // Send welcome email
            emailService.sendWelcomeEmail(savedUser.getEmail(), savedUser.getFirstName());

            return new ApiResponse(true, "Account created successfully. Please log in.", savedUser.getUserId());
        } catch (Exception e) {
            return new ApiResponse(false, "Error during sign up: " + e.getMessage());
        }
    }

    /**
     * Sign in user
     */
    public ApiResponse signIn(SignInRequest signInRequest) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(signInRequest.getEmail());

            if (userOptional.isEmpty()) {
                return new ApiResponse(false, "Invalid email or password");
            }

            User user = userOptional.get();

            // Check if user is active
            if (!user.getIsActive()) {
                return new ApiResponse(false, "Account is deactivated");
            }

            // Verify password
            if (!passwordUtil.matches(signInRequest.getPassword(), user.getPassword())) {
                return new ApiResponse(false, "Invalid email or password");
            }

            // Generate JWT token
            String token = jwtUtil.generateToken(user.getUserId(), user.getEmail());

            // Update last login
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);

            // Create response
            SignInResponse response = new SignInResponse();
            response.setUserId(user.getUserId());
            response.setFirstName(user.getFirstName());
            response.setLastName(user.getLastName());
            response.setEmail(user.getEmail());
            response.setPhoneNumber(user.getPhoneNumber());
            response.setToken(token);
            response.setSuccess(true);
            response.setMessage("Login successful");

            ApiResponse apiResponse = new ApiResponse(true, "Login successful", response);
            return apiResponse;
        } catch (Exception e) {
            return new ApiResponse(false, "Error during sign in: " + e.getMessage());
        }
    }

    /**
     * Forgot password - send reset email
     */
    public ApiResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(forgotPasswordRequest.getEmail());

            if (userOptional.isEmpty()) {
                // Don't reveal if email exists for security reasons
                return new ApiResponse(true, "If the email exists in our system, you will receive a password reset link");
            }

            User user = userOptional.get();

            // Generate reset token
            String resetToken = UUID.randomUUID().toString();
            user.setPasswordResetToken(resetToken);
            user.setPasswordResetTokenExpiry(LocalDateTime.now().plusHours(24));

            userRepository.save(user);

            // Send reset email
            emailService.sendPasswordResetEmail(user.getEmail(), resetToken);

            return new ApiResponse(true, "If the email exists in our system, you will receive a password reset link");
        } catch (Exception e) {
            return new ApiResponse(false, "Error during forgot password: " + e.getMessage());
        }
    }

    /**
     * Reset password using token
     */
    public ApiResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {
        try {
            // Validate passwords match
            if (!resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getConfirmPassword())) {
                return new ApiResponse(false, "Passwords do not match");
            }

            Optional<User> userOptional = userRepository.findByPasswordResetToken(resetPasswordRequest.getToken());

            if (userOptional.isEmpty()) {
                return new ApiResponse(false, "Invalid or expired reset token");
            }

            User user = userOptional.get();

            // Check if token is expired
            if (user.getPasswordResetTokenExpiry() == null || 
                user.getPasswordResetTokenExpiry().isBefore(LocalDateTime.now())) {
                return new ApiResponse(false, "Reset token has expired");
            }

            // Update password
            user.setPassword(passwordUtil.encodePassword(resetPasswordRequest.getNewPassword()));
            user.setPasswordResetToken(null);
            user.setPasswordResetTokenExpiry(null);

            userRepository.save(user);

            return new ApiResponse(true, "Password reset successfully. Please log in with your new password");
        } catch (Exception e) {
            return new ApiResponse(false, "Error during password reset: " + e.getMessage());
        }
    }

    /**
     * Change password for authenticated user
     */
    public ApiResponse changePassword(Long userId, ChangePasswordRequest changePasswordRequest) {
        try {
            // Validate passwords match
            if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
                return new ApiResponse(false, "Passwords do not match");
            }

            Optional<User> userOptional = userRepository.findById(userId);

            if (userOptional.isEmpty()) {
                return new ApiResponse(false, "User not found");
            }

            User user = userOptional.get();

            // Verify current password
            if (!passwordUtil.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
                return new ApiResponse(false, "Current password is incorrect");
            }

            // Update password
            user.setPassword(passwordUtil.encodePassword(changePasswordRequest.getNewPassword()));
            userRepository.save(user);

            return new ApiResponse(true, "Password changed successfully");
        } catch (Exception e) {
            return new ApiResponse(false, "Error during password change: " + e.getMessage());
        }
    }

    /**
     * Get user by ID
     */
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Get user by email
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}

