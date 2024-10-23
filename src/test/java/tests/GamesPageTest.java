package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import pages.GamesPage;
import pages.LoginPage;
import pages.QuizGamePage;
import pages.SignUpPage;
import tests.utils.Utils;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GamesPageTest extends BaseTest {

    private WebDriver newPlayerDriver;
    private FluentWait<WebDriver> newPlayerFluentWait;
    private SignUpPage newPlayerSignUpPage;
    private LoginPage newPlayerLoginPage;
    private GamesPage newPlayerGamesPage;
    private QuizGamePage newPlayerQuizGamePage;

    @BeforeEach
    void setUp() {
        initializeDriver();
        driver.manage().window().maximize();

        driver.get(baseUrl + "register");

        String username = Utils.createRandomUsername();
        String password = Utils.createRandomPassword();
        String email = Utils.createRandomEmail();


        signUpPage.signUp(username, email, password);

        wait.until(ExpectedConditions.urlToBe(baseUrl + "login"));

        loginPage.login(username, password);

        wait.until(ExpectedConditions.urlToBe(baseUrl));
        mainPage.clickMyQuizzes();
    }

    @Test
    @DisplayName("Find game by name and join")
    public void findGameByNameAndJoinTest() {
        Map<String, Boolean> answers = new HashMap<>();
        answers.put("Yes", true);
        answers.put("No", false);
        String quizTitle = "test quiz" +  Utils.getRandomNum();
        createNewQuiz(quizTitle, "Can I Join?", answers);
        mainPage.clickMyQuizzes();
        myQuizzesPage.createGameLobby(quizTitle);

        String newPlayerUsername = createNewPlayerDriver();
        joinGameWithNewPlayer(quizTitle);

        quizGamePage.clickStartButton();

        newPlayerQuizGamePage.clickAnswerButton("Yes");

        quizGamePage.clickResultButton();

        assertTrue(quizGamePage.isUsernameOnScoreBoard(newPlayerUsername));
        assertTrue(quizGamePage.isScoreHigherThanZero());

        quitNewPlayerDriver();
    }


    @Test
    @DisplayName("Join game and rename player then join")
    public void joinGameAndRenamePlayerTest() {
        Map<String, Boolean> answers = new HashMap<>();
        answers.put("Yes", true);
        answers.put("No", false);
        String randomNum = Utils.getRandomNum();
        createNewQuiz("test quiz" + randomNum, "Can I Join and rename player?", answers);
        mainPage.clickMyQuizzes();
        myQuizzesPage.createGameLobby("test quiz" + randomNum);

        createNewPlayerDriver();
        String newPlayerUsername = "RENAMED_USER" + randomNum;
        joinGameWithNewPlayer("test quiz" + randomNum, newPlayerUsername);

        quizGamePage.clickStartButton();

        newPlayerQuizGamePage.clickAnswerButton("Yes");

        quizGamePage.clickResultButton();

        assertTrue(quizGamePage.isUsernameOnScoreBoard(newPlayerUsername));
        assertTrue(quizGamePage.isScoreHigherThanZero());

        quitNewPlayerDriver();
    }

    @Test
    @DisplayName("Create a new invalid quiz, then try to start a new game")
    public void createInvalidQuizThenStartNewGameTest() {
        mainPage.clickMyQuizzes();

        myQuizzesPage.clickOnAddQuiz();

        String quizTitle = "test quiz" + Utils.getRandomNum();
        quizFormPage.enterQuizTitle(quizTitle);
        quizFormPage.clickSaveQuizButton();
        handleConfirmationAlert(true);

        mainPage.clickMyQuizzes();

        myQuizzesPage.createGameLobby(quizTitle);


        assertTrue(myQuizzesPage.isSadEmojiButtonPresent());
    }

    @Test
    @DisplayName("Join game, then select wrong answer")
    public void joinGameWithWrongAnswerTest() {
        Map<String, Boolean> answers = new HashMap<>();
        answers.put("Yes", true);
        answers.put("No", false);
        String quizTitle = "test quiz" +  Utils.getRandomNum();
        createNewQuiz(quizTitle, "Can I Join?", answers);
        mainPage.clickMyQuizzes();
        myQuizzesPage.createGameLobby(quizTitle);

        String newPlayerUsername = createNewPlayerDriver();
        joinGameWithNewPlayer(quizTitle);

        quizGamePage.clickStartButton();

        newPlayerQuizGamePage.clickAnswerButton("No");

        quizGamePage.clickResultButton();

        assertTrue(quizGamePage.isUsernameOnScoreBoard(newPlayerUsername));
        assertFalse(quizGamePage.isScoreHigherThanZero());

        quitNewPlayerDriver();
    }

    private String createNewPlayerDriver(){
        newPlayerDriver = new ChromeDriver(options);
        newPlayerFluentWait = new FluentWait<>(newPlayerDriver)
                .withTimeout(Duration.ofSeconds(4))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(NoSuchElementException.class);
        newPlayerSignUpPage = new SignUpPage(newPlayerDriver, newPlayerFluentWait);
        newPlayerLoginPage = new LoginPage(newPlayerDriver, newPlayerFluentWait);
        newPlayerGamesPage = new GamesPage(newPlayerDriver, newPlayerFluentWait);
        newPlayerQuizGamePage = new QuizGamePage(newPlayerDriver, newPlayerFluentWait);

        String username = Utils.createRandomUsername();
        String password = Utils.createRandomPassword();
        String email = Utils.createRandomEmail();

        newPlayerDriver.manage().window().maximize();
        newPlayerDriver.get(baseUrl + "register");

        newPlayerSignUpPage.signUp(username, email, password);

        newPlayerFluentWait.until(ExpectedConditions.urlToBe(baseUrl + "login"));
        newPlayerLoginPage.login(username, password);
        newPlayerFluentWait.until(ExpectedConditions.urlToBe(baseUrl));
        return username;
    }

    private void joinGameWithNewPlayer(String title) {
        newPlayerDriver.get(baseUrl + "gamelist");
        newPlayerGamesPage.clickJoinGameByName(title);
        newPlayerQuizGamePage.clickJoinButton();
    }

    private void joinGameWithNewPlayer(String title, String newPlayerUsername) {
        newPlayerDriver.get(baseUrl + "gamelist");
        newPlayerGamesPage.clickJoinGameByName(title);
        newPlayerQuizGamePage.renamePlayer(newPlayerUsername);
        newPlayerQuizGamePage.clickJoinButton();
    }

    private void quitNewPlayerDriver() {
        newPlayerDriver.quit();
    }
}