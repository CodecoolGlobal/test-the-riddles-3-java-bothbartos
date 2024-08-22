package tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QuizFormPageTest extends BaseTest {

    @BeforeEach
    public void beforeEach() {
        initializeDriver();
        driver.get(baseUrl);
        driver.manage().window().maximize();

        mainPage.clickLogin();
        loginPage.login(dotenv.get("USERNAME_1"), dotenv.get("PASSWORD_1"));
        mainPage.clickMyQuizzes();
    }

    @AfterEach
    public void afterEach() {
        logoutIfLoggedIn();
        driver.close();
    }

    @Test
    @DisplayName("Create new quiz after logging in")
    public void createNewQuizWithLoggingInTest() {

        Map<String, Boolean> answers = new HashMap<>();
        answers.put("true", true);
        answers.put("false", false);
        createNewQuiz(dotenv.get("QUIZ_TITLE_1"), dotenv.get("QUIZ_QUESTION_1"), answers);

        mainPage.clickMyQuizzes();

        assertTrue(assertQuizDivContainsText(dotenv.get("QUIZ_TITLE_1")));

        mainPage.clickAllQuizzes();
        assertTrue(assertQuizDivContainsText(dotenv.get("QUIZ_TITLE_1")));
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
        answers.put(dotenv.get("QUIZ_1_ANSWER_1"), false);
        answers.put(dotenv.get("QUIZ_1_ANSWER_2"), true);
        answers.put(dotenv.get("QUIZ_1_ANSWER_3"), false);
        createNewQuiz(dotenv.get("QUIZ_TITLE_1"), dotenv.get("QUIZ_QUESTION_1"), answers);

        assertTrue(assertQuizDivContainsText(dotenv.get("QUIZ_TITLE_1")));
        deleteQuizzes();

    }

    @Test
    @DisplayName("Create new quiz then add every answer option")
    public void createNewQuizThenAddEveryAnswerOptionTest() {
        Map<String, Boolean> answers = new HashMap<>();
        answers.put(dotenv.get("QUIZ_2_ANSWER_1"), true);
        answers.put(dotenv.get("QUIZ_2_ANSWER_2"), false);
        answers.put(dotenv.get("QUIZ_2_ANSWER_3"), false);
        answers.put(dotenv.get("QUIZ_2_ANSWER_4"), false);
        answers.put(dotenv.get("QUIZ_2_ANSWER_5"), false);
        answers.put(dotenv.get("QUIZ_2_ANSWER_6"), false);

        createNewQuiz(dotenv.get("QUIZ_TITLE_2"), dotenv.get("QUIZ_QUESTION_2"), answers);

        assertTrue(assertQuizDivContainsText(dotenv.get("QUIZ_TITLE_2")));
        deleteQuizzes();
    }

    @Test
    @DisplayName("Create quiz without correct answer checkbox checked")
    public void createNewQuizWithoutCorrectAnswerCheckboxCheckedTest() {
        Map<String, Boolean> answers = new HashMap<>();
        answers.put(dotenv.get("QUIZ_2_ANSWER_1"), false);
        answers.put(dotenv.get("QUIZ_2_ANSWER_2"), false);

        createNewQuiz(dotenv.get("QUIZ_TITLE_3"), dotenv.get("QUIZ_QUESTION_3"), answers);

        mainPage.clickAllQuizzes();
        assertFalse(assertQuizDivContainsText(dotenv.get("QUIZ_TITLE_3")));
        deleteQuizzes();

    }

    @Test
    @DisplayName("Choose multiple correct answers")
    public void chooseMultipleCorrectAnswersTest() {

        Map<String, Boolean> answers = new HashMap<>();
        answers.put(dotenv.get("QUIZ_4_ANSWER_1"), true);
        answers.put(dotenv.get("QUIZ_4_ANSWER_2"), true);
        createNewQuiz(dotenv.get("QUIZ_TITLE_4"), dotenv.get("QUIZ_QUESTION_4"), answers);

        assertTrue(assertQuizDivContainsText(dotenv.get("QUIZ_TITLE_4")));
        mainPage.clickMyQuizzes();
        myQuizzesPage.editQuiz(dotenv.get("QUIZ_TITLE_4"));
        assertTrue(quizFormPage.isEveryCheckboxChecked());
        deleteQuizzes();

    }

    @Test
    @DisplayName("Edit quiz title")
    public void editQuizTitleTest() {
        Map<String, Boolean> answers = new HashMap<>();
        answers.put(dotenv.get("QUIZ_1_ANSWER_1"), true);
        answers.put(dotenv.get("QUIZ_1_ANSWER_2"), false);
        createNewQuiz(dotenv.get("QUIZ_TITLE_1"), dotenv.get("QUIZ_QUESTION_1"), answers);
        assertTrue(assertQuizDivContainsText(dotenv.get("QUIZ_TITLE_1")));
        mainPage.clickMyQuizzes();

        myQuizzesPage.editQuiz(dotenv.get("QUIZ_TITLE_1"));
        quizFormPage.enterQuizTitle(dotenv.get("QUIZ_TITLE_2"));
        quizFormPage.clickSaveQuizButton();
        handleConfirmationAlert(true);

        mainPage.clickMyQuizzes();
        assertQuizDivContainsText(dotenv.get("QUIZ_TITLE_2"));
        deleteQuizzes();
    }

    @Test
    @DisplayName("user can delete quiz from editor")
    void userCanDeleteQuizFromEditorTest() {
        Map<String, Boolean> answers = new HashMap<>();
        answers.put(dotenv.get("QUIZ_1_ANSWER_1"), true);
        answers.put(dotenv.get("QUIZ_1_ANSWER_2"), false);
        createNewQuiz(dotenv.get("QUIZ_TITLE_1"), dotenv.get("QUIZ_QUESTION_1"), answers);

        mainPage.clickMyQuizzes();
        assertTrue(assertQuizDivContainsText(dotenv.get("QUIZ_TITLE_1")));

        myQuizzesPage.editQuiz(dotenv.get("QUIZ_TITLE_1"));
        quizFormPage.clickOnDeleteButton();
        handleConfirmationAlert(true);

        assertFalse(assertQuizDivContainsText(dotenv.get("QUIZ_TITLE_2")));
    }

    @Test
    @DisplayName("user can edit the quiz")
    void userCanEditTheQuizTest() {
        Map<String, Boolean> answers = new HashMap<>();
        answers.put(dotenv.get("QUIZ_1_ANSWER_1"), true);
        answers.put(dotenv.get("QUIZ_1_ANSWER_2"), false);

        createNewQuiz(dotenv.get("QUIZ_TITLE_1"), dotenv.get("QUIZ_QUESTION_1"), answers);
        mainPage.clickMyQuizzes();

        assertTrue(assertQuizDivContainsText(dotenv.get("QUIZ_TITLE_1")));

        myQuizzesPage.editQuiz(dotenv.get("QUIZ_TITLE_1"));
        quizFormPage.enterQuizTitle("Modified Quiz");
        quizFormPage.clickSaveQuizButton();
        handleConfirmationAlert(true);


        mainPage.clickMyQuizzes();
        assertTrue(assertQuizDivContainsText("Modified Quiz"));
        myQuizzesPage.deleteQuiz("Modified Quiz");
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
        mainPage.clickMyQuizzes();
        myQuizzesPage.deleteQuiz("");
        deleteQuizzes();

    }

    @ParameterizedTest
    @MethodSource("timeCredentials")
    @DisplayName("user can add various time intervals")
    void userCanAddVariousTimeIntervalsTest(
            String timeIntervalText,
            String quizTitle,
            String questionText,
            String answerText1,
            String answerText2,
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

    public static Stream<Arguments> timeCredentials() {
        return CredentialsLoader.loadTimeCredentials();
    }

}