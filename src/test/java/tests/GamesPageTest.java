package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

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


    private void startNewGame(){
        mainPage.clickMyQuizzes();
        quizFormPage.createNewQuiz(dotenv.get("QUIZ_TITLE_1"), dotenv.get("QUIZ_QUESTION_1"));
        answerFormPage.enterFirstAnswer(dotenv.get("QUIZ_1_ANSWER_1"), true);
        answerFormPage.enterSecondAnswer(dotenv.get("QUIZ_1_ANSWER_2"), false);

        quizFormPage.clickSaveQuizButton();
        handleConfirmationAlert(true);

        mainPage.clickMyQuizzes();

        myQuizzesPage.playQuiz(dotenv.get("QUIZ_TITLE_1"));

    }

}