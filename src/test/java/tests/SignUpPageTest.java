package tests;

import org.junit.jupiter.api.*;
import tests.utils.Utils;


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
    public void signUpPageWithEmptyCredentialsTest() {
        signUpPage.enterUsername("");
        signUpPage.enterEmail("");
        signUpPage.enterPassword("");
        signUpPage.clickSignUpButton();
        assertTrue(isAlertPresent());
    }

    @Test
    public void signUpPageTest() {
        String username = Utils.createRandomUsername();
        String email = Utils.createRandomEmail();
        String password = Utils.createRandomPassword();

        signUpPage.signUp(username, email, password);

        assertCorrectUrl(baseUrl + "login");
    }

    @Test
    @DisplayName("Register with in-use username, email, password")
    public void signUpPageWithInUseUsernameTest() {
        String username = Utils.createRandomUsername();
        String email = Utils.createRandomEmail();
        String password = Utils.createRandomPassword();

        signUpPage.signUp(username, email, password);
        driver.get(baseUrl + "register");
        signUpPage.signUp(username, email, password);

        assertTrue(isAlertPresent());
    }
}
