package tests;

import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MyQuizzesPageTest extends BaseTest {

    @BeforeEach
    public void setUp() {
        initializeDriver();
        driver.get(baseUrl);
        driver.manage().window().maximize();

        mainPage.clickLogin();
        loginPage.login(dotenv.get("USERNAME_1"), dotenv.get("PASSWORD_1"));
        mainPage.clickMyQuizzes();
    }

    @DisplayName("When the user logs in and clicks on My Quizzes on main page, My Quizzes page loads.")
    @Test
    public void testMyQuizzesPageLoads() {
        assertCorrectUrl(baseUrl + "quiz/my");
    }

    @Test
    @DisplayName("Test if user can delete quiz from editor")
    public void testDeleteQuizFromEditor() {
        myQuizzesPage.clickOnAddQuiz();
        quizFormPage.enterQuizTitle(dotenv.get("QUIZ_TITLE_5"));
        quizFormPage.clickOnDeleteButton();
        handleConfirmationAlert(true);
        mainPage.clickMyQuizzes();
        assertFalse(assertQuizDivContainsText(dotenv.get("QUIZ_TITLE_5")));
    }

    @Test
    public void createQuiz() {
        Map<String, Boolean> answers = new HashMap<>();
        answers.put( dotenv.get("QUIZ_6_ANSWER_1"), true);
        answers.put( dotenv.get("QUIZ_6_ANSWER_2"), false);
        answers.put(dotenv.get("QUIZ_6_ANSWER_3"), false);
        answers.put(dotenv.get("QUIZ_6_ANSWER_4"), false);
        answers.put(dotenv.get("QUIZ_6_ANSWER_5"), false);
        answers.put(dotenv.get("QUIZ_6_ANSWER_6"), false);
        createNewQuiz(dotenv.get("QUIZ_TITLE_1"), dotenv.get("QUIZ_QUESTION_6"), answers);
        assertCorrectUrl(baseUrl + "quiz/all");
    }

    @Test
    @DisplayName("Delete quiz after logging in")
    public void testDeleteQuizAfterLogin() {
        Map<String, Boolean> answers = new HashMap<>();
        answers.put("42", true);
        answers.put("33", false);

        createNewQuiz("Quiz to delete", "The Question", answers);
        assertTrue(assertQuizDivContainsText("Quiz to delete"));
        mainPage.clickMyQuizzes();

        myQuizzesPage.deleteQuiz("Quiz to delete");
        mainPage.clickAllQuizzes();
        assertFalse(assertQuizDivContainsText(dotenv.get("QUIZ_TITLE_6")));
    }

    @AfterEach
    public void tearDown() {
        logoutIfLoggedIn();
        driver.close();
    }

}
