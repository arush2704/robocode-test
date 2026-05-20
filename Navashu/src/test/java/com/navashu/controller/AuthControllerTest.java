package com.navashu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.navashu.model.*;
import com.navashu.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockbean.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for AuthController using JUnit5 and Mockito
 * Uses MockMvc for testing REST endpoints
 */
@WebMvcTest(AuthController.class)
@DisplayName("AuthController Unit Tests")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    private SignUpRequest signUpRequest;
    private SignInRequest signInRequest;
    private ForgotPasswordRequest forgotPasswordRequest;
    private ResetPasswordRequest resetPasswordRequest;
    private ChangePasswordRequest changePasswordRequest;

    @BeforeEach
    void setUp() {
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

        forgotPasswordRequest = new ForgotPasswordRequest();
        forgotPasswordRequest.setEmail("john@example.com");

        resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setToken("validToken");
        resetPasswordRequest.setNewPassword("newPassword123");
        resetPasswordRequest.setConfirmPassword("newPassword123");

        changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setCurrentPassword("password123");
        changePasswordRequest.setNewPassword("newPassword123");
        changePasswordRequest.setConfirmPassword("newPassword123");
    }

    // ======================== SignUp Endpoint Tests ========================

    @Test
    @DisplayName("POST /api/auth/signup - Should return 201 CREATED on successful signup")
    void testSignUpSuccess() throws Exception {
        // Arrange
        ApiResponse successResponse = new ApiResponse(true, "Account created successfully", 1L);
        when(authService.signUp(any(SignUpRequest.class))).thenReturn(successResponse);

        // Act & Assert
        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Account created successfully"));

        verify(authService, times(1)).signUp(any(SignUpRequest.class));
    }

    @Test
    @DisplayName("POST /api/auth/signup - Should return 400 BAD REQUEST on duplicate email")
    void testSignUpDuplicateEmail() throws Exception {
        // Arrange
        ApiResponse failureResponse = new ApiResponse(false, "Email already registered");
        when(authService.signUp(any(SignUpRequest.class))).thenReturn(failureResponse);

        // Act & Assert
        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));

        verify(authService, times(1)).signUp(any(SignUpRequest.class));
    }

    @Test
    @DisplayName("POST /api/auth/signup - Should return 400 when password mismatch")
    void testSignUpPasswordMismatch() throws Exception {
        // Arrange
        signUpRequest.setConfirmPassword("differentPassword");
        ApiResponse failureResponse = new ApiResponse(false, "Passwords do not match");
        when(authService.signUp(any(SignUpRequest.class))).thenReturn(failureResponse);

        // Act & Assert
        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isBadRequest());
    }

    // ======================== SignIn Endpoint Tests ========================

    @Test
    @DisplayName("POST /api/auth/signin - Should return 200 OK on successful login")
    void testSignInSuccess() throws Exception {
        // Arrange
        SignInResponse signInResponse = new SignInResponse();
        signInResponse.setUserId(1L);
        signInResponse.setFirstName("John");
        signInResponse.setLastName("Doe");
        signInResponse.setEmail("john@example.com");
        signInResponse.setToken("jwt_token_here");
        signInResponse.setSuccess(true);
        signInResponse.setMessage("Login successful");

        ApiResponse response = new ApiResponse(true, "Login successful", signInResponse);
        when(authService.signIn(any(SignInRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signInRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Login successful"));

        verify(authService, times(1)).signIn(any(SignInRequest.class));
    }

    @Test
    @DisplayName("POST /api/auth/signin - Should return 401 UNAUTHORIZED on invalid credentials")
    void testSignInInvalidCredentials() throws Exception {
        // Arrange
        ApiResponse failureResponse = new ApiResponse(false, "Invalid email or password");
        when(authService.signIn(any(SignInRequest.class))).thenReturn(failureResponse);

        // Act & Assert
        mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signInRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false));

        verify(authService, times(1)).signIn(any(SignInRequest.class));
    }

    @Test
    @DisplayName("POST /api/auth/signin - Should return 401 UNAUTHORIZED for deactivated account")
    void testSignInDeactivatedAccount() throws Exception {
        // Arrange
        ApiResponse failureResponse = new ApiResponse(false, "Account is deactivated");
        when(authService.signIn(any(SignInRequest.class))).thenReturn(failureResponse);

        // Act & Assert
        mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signInRequest)))
                .andExpect(status().isUnauthorized());
    }

    // ======================== Forgot Password Endpoint Tests ========================

    @Test
    @DisplayName("POST /api/auth/forgot-password - Should return 200 OK")
    void testForgotPasswordSuccess() throws Exception {
        // Arrange
        ApiResponse response = new ApiResponse(true, "If the email exists in our system, you will receive a password reset link");
        when(authService.forgotPassword(any(ForgotPasswordRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/auth/forgot-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(forgotPasswordRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        verify(authService, times(1)).forgotPassword(any(ForgotPasswordRequest.class));
    }

    @Test
    @DisplayName("POST /api/auth/forgot-password - Should not reveal if email exists (security)")
    void testForgotPasswordNonExistentEmail() throws Exception {
        // Arrange
        ForgotPasswordRequest request = new ForgotPasswordRequest();
        request.setEmail("nonexistent@example.com");
        ApiResponse response = new ApiResponse(true, "If the email exists in our system, you will receive a password reset link");
        when(authService.forgotPassword(any(ForgotPasswordRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/auth/forgot-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    // ======================== Reset Password Endpoint Tests ========================

    @Test
    @DisplayName("POST /api/auth/reset-password - Should return 200 OK on successful reset")
    void testResetPasswordSuccess() throws Exception {
        // Arrange
        ApiResponse response = new ApiResponse(true, "Password reset successfully. Please log in with your new password");
        when(authService.resetPassword(any(ResetPasswordRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/auth/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resetPasswordRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        verify(authService, times(1)).resetPassword(any(ResetPasswordRequest.class));
    }

    @Test
    @DisplayName("POST /api/auth/reset-password - Should return 400 BAD REQUEST on password mismatch")
    void testResetPasswordMismatch() throws Exception {
        // Arrange
        resetPasswordRequest.setConfirmPassword("differentPassword");
        ApiResponse response = new ApiResponse(false, "Passwords do not match");
        when(authService.resetPassword(any(ResetPasswordRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/auth/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resetPasswordRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    @DisplayName("POST /api/auth/reset-password - Should return 400 BAD REQUEST on expired token")
    void testResetPasswordExpiredToken() throws Exception {
        // Arrange
        ApiResponse response = new ApiResponse(false, "Reset token has expired");
        when(authService.resetPassword(any(ResetPasswordRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/auth/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resetPasswordRequest)))
                .andExpect(status().isBadRequest());
    }

    // ======================== Change Password Endpoint Tests ========================

    @Test
    @DisplayName("PUT /api/auth/change-password/{userId} - Should return 200 OK on successful change")
    void testChangePasswordSuccess() throws Exception {
        // Arrange
        ApiResponse response = new ApiResponse(true, "Password changed successfully");
        when(authService.changePassword(1L, changePasswordRequest)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(put("/api/auth/change-password/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changePasswordRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        verify(authService, times(1)).changePassword(1L, changePasswordRequest);
    }

    @Test
    @DisplayName("PUT /api/auth/change-password/{userId} - Should return 400 BAD REQUEST on password mismatch")
    void testChangePasswordMismatch() throws Exception {
        // Arrange
        changePasswordRequest.setConfirmPassword("differentPassword");
        ApiResponse response = new ApiResponse(false, "Passwords do not match");
        when(authService.changePassword(1L, changePasswordRequest)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(put("/api/auth/change-password/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changePasswordRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /api/auth/change-password/{userId} - Should return 400 BAD REQUEST on incorrect current password")
    void testChangePasswordIncorrectCurrent() throws Exception {
        // Arrange
        ApiResponse response = new ApiResponse(false, "Current password is incorrect");
        when(authService.changePassword(1L, changePasswordRequest)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(put("/api/auth/change-password/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changePasswordRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /api/auth/change-password/{userId} - Should return 400 BAD REQUEST when user not found")
    void testChangePasswordUserNotFound() throws Exception {
        // Arrange
        ApiResponse response = new ApiResponse(false, "User not found");
        when(authService.changePassword(999L, changePasswordRequest)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(put("/api/auth/change-password/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changePasswordRequest)))
                .andExpect(status().isBadRequest());
    }

    // ======================== Health Endpoint Tests ========================

    @Test
    @DisplayName("GET /api/auth/health - Should return 200 OK with service up status")
    void testHealthCheckSuccess() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/auth/health")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Authentication service is running"));

        verify(authService, never()).signUp(any());
    }

    @Test
    @DisplayName("GET /api/auth/health - Should support CORS")
    void testHealthCheckCors() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/auth/health")
                .header("Origin", "*"))
                .andExpect(status().isOk());
    }

    // ======================== Validation Tests ========================

    @Test
    @DisplayName("POST /api/auth/signup - Should validate required fields")
    void testSignUpValidation() throws Exception {
        // Arrange - Empty request body
        String invalidRequest = "{}";

        // Act & Assert
        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequest))
                .andExpect(status().isBadRequest());

        verify(authService, never()).signUp(any());
    }

    @Test
    @DisplayName("POST /api/auth/signin - Should validate email format")
    void testSignInValidationInvalidEmail() throws Exception {
        // Arrange
        signInRequest.setEmail("invalid-email");
        
        // Act & Assert
        mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signInRequest)))
                .andExpect(status().isBadRequest());

        verify(authService, never()).signIn(any());
    }

    @Test
    @DisplayName("POST /api/auth/reset-password - Should require valid token")
    void testResetPasswordValidation() throws Exception {
        // Arrange
        resetPasswordRequest.setToken("");

        // Act & Assert
        mockMvc.perform(post("/api/auth/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resetPasswordRequest)))
                .andExpect(status().isBadRequest());
    }
}

