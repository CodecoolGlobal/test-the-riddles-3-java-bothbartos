package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertTrue;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GamesPageTest extends BaseTest {

    @Order(1)
    @Test
    @DisplayName("Start new quiz game")
    public void startNewQuizGameTest() {
        mainPage.clickLogin();
        loginPage.login(dotenv.get("USERNAME_1"), dotenv.get("PASSWORD_1"));

        mainPage.clickMyQuizzes();
        quizFormPage.createNewQuiz(dotenv.get("QUIZ_TITLE_1"), dotenv.get("QUIZ_QUESTION_1"));
        answerFormPage.enterFirstAnswer(dotenv.get("QUIZ_1_ANSWER_1"), true);
        answerFormPage.enterSecondAnswer(dotenv.get("QUIZ_1_ANSWER_2"), false);

        quizFormPage.clickSaveQuizButton();
        handleConfirmationAlert(true);

        mainPage.clickMyQuizzes();

        myQuizzesPage.playQuiz(dotenv.get("QUIZ_TITLE_1"));

        assertUrlContains("game/lobby/");
        driver.get(baseUrl);

    }

    @Order(2)
    @Test
    @DisplayName("Find game by name and join")
    public void findGameByNameAndJoinTest() {
        mainPage.clickGames();
        gamesPage.clickJoinGameByName(dotenv.get("QUIZ_TITLE_1"));
        assertUrlContains("game/quiz");
        driver.get(baseUrl);
    }

    @Order(3)
    @Test
    @DisplayName("Join game and rename player then join")
    public void joinGameAndRenamePlayerTest() {
        mainPage.clickGames();
        gamesPage.clickJoinGameByName(dotenv.get("QUIZ_TITLE_1"));
        gamesPage.renamePlayer("PLAYER");
        assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Good luck!']"))).isDisplayed());
        driver.get(baseUrl);
        mainPage.clickLogout();
    }

}