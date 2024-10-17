package tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tests.Utils.CredentialsLoader;
import tests.Utils.Utils;


import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;


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

    @Test
    public void testSignUpPage() {
        String username = Utils.createRandomUsername();
        String email = Utils.createRandomEmail();
        String password = Utils.createRandomPassword();

        signUpPage.signUp(username, email, password);

        assertCorrectUrl(baseUrl + "login");
    }

    @Test
    @DisplayName("Register with in-use username, email, password")
    public void testSignUpPageWithInUseUsername() {
        String username = Utils.createRandomUsername();
        String email = Utils.createRandomEmail();
        String password = Utils.createRandomPassword();

        signUpPage.signUp(username, email, password);
        driver.get(baseUrl + "register");
        signUpPage.signUp(username, email, password);

        assertTrue(isAlertPresent());
    }

    public static Stream<Arguments> credentialsProvider() {
        return CredentialsLoader.getSignUpCredentials();
    }
}
