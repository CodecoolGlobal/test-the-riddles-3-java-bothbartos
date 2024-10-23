package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import tests.utils.Utils;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginPageTest extends BaseTest{

    @BeforeEach
    public void setUp() {
        initializeDriver();
        driver.get(baseUrl + "register");
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void loginTest() {
        String username = Utils.createRandomUsername();
        String password = Utils.createRandomPassword();
        String email = Utils.createRandomEmail();

        signUpPage.signUp(username, email, password);

        wait.until(ExpectedConditions.urlToBe(baseUrl+"login"));
        loginPage.login(username, password);

        assertCorrectUrl(baseUrl);
    }

    @Test
    @DisplayName("Login with valid credentials then write /login in url")
    public void loginWithValidCredentialsThenWriteLoginInUrlTest() {
        String username = Utils.createRandomUsername();
        String email = Utils.createRandomEmail();
        String password = Utils.createRandomPassword();

        signUpPage.signUp(username, email, password);

        wait.until(ExpectedConditions.urlToBe(baseUrl+"login"));
        loginPage.login(username, password);

        driver.get(baseUrl + "login");

        assertFalse(loginPage.isLoginButtonPresent());
    }

    @DisplayName("Login then logout test")
    @Test
    public void loginLogoutTest() {
        String username = Utils.createRandomUsername();
        String password = Utils.createRandomPassword();
        String email = Utils.createRandomEmail();

        signUpPage.signUp(username, email, password);
        wait.until(ExpectedConditions.urlToBe(baseUrl+"login"));
        loginPage.login(username, password);
        assertCorrectUrl(baseUrl);
        mainPage.clickLogout();
        assertTrue(loginPage.isLoginButtonPresent());
    }
}
