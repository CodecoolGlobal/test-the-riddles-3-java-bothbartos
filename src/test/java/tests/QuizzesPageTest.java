package tests;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class QuizzesPageTest extends BaseTest {

    @Order(1)
    @Test
    @DisplayName("Edit button only clickable when logged in")
    public void editButtonOnQuizTest() {
        mainPage.clickMyQuizzes();
        assertCorrectUrl(baseUrl + "login");
    }


    @Order(2)
    @Test
    @DisplayName("Create quiz with one profile then switch to an other and copy created quiz")
    public void createQuizWithOneProfileThenCopyQuizWithOtherProfileTest() {
        mainPage.clickLogin();
        loginPage.login(dotenv.get("USERNAME_1"), dotenv.get("PASSWORD_1"));
        mainPage.clickAllQuizzes();
        quizzesPage.clickAddQuizButton();
        quizFormPage.enterQuizTitle(dotenv.get("QUIZ_TITLE_3"));
        quizFormPage.clickOnAddQuestionButton();
        quizFormPage.enterQuestion(dotenv.get("QUIZ_QUESTION_3"));
        answerFormPage.enterFirstAnswer(dotenv.get("QUIZ_3_ANSWER_1"), true);
        answerFormPage.enterSecondAnswer(dotenv.get("QUIZ_3_ANSWER_2"), false);
        answerFormPage.clickSaveQuestionButton();
        handleConfirmationAlert(true);
        quizFormPage.clickSaveQuizButton();
        handleConfirmationAlert(true);
        mainPage.clickLogout();
        loginPage.login(dotenv.get("USERNAME_2"), dotenv.get("PASSWORD_2"));
        mainPage.clickAllQuizzes();
        quizzesPage.copyQuizByTitle(dotenv.get("QUIZ_TITLE_3"));
        quizFormPage.enterQuizTitle(dotenv.get("QUIZ_TITLE_1"));
        quizFormPage.clickSaveQuizButton();
        handleConfirmationAlert(true);
        assertQuizDivContainsText(dotenv.get("QUIZ_TITLE_1"));
        mainPage.clickLogout();
    }

    @Order(3)
    @Test
    @DisplayName("Find quiz by title")
    public void findQuizByTitleTest() {
        mainPage.clickLogin();
        loginPage.login(dotenv.get("USERNAME_1"), dotenv.get("PASSWORD_1"));
        mainPage.clickAllQuizzes();
        assertTrue(quizzesPage.getQuizByTitle(dotenv.get("QUIZ_TITLE_3")).isDisplayed());
    }

}