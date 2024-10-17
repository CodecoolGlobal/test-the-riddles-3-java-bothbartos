package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.LoginPage;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class GamesPageTest extends BaseTest {

    @BeforeEach
    void setUp() {
        initializeDriver();
        driver.manage().window().maximize();

        driver.get(baseUrl + "login");
        loginPage.login(dotenv.get("USERNAME_1"), dotenv.get("PASSWORD_1"));
    }


    @Test
    @DisplayName("Start new quiz game")
    public void startNewQuizGameTest() {
        startNewGame();

        assertUrlContains("game/lobby/");

        deleteQuizzes();

    }

    @Test
    @DisplayName("Find game by name and join")
    public void findGameByNameAndJoinTest() {
        startNewGame();

        driver.get(baseUrl);

        mainPage.clickGames();
        gamesPage.clickJoinGameByName(dotenv.get("QUIZ_TITLE_1"));
        assertUrlContains("game/quiz");

        deleteQuizzes();
    }

    @Test
    @DisplayName("Join game and rename player then join")
    public void joinGameAndRenamePlayerTest() {
        startNewGame();

        driver.get(baseUrl);

        mainPage.clickGames();
        gamesPage.clickJoinGameByName(dotenv.get("QUIZ_TITLE_1"));
        gamesPage.renamePlayer("PLAYER");
        assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Good luck!']"))).isDisplayed());
        deleteQuizzes();
    }

    @Test
    @DisplayName("Create a new invalid quiz, then try to start a new game")
    public void createInvalidQuizThenStartNewGameTest() {
        mainPage.clickMyQuizzes();

        myQuizzesPage.clickOnAddQuiz();

        quizFormPage.enterQuizTitle(dotenv.get("QUIZ_TITLE_2"));
        quizFormPage.clickSaveQuizButton();
        handleConfirmationAlert(true);

        mainPage.clickMyQuizzes();

        myQuizzesPage.startInvalidGame(dotenv.get("QUIZ_TITLE_2"));


        assertTrue(myQuizzesPage.isSadEmojiButtonPresent());
        deleteQuizzes();
    }


    private void startNewGame() {

        driver.get(baseUrl + "quiz/my");
        Map<String, Boolean> answers = new HashMap<>();
        answers.put(dotenv.get("QUIZ_1_ANSWER_1"), true);
        answers.put(dotenv.get("QUIZ_1_ANSWER_2"), false);
        createNewQuiz(dotenv.get("QUIZ_TITLE_1"), dotenv.get("QUIZ_QUESTION_1"), answers);

        driver.get(baseUrl + "quiz/my");

        myQuizzesPage.playQuiz(dotenv.get("QUIZ_TITLE_1"));
    }

}