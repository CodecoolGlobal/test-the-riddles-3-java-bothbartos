package tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SignUpPageTest extends BaseTest {

    @BeforeEach
    public void navigateToSignUpPage() {
        initializeDriver();
        driver.get(baseUrl + "register");
        driver.manage().window().maximize();
    }

    @Test
    @DisplayName("Register with empty credentials")
    public void testSignUpPageWithEmptyCredentials() {
        signUpPage.enterUsername("");
        signUpPage.enterEmail("");
        signUpPage.enterPassword("");
        signUpPage.clickSignUpButton();
        assertTrue(isAlertPresent());
    }

    @ParameterizedTest
    @MethodSource("credentialsProvider")
    public void testSignUpPage(String username, String email, String password) {

        signUpPage.enterUsername(username);
        signUpPage.enterEmail(email);
        signUpPage.enterPassword(password);
        signUpPage.clickSignUpButton();

        assertCorrectUrl(baseUrl + "login");
    }

    @Test
    @DisplayName("Register with in-use username, email, password")
    public void testSignUpPageWithInUseUsername() {
        signUpPage.enterUsername(dotenv.get("USERNAME_1"));
        signUpPage.enterEmail(dotenv.get("EMAIL_1"));
        signUpPage.enterPassword(dotenv.get("PASSWORD_1"));
        signUpPage.clickSignUpButton();
        assertTrue(isAlertPresent());
    }

    public static Stream<Arguments> credentialsProvider() {
        return CredentialsLoader.getSignUpCredentials();
    }
}
