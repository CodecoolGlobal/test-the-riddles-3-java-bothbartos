package tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.stream.Stream;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginPageTest extends BaseTest{

    @BeforeEach
    public void navigateToLoginPage() {
        driver.get(baseUrl+"login");
    }

    @Order(1)
    @ParameterizedTest
    @MethodSource("credentialsProvider")
    public void testLogin(String username, String password) {
        loginPage.login(username, password);
        assertCorrectUrl(baseUrl);
    }

    @Order(2)
    @Test
    @DisplayName("User stays logged in after closing and opening browser")
    public void testLoginAfterClose() {
        ChromeOptions testOptions = new ChromeOptions();
        testOptions.addArguments("user-data-dir=/tmp/tarun");
        testOptions.addArguments("--disable-search-engine-choice-screen");
        WebDriver testDriver = new ChromeDriver(testOptions);

        testDriver.manage().window().maximize();
        testDriver.get(baseUrl);

        mainPage.clickLogin();
        loginPage.login(dotenv.get("USERNAME_1"), dotenv.get("PASSWORD_1"));

        testDriver.quit();

        testDriver = new ChromeDriver(options);
        testDriver.manage().window().maximize();
        testDriver.get(baseUrl);

        assertLogoutButtonIsPresent();
        testDriver.quit();
    }

    public static Stream<Arguments> credentialsProvider() {
        return CredentialsLoader.getLoginCredentials();
    }

}
