package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import tests.utils.Utils;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class QuizzesPageTest extends BaseTest {

    @BeforeEach
    public void setupTest(){
        initializeDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl + "register");

        String username = Utils.createRandomUsername();
        String password = Utils.createRandomPassword();
        String email = Utils.createRandomEmail();

        signUpPage.signUp(username, email, password);

        wait.until(ExpectedConditions.urlToBe(baseUrl + "login"));
        loginPage.login(username, password);

        mainPage.clickAllQuizzes();
    }

    @Test
    @DisplayName("Edit button only clickable when logged in")
    public void editButtonOnQuizTest() {
        mainPage.clickLogout();
        mainPage.clickMyQuizzes();
        assertCorrectUrl(baseUrl + "login");
    }


    @Test
    @DisplayName("Create quiz with one profile then switch to an other and copy created quiz")
    public void createQuizWithOneProfileThenCopyQuizWithOtherProfileTest() {
        Map<String, Boolean> answers = new HashMap<>();
        answers.put("Yes", true);
        answers.put("No", false);

        String newUsername = Utils.createRandomUsername();
        String newPassword = Utils.createRandomPassword();
        String newEmail = Utils.createRandomEmail();
        String randomNum = Utils.getRandomNum();
        createNewQuiz("test quiz" + randomNum, "Will it fail?", answers);

        mainPage.clickLogout();
        mainPage.clickSignUp();

        signUpPage.signUp(newUsername, newEmail, newPassword);

        wait.until(ExpectedConditions.urlToBe(baseUrl + "login"));
        loginPage.login(newUsername, newPassword);

        mainPage.clickAllQuizzes();
        quizzesPage.copyQuizByTitle("test quiz" + randomNum);
        quizFormPage.enterQuizTitle("Will it change?");
        quizFormPage.clickSaveQuizButton();
        handleConfirmationAlert(true);
        assertQuizDivContainsText("Will it change?");
    }

    @Test
    @DisplayName("Find quiz by title")
    public void findQuizByTitleTest() {
        Map<String, Boolean> answers = new HashMap<>();
        answers.put("Yes", true);
        answers.put("No", false);

        String randomNum = Utils.getRandomNum();
        createNewQuiz("test quiz" + randomNum, "Will it fail?", answers);
        mainPage.clickAllQuizzes();
        assertTrue(quizzesPage.getQuizByTitle("test quiz" + randomNum).isDisplayed());
    }
    @AfterEach
    public void teardown(){
        logoutIfLoggedIn();
        driver.close();
    }
}