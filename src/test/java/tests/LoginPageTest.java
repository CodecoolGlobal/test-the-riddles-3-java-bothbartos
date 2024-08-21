package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.MainPage;

import java.time.Duration;
import java.util.stream.Stream;

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
        options.addArguments("user-data-dir=/tmp/tarun");
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


    public static Stream<Arguments> credentialsProvider() {
        return CredentialsLoader.getLoginCredentials();
    }

}
