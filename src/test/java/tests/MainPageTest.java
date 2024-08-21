package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pages.LoginPage;
import pages.MainPage;
import pages.SignUpPage;

import java.util.stream.Stream;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MainPageTest extends BaseTest {

    @ParameterizedTest
    @MethodSource("getLoginCredentials")
    public void testLoginLogout(String username, String password) {
        mainPage.clickLogin();
        loginPage.login(username, password);
        assertCorrectUrl(baseUrl);
        mainPage.clickLogout();
    }

    @ParameterizedTest
    @MethodSource("getSignUpCredentials")
    public void testSignUp(String username, String email, String password) {
        mainPage.clickSignUp();
        signUpPage.signUp(username, email, password);
        assertCorrectUrl(baseUrl+"login");
    }

    public static Stream<Arguments> getLoginCredentials() {
        return CredentialsLoader.getLoginCredentials();
    }

    public static Stream<Arguments> getSignUpCredentials() {
        return CredentialsLoader.getSignUpCredentials();
    }

}

