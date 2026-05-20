package com.navashu.service;

import com.navashu.entity.User;
import com.navashu.model.*;
import com.navashu.repository.UserRepository;
import com.navashu.util.EmailService;
import com.navashu.util.JwtUtil;
import com.navashu.util.PasswordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AuthService using JUnit5 and Mockito
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AuthService Unit Tests")
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordUtil passwordUtil;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AuthService authService;

    private User testUser;
    private SignUpRequest signUpRequest;
    private SignInRequest signInRequest;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUserId(1L);
        testUser.setFirstName("John");
        testUser.setLastName("Doe");
        testUser.setEmail("john@example.com");
        testUser.setPhoneNumber("1234567890");
        testUser.setPassword("$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeUxWdeS86E36P4/DJm");
        testUser.setIsActive(true);
        testUser.setIsEmailVerified(false);

        signUpRequest = new SignUpRequest();
        signUpRequest.setFirstName("John");
        signUpRequest.setLastName("Doe");
        signUpRequest.setEmail("john@example.com");
        signUpRequest.setPhoneNumber("1234567890");
        signUpRequest.setPassword("password123");
        signUpRequest.setConfirmPassword("password123");

        signInRequest = new SignInRequest();
        signInRequest.setEmail("john@example.com");
        signInRequest.setPassword("password123");
    }

    // ======================== SignUp Tests ========================

    @Test
    @DisplayName("Should successfully sign up a new user")
    void testSignUpSuccess() {
        // Arrange
        when(userRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(false);
        when(passwordUtil.encodePassword(signUpRequest.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        doNothing().when(emailService).sendWelcomeEmail(anyString(), anyString());

        // Act
        ApiResponse response = authService.signUp(signUpRequest);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getSuccess()).isTrue();
        assertThat(response.getMessage()).contains("Account created successfully");
        verify(userRepository, times(1)).save(any(User.class));
        verify(emailService, times(1)).sendWelcomeEmail(signUpRequest.getEmail(), signUpRequest.getFirstName());
    }

    @Test
    @DisplayName("Should fail signup when passwords don't match")
    void testSignUpPasswordMismatch() {
        // Arrange
        signUpRequest.setConfirmPassword("differentPassword");

        // Act
        ApiResponse response = authService.signUp(signUpRequest);

        // Assert
        assertThat(response.getSuccess()).isFalse();
        assertThat(response.getMessage()).contains("Passwords do not match");
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should fail signup when email already exists")
    void testSignUpEmailExists() {
        // Arrange
        when(userRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(true);

        // Act
        ApiResponse response = authService.signUp(signUpRequest);

        // Assert
        assertThat(response.getSuccess()).isFalse();
        assertThat(response.getMessage()).contains("Email already registered");
        verify(userRepository, never()).save(any());
    }

    // ======================== SignIn Tests ========================

    @Test
    @DisplayName("Should successfully sign in user with correct credentials")
    void testSignInSuccess() {
        // Arrange
        when(userRepository.findByEmail(signInRequest.getEmail())).thenReturn(Optional.of(testUser));
        when(passwordUtil.matches(signInRequest.getPassword(), testUser.getPassword())).thenReturn(true);
        when(jwtUtil.generateToken(testUser.getUserId(), testUser.getEmail())).thenReturn("validToken");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        ApiResponse response = authService.signIn(signInRequest);

        // Assert
        assertThat(response.getSuccess()).isTrue();
        assertThat(response.getMessage()).contains("Login successful");
        assertThat(response.getData()).isNotNull();
        verify(jwtUtil, times(1)).generateToken(testUser.getUserId(), testUser.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should fail signin with invalid email")
    void testSignInInvalidEmail() {
        // Arrange
        when(userRepository.findByEmail(signInRequest.getEmail())).thenReturn(Optional.empty());

        // Act
        ApiResponse response = authService.signIn(signInRequest);

        // Assert
        assertThat(response.getSuccess()).isFalse();
        assertThat(response.getMessage()).contains("Invalid email or password");
        verify(jwtUtil, never()).generateToken(anyLong(), anyString());
    }

    @Test
    @DisplayName("Should fail signin with wrong password")
    void testSignInWrongPassword() {
        // Arrange
        when(userRepository.findByEmail(signInRequest.getEmail())).thenReturn(Optional.of(testUser));
        when(passwordUtil.matches(signInRequest.getPassword(), testUser.getPassword())).thenReturn(false);

        // Act
        ApiResponse response = authService.signIn(signInRequest);

        // Assert
        assertThat(response.getSuccess()).isFalse();
        assertThat(response.getMessage()).contains("Invalid email or password");
    }

    @Test
    @DisplayName("Should fail signin when account is deactivated")
    void testSignInDeactivatedAccount() {
        // Arrange
        testUser.setIsActive(false);
        when(userRepository.findByEmail(signInRequest.getEmail())).thenReturn(Optional.of(testUser));

        // Act
        ApiResponse response = authService.signIn(signInRequest);

        // Assert
        assertThat(response.getSuccess()).isFalse();
        assertThat(response.getMessage()).contains("Account is deactivated");
    }

    // ======================== Forgot Password Tests ========================

    @Test
    @DisplayName("Should successfully send forgot password email")
    void testForgotPasswordSuccess() {
        // Arrange
        ForgotPasswordRequest request = new ForgotPasswordRequest();
        request.setEmail("john@example.com");
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        doNothing().when(emailService).sendPasswordResetEmail(anyString(), anyString());

        // Act
        ApiResponse response = authService.forgotPassword(request);

        // Assert
        assertThat(response.getSuccess()).isTrue();
        verify(userRepository, times(1)).save(any(User.class));
        verify(emailService, times(1)).sendPasswordResetEmail(anyString(), anyString());
    }

    @Test
    @DisplayName("Should return success for non-existent email (security measure)")
    void testForgotPasswordNonExistentEmail() {
        // Arrange
        ForgotPasswordRequest request = new ForgotPasswordRequest();
        request.setEmail("nonexistent@example.com");
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        // Act
        ApiResponse response = authService.forgotPassword(request);

        // Assert
        assertThat(response.getSuccess()).isTrue();
        assertThat(response.getMessage()).contains("If the email exists");
        verify(emailService, never()).sendPasswordResetEmail(anyString(), anyString());
    }

    // ======================== Reset Password Tests ========================

    @Test
    @DisplayName("Should successfully reset password with valid token")
    void testResetPasswordSuccess() {
        // Arrange
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setToken("validToken");
        request.setNewPassword("newPassword123");
        request.setConfirmPassword("newPassword123");

        User userWithToken = new User();
        userWithToken.setUserId(1L);
        userWithToken.setEmail("john@example.com");
        userWithToken.setPasswordResetToken("validToken");
        userWithToken.setPasswordResetTokenExpiry(LocalDateTime.now().plusHours(1));

        when(userRepository.findByPasswordResetToken("validToken")).thenReturn(Optional.of(userWithToken));
        when(passwordUtil.encodePassword(request.getNewPassword())).thenReturn("encodedNewPassword");
        when(userRepository.save(any(User.class))).thenReturn(userWithToken);

        // Act
        ApiResponse response = authService.resetPassword(request);

        // Assert
        assertThat(response.getSuccess()).isTrue();
        assertThat(response.getMessage()).contains("Password reset successfully");
        assertThat(userWithToken.getPasswordResetToken()).isNull();
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should fail password reset when passwords don't match")
    void testResetPasswordMismatch() {
        // Arrange
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setToken("validToken");
        request.setNewPassword("password123");
        request.setConfirmPassword("differentPassword");

        // Act
        ApiResponse response = authService.resetPassword(request);

        // Assert
        assertThat(response.getSuccess()).isFalse();
        assertThat(response.getMessage()).contains("Passwords do not match");
    }

    @Test
    @DisplayName("Should fail password reset with expired token")
    void testResetPasswordExpiredToken() {
        // Arrange
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setToken("expiredToken");
        request.setNewPassword("newPassword123");
        request.setConfirmPassword("newPassword123");

        User userWithExpiredToken = new User();
        userWithExpiredToken.setPasswordResetTokenExpiry(LocalDateTime.now().minusHours(1));

        when(userRepository.findByPasswordResetToken("expiredToken")).thenReturn(Optional.of(userWithExpiredToken));

        // Act
        ApiResponse response = authService.resetPassword(request);

        // Assert
        assertThat(response.getSuccess()).isFalse();
        assertThat(response.getMessage()).contains("Reset token has expired");
    }

    @Test
    @DisplayName("Should fail password reset with invalid token")
    void testResetPasswordInvalidToken() {
        // Arrange
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setToken("invalidToken");
        request.setNewPassword("newPassword123");
        request.setConfirmPassword("newPassword123");

        when(userRepository.findByPasswordResetToken("invalidToken")).thenReturn(Optional.empty());

        // Act
        ApiResponse response = authService.resetPassword(request);

        // Assert
        assertThat(response.getSuccess()).isFalse();
        assertThat(response.getMessage()).contains("Invalid or expired reset token");
    }

    // ======================== Change Password Tests ========================

    @Test
    @DisplayName("Should successfully change password")
    void testChangePasswordSuccess() {
        // Arrange
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setCurrentPassword("password123");
        request.setNewPassword("newPassword123");
        request.setConfirmPassword("newPassword123");

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(passwordUtil.matches(request.getCurrentPassword(), testUser.getPassword())).thenReturn(true);
        when(passwordUtil.encodePassword(request.getNewPassword())).thenReturn("encodedNewPassword");
        when(userRepository.save(testUser)).thenReturn(testUser);

        // Act
        ApiResponse response = authService.changePassword(1L, request);

        // Assert
        assertThat(response.getSuccess()).isTrue();
        assertThat(response.getMessage()).contains("Password changed successfully");
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    @DisplayName("Should fail when new passwords don't match")
    void testChangePasswordMismatch() {
        // Arrange
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setCurrentPassword("password123");
        request.setNewPassword("newPassword123");
        request.setConfirmPassword("differentPassword");

        // Act
        ApiResponse response = authService.changePassword(1L, request);

        // Assert
        assertThat(response.getSuccess()).isFalse();
        assertThat(response.getMessage()).contains("Passwords do not match");
    }

    @Test
    @DisplayName("Should fail when current password is incorrect")
    void testChangePasswordIncorrectCurrent() {
        // Arrange
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setCurrentPassword("wrongPassword");
        request.setNewPassword("newPassword123");
        request.setConfirmPassword("newPassword123");

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(passwordUtil.matches(request.getCurrentPassword(), testUser.getPassword())).thenReturn(false);

        // Act
        ApiResponse response = authService.changePassword(1L, request);

        // Assert
        assertThat(response.getSuccess()).isFalse();
        assertThat(response.getMessage()).contains("Current password is incorrect");
    }

    @Test
    @DisplayName("Should fail when user not found")
    void testChangePasswordUserNotFound() {
        // Arrange
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setCurrentPassword("password123");
        request.setNewPassword("newPassword123");
        request.setConfirmPassword("newPassword123");

        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        ApiResponse response = authService.changePassword(999L, request);

        // Assert
        assertThat(response.getSuccess()).isFalse();
        assertThat(response.getMessage()).contains("User not found");
    }

    // ======================== Get User Tests ========================

    @Test
    @DisplayName("Should get user by ID")
    void testGetUserById() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // Act
        Optional<User> user = authService.getUserById(1L);

        // Assert
        assertThat(user).isPresent();
        assertThat(user.get().getEmail()).isEqualTo("john@example.com");
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return empty when user not found by ID")
    void testGetUserByIdNotFound() {
        // Arrange
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<User> user = authService.getUserById(999L);

        // Assert
        assertThat(user).isEmpty();
    }

    @Test
    @DisplayName("Should get user by email")
    void testGetUserByEmail() {
        // Arrange
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(testUser));

        // Act
        Optional<User> user = authService.getUserByEmail("john@example.com");

        // Assert
        assertThat(user).isPresent();
        assertThat(user.get().getFirstName()).isEqualTo("John");
        verify(userRepository, times(1)).findByEmail("john@example.com");
    }

    @Test
    @DisplayName("Should return empty when user not found by email")
    void testGetUserByEmailNotFound() {
        // Arrange
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        // Act
        Optional<User> user = authService.getUserByEmail("nonexistent@example.com");

        // Assert
        assertThat(user).isEmpty();
    }
}

