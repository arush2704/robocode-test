package com.navashu.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for PasswordUtil using JUnit5
 * Tests password encoding and validation
 */
@SpringBootTest
@DisplayName("PasswordUtil Unit Tests")
class PasswordUtilTest {

    @Autowired
    private PasswordUtil passwordUtil;

    private String testPassword;
    private String encodedPassword;

    @BeforeEach
    void setUp() {
        testPassword = "MySecurePassword123!@#";
    }

    @Test
    @DisplayName("Should successfully encode a password")
    void testEncodePassword() {
        // Act
        String result = passwordUtil.encodePassword(testPassword);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result).isNotEqualTo(testPassword); // Encoded password should be different
        assertThat(result.length()).isGreaterThan(testPassword.length());
        encodedPassword = result;
    }

    @Test
    @DisplayName("Should verify matching password with encoded password")
    void testMatchesWithCorrectPassword() {
        // Arrange
        String encoded = passwordUtil.encodePassword(testPassword);

        // Act
        boolean result = passwordUtil.matches(testPassword, encoded);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Should not match with incorrect password")
    void testMatchesWithIncorrectPassword() {
        // Arrange
        String encoded = passwordUtil.encodePassword(testPassword);
        String wrongPassword = "WrongPassword123";

        // Act
        boolean result = passwordUtil.matches(wrongPassword, encoded);

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Should handle special characters in password")
    void testEncodePasswordWithSpecialCharacters() {
        // Arrange
        String specialPassword = "P@$$w0rd!#%&*(){}[]<>?";

        // Act
        String encoded = passwordUtil.encodePassword(specialPassword);
        boolean matches = passwordUtil.matches(specialPassword, encoded);

        // Assert
        assertThat(encoded).isNotNull();
        assertThat(matches).isTrue();
    }

    @Test
    @DisplayName("Should handle very long passwords")
    void testEncodePasswordWithLongPassword() {
        // Arrange
        String longPassword = "a".repeat(100) + "Password123";

        // Act
        String encoded = passwordUtil.encodePassword(longPassword);
        boolean matches = passwordUtil.matches(longPassword, encoded);

        // Assert
        assertThat(encoded).isNotNull();
        assertThat(matches).isTrue();
    }

    @Test
    @DisplayName("Should handle empty password")
    void testEncodePasswordEmpty() {
        // Arrange
        String emptyPassword = "";

        // Act
        String encoded = passwordUtil.encodePassword(emptyPassword);

        // Assert
        assertThat(encoded).isNotNull();
        assertThat(encoded).isNotEmpty();
    }

    @Test
    @DisplayName("Should encode same password differently each time (salt)")
    void testEncodedPasswordsDifferent() {
        // Act
        String encoded1 = passwordUtil.encodePassword(testPassword);
        String encoded2 = passwordUtil.encodePassword(testPassword);

        // Assert
        assertThat(encoded1).isNotEqualTo(encoded2); // Different salts
        assertThat(passwordUtil.matches(testPassword, encoded1)).isTrue();
        assertThat(passwordUtil.matches(testPassword, encoded2)).isTrue();
    }

    @Test
    @DisplayName("Should generate a random token")
    void testGenerateRandomToken() {
        // Act
        String token = passwordUtil.generateRandomToken();

        // Assert
        assertThat(token).isNotNull();
        assertThat(token).isNotEmpty();
        assertThat(token.length()).isGreaterThan(40); // Base64 encoded 32 bytes
    }

    @Test
    @DisplayName("Should generate different tokens each time")
    void testGenerateRandomTokensAreDifferent() {
        // Act
        String token1 = passwordUtil.generateRandomToken();
        String token2 = passwordUtil.generateRandomToken();

        // Assert
        assertThat(token1).isNotEqualTo(token2);
    }

    @Test
    @DisplayName("Should handle null password gracefully")
    void testMatchesWithNullPassword() {
        // Arrange
        String encoded = passwordUtil.encodePassword("password");

        // Act & Assert
        assertThatThrownBy(() -> passwordUtil.matches(null, encoded))
                .isNotNull();
    }

    @Test
    @DisplayName("Should handle null encoded password gracefully")
    void testMatchesWithNullEncodedPassword() {
        // Act & Assert
        assertThatThrownBy(() -> passwordUtil.matches(testPassword, null))
                .isNotNull();
    }

    @Test
    @DisplayName("Should validate password strength indicators")
    void testPasswordEncoding() {
        // Arrange
        String testCases[] = {
                "password123",
                "P@ssw0rd!",
                "VeryStrongPassword123!@#$%",
                "simple",
                "123456789"
        };

        // Act & Assert
        for (String password : testCases) {
            String encoded = passwordUtil.encodePassword(password);
            assertThat(encoded).isNotNull();
            assertThat(passwordUtil.matches(password, encoded)).isTrue();
            assertThat(passwordUtil.matches(password + "x", encoded)).isFalse();
        }
    }

    @Test
    @DisplayName("Should handle unicode characters in password")
    void testEncodePasswordWithUnicodeCharacters() {
        // Arrange
        String unicodePassword = "Пароль123!@#"; // Russian + numbers

        // Act
        String encoded = passwordUtil.encodePassword(unicodePassword);
        boolean matches = passwordUtil.matches(unicodePassword, encoded);

        // Assert
        assertThat(matches).isTrue();
    }

    @Test
    @DisplayName("Token should be URL-safe (no padding or special chars)")
    void testGeneratedTokenIsUrlSafe() {
        // Act
        String token = passwordUtil.generateRandomToken();

        // Assert
        assertThat(token).doesNotContain("=", "+", "/");
    }
}

