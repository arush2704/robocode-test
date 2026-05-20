package com.navashu.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.navashu.entity.User;
import com.navashu.model.*;
import com.navashu.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the authentication flow using JUnit5
 * Tests the complete application with real dependencies
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Authentication Integration Tests")
@Transactional
class AuthenticationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    private SignUpRequest signUpRequest;
    private SignInRequest signInRequest;
    private ChangePasswordRequest changePasswordRequest;

    @BeforeEach
    void setUp() {
        // Clean up
        userRepository.deleteAll();

        // Setup test data
        signUpRequest = new SignUpRequest();
        signUpRequest.setFirstName("Integration");
        signUpRequest.setLastName("Test");
        signUpRequest.setEmail("integration@test.com");
        signUpRequest.setPhoneNumber("1234567890");
        signUpRequest.setPassword("TestPassword123!");
        signUpRequest.setConfirmPassword("TestPassword123!");

        signInRequest = new SignInRequest();
        signInRequest.setEmail("integration@test.com");
        signInRequest.setPassword("TestPassword123!");

        changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setCurrentPassword("TestPassword123!");
        changePasswordRequest.setNewPassword("NewPassword456!");
        changePasswordRequest.setConfirmPassword("NewPassword456!");
    }

    @Test
    @DisplayName("Complete flow: SignUp -> SignIn -> ChangePassword")
    void testCompleteAuthenticationFlow() throws Exception {
        // Step 1: Sign up
        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value(containsString("Account created successfully")));

        // Verify user is created in database
        var createdUser = userRepository.findByEmail("integration@test.com");
        assert createdUser.isPresent();
        Long userId = createdUser.get().getUserId();

        // Step 2: Sign in
        mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signInRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Login successful"))
                .andExpect(jsonPath("$.data.token").isNotEmpty())
                .andExpect(jsonPath("$.data.userId").value(userId.intValue()));

        // Step 3: Change password
        mockMvc.perform(put("/api/auth/change-password/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changePasswordRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Password changed successfully"));

        // Step 4: Verify old password no longer works
        mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signInRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false));

        // Step 5: Sign in with new password
        signInRequest.setPassword("NewPassword456!");
        mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signInRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("Health check should always return OK")
    void testHealthCheckAlwaysUp() throws Exception {
        mockMvc.perform(get("/api/auth/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Authentication service is running"));
    }

    @Test
    @DisplayName("Multiple users can register and sign in independently")
    void testMultipleUsersIndependent() throws Exception {
        // User 1 signup
        SignUpRequest user1 = new SignUpRequest();
        user1.setFirstName("User1");
        user1.setLastName("Test");
        user1.setEmail("user1@test.com");
        user1.setPhoneNumber("1111111111");
        user1.setPassword("Password1!");
        user1.setConfirmPassword("Password1!");

        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isCreated());

        // User 2 signup
        SignUpRequest user2 = new SignUpRequest();
        user2.setFirstName("User2");
        user2.setLastName("Test");
        user2.setEmail("user2@test.com");
        user2.setPhoneNumber("2222222222");
        user2.setPassword("Password2!");
        user2.setConfirmPassword("Password2!");

        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user2)))
                .andExpect(status().isCreated());

        // User 1 signin
        SignInRequest signin1 = new SignInRequest();
        signin1.setEmail("user1@test.com");
        signin1.setPassword("Password1!");

        mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signin1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value("user1@test.com"));

        // User 2 signin
        SignInRequest signin2 = new SignInRequest();
        signin2.setEmail("user2@test.com");
        signin2.setPassword("Password2!");

        mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signin2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value("user2@test.com"));

        // Verify duplicate email fails
        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    @DisplayName("Forgot password flow returns success message for security")
    void testForgotPasswordFlow() throws Exception {
        // Create user first
        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isCreated());

        // Request password reset
        ForgotPasswordRequest request = new ForgotPasswordRequest();
        request.setEmail("integration@test.com");

        mockMvc.perform(post("/api/auth/forgot-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value(containsString("If the email exists")));

        // Also test with non-existent email (should not reveal)
        ForgotPasswordRequest nouser = new ForgotPasswordRequest();
        nouser.setEmail("nouser@test.com");

        mockMvc.perform(post("/api/auth/forgot-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nouser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value(containsString("If the email exists")));
    }

    @Test
    @DisplayName("CORS headers are properly set")
    void testCorsHeaders() throws Exception {
        mockMvc.perform(get("/api/auth/health")
                .header("Origin", "http://localhost:3000"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Invalid signup request returns bad request")
    void testSignupValidation() throws Exception {
        // Missing required fields
        String invalidRequest = "{\"firstName\":\"Test\"}";

        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Invalid signin request returns bad request")
    void testSigninValidation() throws Exception {
        // Invalid email format
        String invalidRequest = "{\"email\":\"notanemail\",\"password\":\"test\"}";

        mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("User can update password multiple times")
    void testMultiplePasswordChanges() throws Exception {
        // Signup
        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isCreated());

        var user = userRepository.findByEmail("integration@test.com").get();
        Long userId = user.getUserId();

        // Change password first time
        changePasswordRequest.setCurrentPassword("TestPassword123!");
        changePasswordRequest.setNewPassword("SecondPassword!");
        changePasswordRequest.setConfirmPassword("SecondPassword!");

        mockMvc.perform(put("/api/auth/change-password/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changePasswordRequest)))
                .andExpect(status().isOk());

        // Change password second time
        changePasswordRequest.setCurrentPassword("SecondPassword!");
        changePasswordRequest.setNewPassword("ThirdPassword!");
        changePasswordRequest.setConfirmPassword("ThirdPassword!");

        mockMvc.perform(put("/api/auth/change-password/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changePasswordRequest)))
                .andExpect(status().isOk());

        // Verify new password works
        signInRequest.setPassword("ThirdPassword!");
        mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signInRequest)))
                .andExpect(status().isOk());
    }
}

