package tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.support.ui.ExpectedConditions;
import tests.Utils.CredentialsLoader;
import tests.Utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class QuizFormPageTest extends BaseTest {

    @BeforeEach
    public void beforeEach() {
        initializeDriver();
        driver.get(baseUrl + "register");
        driver.manage().window().maximize();
        String username = Utils.createRandomUsername();
        String email = Utils.createRandomEmail();
        String password = Utils.createRandomPassword();
        signUpPage.signUp(username, email, password);
        wait.until(ExpectedConditions.urlToBe(baseUrl + "login"));
        loginPage.login(username, password);
        wait.until(ExpectedConditions.urlToBe(baseUrl));
        mainPage.clickMyQuizzes();
    }

    @AfterEach
    public void afterEach() {
        logoutIfLoggedIn();
        driver.close();
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/quiz_form_fields.csv", numLinesToSkip = 1)
    @DisplayName("Create new quiz after logging in")
    public void createNewQuizWithLoggingInTest(String title,
                                               String question,
                                               String answer1,
                                               String answer2) {

        Map<String, Boolean> answers = new HashMap<>();
        answers.put(answer1, true);
        answers.put(answer2, false);
        createNewQuiz(title, question, answers);

        mainPage.clickMyQuizzes();

        assertTrue(assertQuizDivContainsText(title));

        mainPage.clickAllQuizzes();
        assertTrue(assertQuizDivContainsText(title));
        deleteQuizzes();
    }

    @Test
    @DisplayName("Create new quiz without logging in is not possible")
    public void createNewQuizWithoutLoggingInTest() {
        mainPage.clickLogout();
        mainPage.clickMyQuizzes();
        String actual = driver.getCurrentUrl();
        assertEquals(baseUrl + "login", actual);
    }

    @Test
    @DisplayName("Create new quiz then add a question to it")
    public void createNewQuizThenAddQuestionToItTest() {

        Map<String, Boolean> answers = new HashMap<>();
        answers.put("answer1", false);
        answers.put("answer2", true);
        answers.put("answer3", false);

        String randomNum = Utils.getRandomNum();
        createNewQuiz("test quiz" + randomNum, "Can I add new answer?", answers);

        driver.get(baseUrl + "quiz/all");
        assertTrue(assertQuizDivContainsText("test quiz" + randomNum));



        driver.get(baseUrl + "quiz/my");
        assertTrue(assertQuizDivContainsText("test quiz" + randomNum));
        deleteQuizzes();

    }

    @Test
    @DisplayName("Create new quiz then add every answer option")
    public void createNewQuizThenAddEveryAnswerOptionTest() {
        Map<String, Boolean> answers = new HashMap<>();
        answers.put("answer1", true);
        answers.put("answer2", false);
        answers.put("answer3", false);
        answers.put("answer4", false);
        answers.put("answer5", false);
        answers.put("answer6", false);

        String randomNum = Utils.getRandomNum();
        createNewQuiz("test quiz" + randomNum, "Can I add every answer?", answers);

        assertTrue(assertQuizDivContainsText("test quiz" + randomNum));
        deleteQuizzes();
    }

    @Test
    @DisplayName("Create quiz without correct answer checkbox checked")
    public void createNewQuizWithoutCorrectAnswerCheckboxCheckedTest() {
        Map<String, Boolean> answers = new HashMap<>();
        answers.put("yes", false);
        answers.put("no", false);

        String randomNum = Utils.getRandomNum();
        createNewQuiz("test quiz" + randomNum, "no correct answer?", answers);

        mainPage.clickAllQuizzes();
        assertFalse(assertQuizDivContainsText("test quiz" + randomNum));
        deleteQuizzes();

    }

    @Test
    @DisplayName("Choose multiple correct answers")
    public void chooseMultipleCorrectAnswersTest() {

        Map<String, Boolean> answers = new HashMap<>();
        answers.put("yes", true);
        answers.put("no", true);

        String randomNum = Utils.getRandomNum();
        createNewQuiz("test quiz" + randomNum, "Can i choose multiple good answers?", answers);

        assertTrue(assertQuizDivContainsText("test quiz" + randomNum));
        mainPage.clickMyQuizzes();
        myQuizzesPage.editQuiz("test quiz" + randomNum);
        assertTrue(quizFormPage.isEveryCheckboxChecked());
        deleteQuizzes();

    }

    @Test
    @DisplayName("Edit quiz title")
    public void editQuizTitleTest() {
        Map<String, Boolean> answers = new HashMap<>();
        answers.put("yes", true);
        answers.put("no", false);

        String randomNum = Utils.getRandomNum();
        createNewQuiz("test quiz" + randomNum, "Can I edit quiz title?", answers);
        assertTrue(assertQuizDivContainsText("test quiz" + randomNum));
        mainPage.clickMyQuizzes();

        myQuizzesPage.editQuiz("test quiz" + randomNum);
        quizFormPage.enterQuizTitle("Is it changed?");
        quizFormPage.clickSaveQuizButton();
        handleConfirmationAlert(true);

        mainPage.clickMyQuizzes();
        assertQuizDivContainsText("test quiz" + randomNum);
        deleteQuizzes();
    }

    @Test
    @DisplayName("user can delete quiz from editor")
    void userCanDeleteQuizFromEditorTest() {
        Map<String, Boolean> answers = new HashMap<>();
        answers.put("yes", true);
        answers.put("no", false);
        String randomNum = Utils.getRandomNum();
        createNewQuiz("test quiz" + randomNum, "Can I delete quiz from editor?", answers);

        driver.get(baseUrl + "quiz/my");
        assertTrue(assertQuizDivContainsText("test quiz" + randomNum));

        myQuizzesPage.editQuiz("test quiz" + randomNum);
        quizFormPage.clickOnDeleteButton();
        handleConfirmationAlert(true);

        assertFalse(assertQuizDivContainsText("test quiz" + randomNum));
    }

    @Test
    @DisplayName("user can create quiz with empty check boxes")
    void userCanCreateQuizWithEmptyBoxesTest() {
        Map<String, Boolean> answers = new HashMap<>();
        answers.put("", false);
        answers.put(" ", false);

        createNewQuiz("", "", answers);

        mainPage.clickAllQuizzes();
        assertFalse(assertQuizDivContainsText(""));

        deleteQuizzes();

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/quiz_form_fields.csv", numLinesToSkip = 1)
    @DisplayName("user can add various time intervals")
    void userCanAddVariousTimeIntervalsTest(
            String quizTitle,
            String questionText,
            String answerText1,
            String answerText2,
            String timeIntervalText,
            String expected
    ) {
        Map<String, Boolean> answers = new HashMap<>();
        answers.put(answerText1, true);
        answers.put(answerText2, false);
        createNewQuiz(quizTitle, questionText, timeIntervalText, answers);

        mainPage.clickMyQuizzes();

        assertTrue(assertQuizDivContainsText(quizTitle));

        myQuizzesPage.editQuiz(quizTitle);
        answerFormPage.clickOnQuestionButton();
        String actual = answerFormPage.getTimerText();
        assertEquals(expected, actual);
        deleteQuizzes();
    }
}