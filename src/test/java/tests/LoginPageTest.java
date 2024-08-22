package tests;


import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class LoginPageTest extends BaseTest{

    @BeforeEach
    public void setUp() {
        initializeDriver();
        driver.get(baseUrl + "login");
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @ParameterizedTest
    @MethodSource("credentialsProvider")
    public void testLogin(String username, String password) {
        loginPage.login(username, password);
        assertCorrectUrl(baseUrl);
    }

    @Test
    @DisplayName("User stays logged in after closing and opening browser")
    public void testLoginAfterClose() {
        driver.quit();
        options.addArguments("user-data-dir="+ dotenv.get("TEMP_FOLDER"));
        initializeDriver();
        driver.get(baseUrl + "login");
        driver.manage().window().maximize();


        loginPage.login(dotenv.get("USERNAME_1"), dotenv.get("PASSWORD_1"));

        driver.quit();

        initializeDriver();
        driver.get(baseUrl);

        assertLogoutButtonIsPresent();
        driver.quit();
    }

    @Test
    @DisplayName("Login with valid credentials then write /login in url")
    public void testLoginWithValidCredentialsThenWriteLoginInUrl() {
        loginPage.login(dotenv.get("USERNAME_1"), dotenv.get("PASSWORD_1"));
        driver.get(baseUrl + "login");
        assertFalse(loginPage.isLoginButtonPresent());
    }


    public static Stream<Arguments> credentialsProvider() {
        return CredentialsLoader.getLoginCredentials();
    }

}
