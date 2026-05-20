package com.navashu.repository;

import com.navashu.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for UserRepository using JUnit5
 * Tests CRUD operations and custom queries
 */
@DataJpaTest
@DisplayName("UserRepository Unit Tests")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setFirstName("John");
        testUser.setLastName("Doe");
        testUser.setEmail("john@example.com");
        testUser.setPhoneNumber("1234567890");
        testUser.setPassword("encodedPassword123");
        testUser.setIsActive(true);
        testUser.setIsEmailVerified(false);
    }

    @Test
    @DisplayName("Should save and retrieve user by ID")
    void testSaveAndFindUserById() {
        // Arrange
        User savedUser = userRepository.save(testUser);

        // Act
        Optional<User> retrievedUser = userRepository.findById(savedUser.getUserId());

        // Assert
        assertThat(retrievedUser).isPresent();
        assertThat(retrievedUser.get().getEmail()).isEqualTo("john@example.com");
        assertThat(retrievedUser.get().getFirstName()).isEqualTo("John");
    }

    @Test
    @DisplayName("Should find user by email")
    void testFindUserByEmail() {
        // Arrange
        userRepository.save(testUser);

        // Act
        Optional<User> foundUser = userRepository.findByEmail("john@example.com");

        // Assert
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUserId()).isNotNull();
        assertThat(foundUser.get().getFirstName()).isEqualTo("John");
    }

    @Test
    @DisplayName("Should return empty when email not found")
    void testFindUserByEmailNotFound() {
        // Act
        Optional<User> foundUser = userRepository.findByEmail("nonexistent@example.com");

        // Assert
        assertThat(foundUser).isEmpty();
    }

    @Test
    @DisplayName("Should check if email exists")
    void testExistsByEmail() {
        // Arrange
        userRepository.save(testUser);

        // Act & Assert
        assertThat(userRepository.existsByEmail("john@example.com")).isTrue();
        assertThat(userRepository.existsByEmail("other@example.com")).isFalse();
    }

    @Test
    @DisplayName("Should find user by password reset token")
    void testFindUserByPasswordResetToken() {
        // Arrange
        testUser.setPasswordResetToken("validResetToken");
        testUser.setPasswordResetTokenExpiry(LocalDateTime.now().plusHours(24));
        User savedUser = userRepository.save(testUser);

        // Act
        Optional<User> foundUser = userRepository.findByPasswordResetToken("validResetToken");

        // Assert
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getPasswordResetToken()).isEqualTo("validResetToken");
    }

    @Test
    @DisplayName("Should return empty when reset token not found")
    void testFindUserByPasswordResetTokenNotFound() {
        // Act
        Optional<User> foundUser = userRepository.findByPasswordResetToken("nonExistentToken");

        // Assert
        assertThat(foundUser).isEmpty();
    }

    @Test
    @DisplayName("Should update user")
    void testUpdateUser() {
        // Arrange
        User savedUser = userRepository.save(testUser);

        // Act
        savedUser.setLastName("Smith");
        savedUser.setPhoneNumber("9876543210");
        User updatedUser = userRepository.save(savedUser);

        // Assert
        assertThat(updatedUser.getLastName()).isEqualTo("Smith");
        assertThat(updatedUser.getPhoneNumber()).isEqualTo("9876543210");
    }

    @Test
    @DisplayName("Should delete user")
    void testDeleteUser() {
        // Arrange
        User savedUser = userRepository.save(testUser);
        Long userId = savedUser.getUserId();

        // Act
        userRepository.deleteById(userId);

        // Assert
        Optional<User> deletedUser = userRepository.findById(userId);
        assertThat(deletedUser).isEmpty();
    }

    @Test
    @DisplayName("Should handle duplicate email constraint")
    void testDuplicateEmailConstraint() {
        // Arrange
        userRepository.save(testUser);
        
        User duplicateUser = new User();
        duplicateUser.setFirstName("Jane");
        duplicateUser.setLastName("Smith");
        duplicateUser.setEmail("john@example.com"); // Same email
        duplicateUser.setPassword("password123");
        duplicateUser.setIsActive(true);

        // Act & Assert
        assertThatThrownBy(() -> {
            userRepository.save(duplicateUser);
            entityManager.flush();
        }).isNotNull();
    }

    @Test
    @DisplayName("Should handle update of email verification status")
    void testUpdateEmailVerificationStatus() {
        // Arrange
        User savedUser = userRepository.save(testUser);

        // Act
        savedUser.setIsEmailVerified(true);
        userRepository.save(savedUser);

        // Assert
        Optional<User> retrievedUser = userRepository.findById(savedUser.getUserId());
        assertThat(retrievedUser.get().getIsEmailVerified()).isTrue();
    }

    @Test
    @DisplayName("Should handle account activation status")
    void testAccountActivationStatus() {
        // Arrange
        User savedUser = userRepository.save(testUser);

        // Act
        savedUser.setIsActive(false);
        userRepository.save(savedUser);

        // Assert
        Optional<User> retrievedUser = userRepository.findById(savedUser.getUserId());
        assertThat(retrievedUser.get().getIsActive()).isFalse();
    }

    @Test
    @DisplayName("Should track creation timestamp")
    void testCreatedAtTimestamp() {
        // Arrange
        LocalDateTime beforeSave = LocalDateTime.now();

        // Act
        User savedUser = userRepository.save(testUser);

        // Assert
        assertThat(savedUser.getCreatedAt()).isNotNull();
        assertThat(savedUser.getCreatedAt()).isAfterOrEqualTo(beforeSave);
    }

    @Test
    @DisplayName("Should track update timestamp")
    void testUpdatedAtTimestamp() {
        // Arrange
        User savedUser = userRepository.save(testUser);
        LocalDateTime initialUpdate = savedUser.getUpdatedAt();

        // Act
        try {
            Thread.sleep(100); // Ensure time passes
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        savedUser.setLastName("UpdatedName");
        User updatedUser = userRepository.save(savedUser);

        // Assert
        assertThat(updatedUser.getUpdatedAt()).isAfterOrEqualTo(initialUpdate);
    }

    @Test
    @DisplayName("Should update last login timestamp")
    void testLastLoginTimestamp() {
        // Arrange
        User savedUser = userRepository.save(testUser);

        // Act
        savedUser.setLastLogin(LocalDateTime.now());
        User updatedUser = userRepository.save(savedUser);

        // Assert
        assertThat(updatedUser.getLastLogin()).isNotNull();
    }

    @Test
    @DisplayName("Should save and retrieve multiple users")
    void testSaveAndRetrieveMultipleUsers() {
        // Arrange
        User user1 = new User();
        user1.setFirstName("User1");
        user1.setLastName("Last1");
        user1.setEmail("user1@example.com");
        user1.setPassword("pass1");
        user1.setIsActive(true);

        User user2 = new User();
        user2.setFirstName("User2");
        user2.setLastName("Last2");
        user2.setEmail("user2@example.com");
        user2.setPassword("pass2");
        user2.setIsActive(true);

        // Act
        userRepository.save(user1);
        userRepository.save(user2);

        // Assert
        assertThat(userRepository.findByEmail("user1@example.com")).isPresent();
        assertThat(userRepository.findByEmail("user2@example.com")).isPresent();
    }

    @Test
    @DisplayName("Should handle null optional values")
    void testHandleNullOptionalValues() {
        // Act
        Optional<User> notFound = userRepository.findByEmail("definitely@not.exists");

        // Assert
        assertThat(notFound).isEmpty();
        assertThat(notFound.orElse(null)).isNull();
    }

    @Test
    @DisplayName("Should allow null values for optional fields")
    void testOptionalFieldsCanBeNull() {
        // Arrange
        testUser.setLastName(null);
        testUser.setPhoneNumber(null);
        testUser.setPasswordResetToken(null);
        testUser.setLastLogin(null);

        // Act
        User savedUser = userRepository.save(testUser);

        // Assert
        assertThat(savedUser.getLastName()).isNull();
        assertThat(savedUser.getPhoneNumber()).isNull();
        assertThat(savedUser.getPasswordResetToken()).isNull();
    }
}

