package tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QuizFormPageTest extends BaseTest{

    @Test
    @DisplayName("Create new quiz after logging in")
    @Order(1)
    public void createNewQuizWithLoggingInTest(){
        mainPage.clickLogin();
        loginPage.login(dotenv.get("USERNAME_1"), dotenv.get("PASSWORD_1"));
        mainPage.clickMyQuizzes();

        myQuizzesPage.clickOnAddQuiz();
        quizFormPage.enterQuizTitle(dotenv.get("QUIZ_TITLE_1"));
        quizFormPage.clickSaveQuizButton();
        handleConfirmationAlert(true);

        mainPage.clickMyQuizzes();

        assertTrue(assertQuizDivContainsText(dotenv.get("QUIZ_TITLE_1")));

        mainPage.clickAllQuizzes();
        assertFalse(assertQuizDivContainsText(dotenv.get("QUIZ_TITLE_1")));
        mainPage.clickLogout();
    }

    @Test
    @DisplayName("Create new quiz without logging in")
    @Order(2)
    public void createNewQuizWithoutLoggingInTest(){
        mainPage.clickMyQuizzes();
        assertCorrectUrl(baseUrl + "login");
    }

    @Test
    @DisplayName("Create new quiz then add a question to it")
    @Order(3)
    public void createNewQuizThenAddQuestionToItTest(){
        mainPage.clickLogin();
        loginPage.login(dotenv.get("USERNAME_1"), dotenv.get("PASSWORD_1"));
        mainPage.clickMyQuizzes();

        quizFormPage.createNewQuiz(dotenv.get("QUIZ_TITLE_1"), dotenv.get("QUIZ_QUESTION_1"));

        answerFormPage.clickOnAddOptionButton();
        answerFormPage.enterFirstAnswer(dotenv.get("QUIZ_1_ANSWER_1"), false);
        answerFormPage.enterSecondAnswer(dotenv.get("QUIZ_1_ANSWER_2"), true);
        answerFormPage.enterThirdAnswer(dotenv.get("QUIZ_1_ANSWER_3"), false);

        quizFormPage.clickSaveQuizButton();
        handleConfirmationAlert(true);

        assertTrue(assertQuizDivContainsText(dotenv.get("QUIZ_TITLE_1")));
    }

    @Test
    @DisplayName("Create new quiz then add every answer option")
    @Order(4)
    public void createNewQuizThenAddEveryAnswerOptionTest(){
        mainPage.clickMyQuizzes();

        quizFormPage.createNewQuiz(dotenv.get("QUIZ_TITLE_2"), dotenv.get("QUIZ_QUESTION_2"));

        answerFormPage.clickOnAddOptionButton();
        answerFormPage.clickOnAddOptionButton();
        answerFormPage.clickOnAddOptionButton();
        answerFormPage.clickOnAddOptionButton();

        answerFormPage.enterFirstAnswer(dotenv.get("QUIZ_2_ANSWER_1"), true);
        answerFormPage.enterSecondAnswer(dotenv.get("QUIZ_2_ANSWER_2"), false);
        answerFormPage.enterThirdAnswer(dotenv.get("QUIZ_2_ANSWER_3"), false);
        answerFormPage.enterFourthAnswer(dotenv.get("QUIZ_2_ANSWER_4"), false);
        answerFormPage.enterFifthAnswer(dotenv.get("QUIZ_2_ANSWER_5"), false);
        answerFormPage.enterSixthAnswer(dotenv.get("QUIZ_2_ANSWER_6"), false);

        quizFormPage.clickSaveQuizButton();
        handleConfirmationAlert(true);

        assertTrue(assertQuizDivContainsText(dotenv.get("QUIZ_TITLE_2")));
    }

    @Test
    @DisplayName("Create quiz without correct answer checkbox checked")
    @Order(5)
    public void createNewQuizWithoutCorrectAnswerCheckboxCheckedTest(){
        mainPage.clickMyQuizzes();

        quizFormPage.createNewQuiz(dotenv.get("QUIZ_TITLE_3"), dotenv.get("QUIZ_QUESTION_3"));
        answerFormPage.enterFirstAnswer(dotenv.get("QUIZ_2_ANSWER_1"), false);
        answerFormPage.enterSecondAnswer(dotenv.get("QUIZ_2_ANSWER_2"), false);

        quizFormPage.clickSaveQuizButton();
        handleConfirmationAlert(true);

        mainPage.clickAllQuizzes();

        assertFalse(assertQuizDivContainsText(dotenv.get("QUIZ_TITLE_3")));
    }

    @Test
    @DisplayName("Choose multiple correct answers")
    @Order(6)
    public void chooseMultipleCorrectAnswersTest(){
        mainPage.clickMyQuizzes();
        quizFormPage.createNewQuiz(dotenv.get("QUIZ_TITLE_4"), dotenv.get("QUIZ_QUESTION_4"));
        answerFormPage.enterFirstAnswer(dotenv.get("QUIZ_4_ANSWER_1"), true);
        answerFormPage.enterSecondAnswer(dotenv.get("QUIZ_4_ANSWER_2"), true);

        answerFormPage.clickSaveQuestionButton();
        handleConfirmationAlert(true);
        quizFormPage.clickSaveQuizButton();
        handleConfirmationAlert(true);

        driver.get(baseUrl + "quiz/my");

        myQuizzesPage.editQuiz(dotenv.get("QUIZ_TITLE_4"));

        assertTrue(quizFormPage.isEveryCheckboxChecked());
    }

    @Test
    @DisplayName("Edit quiz title")
    @Order(7)
    public void editQuizTitleTest(){
        mainPage.clickMyQuizzes();

        myQuizzesPage.editQuiz(dotenv.get("QUIZ_TITLE_1"));

        quizFormPage.enterQuizTitle(dotenv.get("QUIZ_TITLE_2"));
        quizFormPage.clickSaveQuizButton();
        handleConfirmationAlert(true);

       assertTrue(assertQuizDivContainsText(dotenv.get("QUIZ_TITLE_2")));
    }

    @Order(8)
    @Test
    @DisplayName("user can delete quiz from editor")
    void userCanDeleteQuizFromEditorTest(){
        mainPage.clickMyQuizzes();
        myQuizzesPage.clickOnAddQuiz();
        quizFormPage.enterQuizTitle("My Quiz");
        quizFormPage.clickSaveQuizButton();
        handleConfirmationAlert(true);
        driver.get("http://localhost:3000/quiz/my");
        myQuizzesPage.editQuiz("My Quiz");
        quizFormPage.clickOnDeleteButton();
        handleConfirmationAlert(true);
        assertFalse(assertQuizDivContainsText("My Quiz"));
    }

    @Order(9)
    @Test
    @DisplayName("user can edit the quiz")
    void userCanEditTheQuizTest(){

        mainPage.clickMyQuizzes();
        myQuizzesPage.clickOnAddQuiz();
        quizFormPage.enterQuizTitle("My Quiz");
        quizFormPage.clickSaveQuizButton();
        handleConfirmationAlert(true);
        driver.get("http://localhost:3000/quiz/my");
        myQuizzesPage.editQuiz("My Quiz");
        quizFormPage.enterQuizTitle("Modified Quiz");
        quizFormPage.clickSaveQuizButton();
        handleConfirmationAlert(true);
        assertFalse(assertQuizDivContainsText("Modified Quiz"));
        driver.get("http://localhost:3000/quiz/my");
        myQuizzesPage.deleteQuiz("Modified Quiz");
    }

    @Order(10)
    @Test
    @DisplayName("user can create quiz with empty boxes")
    void userCanCreateQuizWithEmptyBoxesTest(){
        mainPage.clickMyQuizzes();
        myQuizzesPage.clickOnAddQuiz();
        quizFormPage.enterQuizTitle("My Quiz");
        quizFormPage.clickSaveQuizButton();
        handleConfirmationAlert(true);
        driver.get("http://localhost:3000/quiz/my");
        myQuizzesPage.editQuiz("My Quiz");
        quizFormPage.enterQuizTitle(" ");
        quizFormPage.clickOnAddQuestionButton();
        quizFormPage.enterQuestion("");
        answerFormPage.setTimer("");
        answerFormPage.enterFirstAnswer("", false);
        answerFormPage.enterSecondAnswer("", false);
        quizFormPage.clickSaveQuizButton();
        handleConfirmationAlert(true);
        assertFalse(assertQuizDivContainsText(""));
        driver.get("http://localhost:3000/quiz/my");
        myQuizzesPage.deleteQuiz("");
    }

    @Order(11)
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
    ){
        mainPage.clickMyQuizzes();
        myQuizzesPage.clickOnAddQuiz();
        quizFormPage.enterQuizTitle(quizTitle);
        quizFormPage.clickSaveQuizButton();
        handleConfirmationAlert(true);
        driver.get("http://localhost:3000/quiz/my");
        myQuizzesPage.editQuiz(quizTitle);
        quizFormPage.enterQuizTitle(quizTitle);
        quizFormPage.clickOnAddQuestionButton();
        quizFormPage.enterQuestion(questionText);
        answerFormPage.setTimer(timeIntervalText);
        answerFormPage.enterFirstAnswer(answerText1, false);
        answerFormPage.enterSecondAnswer(answerText2, false);
        quizFormPage.clickSaveQuizButton();
        handleConfirmationAlert(true);
        driver.get("http://localhost:3000/quiz/my");
        myQuizzesPage.editQuiz(quizTitle);
        answerFormPage.clickOnQuestionButton();
        String actual = answerFormPage.getTimerText();
        assertEquals(expected, actual);
    }

    public static Stream<Arguments> timeCredentials() {
        return CredentialsLoader.loadTimeCredentials();
    }


}