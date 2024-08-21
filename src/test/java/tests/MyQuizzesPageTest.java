package tests;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MyQuizzesPageTest extends BaseTest {

    @DisplayName("When the user logs in and clicks on My Quizzes on main page, My Quizzes page loads.")
    @Test
    @Order(1)
    public void testMyQuizzesPageLoads() {
        logInAndNavigateToQuizPage();
        assertCorrectUrl(baseUrl+"quiz/my");
    }

    @Test
    @DisplayName("Test if user can delete quiz from editor")
    @Order(2)
    public void testDeleteQuizFromEditor() {
        mainPage.clickMyQuizzes();
        myQuizzesPage.clickOnAddQuiz();
        quizFormPage.enterQuizTitle(dotenv.get("QUIZ_TITLE_5"));
        quizFormPage.clickOnDeleteButton();
        handleConfirmationAlert(true);
        mainPage.clickMyQuizzes();
        assertFalse(assertQuizDivContainsText(dotenv.get("QUIZ_TITLE_5")));
    }

    @Test
    @Order(3)
    public void createQuiz(){
        mainPage.clickMyQuizzes();
        myQuizzesPage.clickOnAddQuiz();
        quizFormPage.enterQuizTitle(dotenv.get("QUIZ_TITLE_6"));
        quizFormPage.clickOnAddQuestionButton();
        quizFormPage.enterQuestion(dotenv.get("QUIZ_QUESTION_6"));
        answerFormPage.enterFirstAnswer(dotenv.get("QUIZ_6_ANSWER_1"), true);
        answerFormPage.enterSecondAnswer(dotenv.get("QUIZ_6_ANSWER_2"), false);
        quizFormPage.clickSaveQuizButton();
        handleConfirmationAlert(true);
        assertCorrectUrl(baseUrl+"quiz/all");
    }

    @Test
    @DisplayName("Delete quiz after logging in")
    @Order(4)
    public void testDeleteQuizAfterLogin() {
        mainPage.clickMyQuizzes();
        myQuizzesPage.deleteQuiz(dotenv.get("QUIZ_TITLE_6"));
        assertFalse(assertQuizDivContainsText(dotenv.get("QUIZ_TITLE_6")));
    }

    private void logInAndNavigateToQuizPage() {
        driver.get(baseUrl);
        mainPage.clickLogin();
        loginPage.login(dotenv.get("USERNAME_1"), dotenv.get("PASSWORD_1"));
        mainPage.clickMyQuizzes();
    }
}
