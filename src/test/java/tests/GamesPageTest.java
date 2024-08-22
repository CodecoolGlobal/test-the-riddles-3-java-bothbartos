package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class GamesPageTest extends BaseTest {

    @BeforeEach
    void setUp() {
        initializeDriver();
        driver.get(baseUrl + "login");
        driver.manage().window().maximize();
        loginPage.login(dotenv.get("USERNAME_1"), dotenv.get("PASSWORD_1"));
    }

    @AfterEach
    public void tearDown() {
        try{
            deleteAllQuizzes();
        }catch (Exception e){
            System.out.println("Error deleting quizzes");
        }
        if(driver !=null){
            driver.quit();
        }
    }

    @Test
    @DisplayName("Start new quiz game")
    public void startNewQuizGameTest() {
        startNewGame();

        assertUrlContains("game/lobby/");

    }

    @Test
    @DisplayName("Find game by name and join")
    public void findGameByNameAndJoinTest() {
        startNewGame();

        driver.get(baseUrl);

        mainPage.clickGames();
        gamesPage.clickJoinGameByName(dotenv.get("QUIZ_TITLE_1"));
        assertUrlContains("game/quiz");
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
    }

    @Test
    @DisplayName("Create a new invalid quiz, then try to start a new game")
    public void createInvalidQuizThenStartNewGameTest() {
        mainPage.clickMyQuizzes();

        myQuizzesPage.clickOnAddQuiz();

        quizFormPage.enterQuizTitle(dotenv.get("QUIZ_TITLE_1"));
        quizFormPage.clickSaveQuizButton();
        handleConfirmationAlert(true);

        mainPage.clickMyQuizzes();

        myQuizzesPage.startInvalidGame(dotenv.get("QUIZ_TITLE_1"));

        mainPage.clickGames();

        assertFalse(assertQuizDivContainsText(dotenv.get("QUIZ_TITLE_1")));
        deleteAllQuizzes();
    }


    private void startNewGame() {

        mainPage.clickMyQuizzes();
        Map<String, Boolean> answers = new HashMap<>();
        answers.put(dotenv.get("QUIZ_1_ANSWER_1"), true);
        answers.put(dotenv.get("QUIZ_1_ANSWER_2"), false);
        createNewQuiz(dotenv.get("QUIZ_TITLE_1"), dotenv.get("QUIZ_QUESTION_1"), answers);

        mainPage.clickMyQuizzes();

        myQuizzesPage.playQuiz(dotenv.get("QUIZ_TITLE_1"));

    }

}