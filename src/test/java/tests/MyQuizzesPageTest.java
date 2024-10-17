package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import tests.Utils.CredentialsLoader;
import tests.Utils.Utils;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyQuizzesPageTest extends BaseTest {

    @BeforeEach
    public void setUp() {
        String username = Utils.createRandomUsername();
        String email = Utils.createRandomEmail();
        String password = Utils.createRandomPassword();

        initializeDriver();

        driver.manage().window().maximize();
        driver.get(baseUrl + "register");
        signUpPage.signUp(username, email, password);
        wait.until(ExpectedConditions.urlToBe(baseUrl + "login"));
        loginPage.login(username, password);
        mainPage.clickMyQuizzes();
    }

    @DisplayName("When the user logs in and clicks on My Quizzes on main page, My Quizzes page loads.")
    @Test
    public void testMyQuizzesPageLoads() {
        assertCorrectUrl(baseUrl + "quiz/my");
    }

    @Test
    @DisplayName("Delete quiz after logging in")
    public void testDeleteQuizAfterLogin() {
        Map<String, Boolean> answers = new HashMap<>();
        answers.put("42", true);
        answers.put("33", false);

        String randomNum = Utils.getRandomNum();
        createNewQuiz("test quiz" + randomNum, "The Question", answers);
        assertTrue(assertQuizDivContainsText("Quiz to delete"));
        mainPage.clickMyQuizzes();

        myQuizzesPage.deleteQuiz("test quiz" + randomNum);
        mainPage.clickAllQuizzes();
        assertFalse(assertQuizDivContainsText("test quiz" + randomNum));
    }

    @AfterEach
    public void tearDown() {
        logoutIfLoggedIn();
        driver.close();
    }

}
