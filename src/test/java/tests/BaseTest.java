package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public abstract class BaseTest {
    protected static WebDriver driver;
    protected static WebDriverWait wait;
    protected static String baseUrl;
    protected static Dotenv dotenv;
    protected static ChromeOptions options;

    protected static MainPage mainPage;
    protected static LoginPage loginPage;
    protected static SignUpPage signUpPage;
    protected static MyQuizzesPage myQuizzesPage;
    protected static QuizFormPage quizFormPage;
    protected static AnswerForm answerFormPage;
    protected static GamesPage gamesPage;
    protected static QuizzesPage quizzesPage;

    @BeforeAll
    public static void setup() {
        dotenv = Dotenv.load();
        baseUrl = dotenv.get("ROOT_URL");
    }

    @BeforeEach
    public void before() {
        options = new ChromeOptions();
        options.addArguments("--disable-search-engine-choice-screen");
        options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));

        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false
        ));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public static void initializeDriver() {
        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        mainPage = new MainPage(driver, wait);
        loginPage = new LoginPage(driver, wait);
        signUpPage = new SignUpPage(driver, wait);
        myQuizzesPage = new MyQuizzesPage(driver, wait);
        answerFormPage = new AnswerForm(driver, wait);
        quizFormPage = new QuizFormPage(driver, wait, answerFormPage);
        gamesPage = new GamesPage(driver, wait);
        quizzesPage = new QuizzesPage(driver, wait);
    }

    protected void logoutIfLoggedIn() {
        try {
            driver.findElement(By.xpath("//*[text()='Logout']"));
            mainPage.clickLogout();
        } catch (Exception e) {
            System.out.println("You are not logged in");
        }
    }

    protected boolean waitForUrlToBe(String url) {
        try {
            return wait.until(ExpectedConditions.urlToBe(url));
        } catch (Exception e) {
            return false;
        }
    }

    protected void assertCorrectUrl(String url) {
        assertTrue(waitForUrlToBe(url), "Expected URL: " + url + ", but was: " + driver.getCurrentUrl());
    }

    private boolean waitForUrlToContain(String url) {
        try {
            return wait.until(ExpectedConditions.urlContains(url));
        } catch (Exception e) {
            return false;
        }
    }

    protected void assertUrlContains(String url) {
        assertTrue(waitForUrlToContain(url), "Expected URL: " + url + ", but was: " + driver.getCurrentUrl());
    }

    protected void handleConfirmationAlert(boolean ok) {
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        assertNotNull(alert);
        if (ok) {
            alert.accept();
        } else {
            alert.dismiss();
        }
    }

    protected boolean assertQuizDivContainsText(String expected) {
        try {
            WebElement quizzesDiv = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='grow pt-16']")));
            wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(quizzesDiv, By.xpath(".//span")));
            List<WebElement> quizTitles = quizzesDiv.findElements(By.xpath(".//span"));
            if (quizTitles != null) {
                for (WebElement quizTitle : quizTitles) {
                    if (quizTitle.getText().equals(expected)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Couldn't find quiz div");
            return false;
        }
        return false;
    }

    protected void assertLogoutButtonIsPresent() {
        WebElement logoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[normalize-space()='Logout']")));
        assertTrue(logoutButton.isDisplayed());
    }

    protected boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    public void deleteAllQuizzes() {
        driver.get(baseUrl);
        logoutIfLoggedIn();
        mainPage.clickLogin();
        loginPage.login(dotenv.get("USERNAME_1"), dotenv.get("PASSWORD_1"));
        mainPage.clickMyQuizzes();
        myQuizzesPage.deleteAllQuizzes();
        mainPage.clickLogout();
        loginPage.login(dotenv.get("USERNAME_2"), dotenv.get("PASSWORD_2"));
        mainPage.clickMyQuizzes();
        myQuizzesPage.deleteAllQuizzes();
        mainPage.clickLogout();
    }

    protected void createNewQuiz(String title, String question, Map<String, Boolean> options) {
        myQuizzesPage.clickOnAddQuiz();
        quizFormPage.enterQuizTitle(title);
        quizFormPage.clickOnAddQuestionButton();
        quizFormPage.enterQuestion(question);
        answerFormPage.enterAnswers(options);
        if (options.size() > 2) {
            answerFormPage.clickSaveQuestionButton();
            handleConfirmationAlert(true);
        }
        quizFormPage.clickSaveQuizButton();
        handleConfirmationAlert(true);
    }
    protected void createNewQuiz(String title, String question,String timeout, Map<String, Boolean> options) {
        myQuizzesPage.clickOnAddQuiz();
        quizFormPage.enterQuizTitle(title);
        quizFormPage.clickOnAddQuestionButton();
        quizFormPage.enterQuestion(question);
        answerFormPage.enterAnswers(options);
        answerFormPage.setTimer(timeout);
        if (options.size() > 2) {
            answerFormPage.clickSaveQuestionButton();
            handleConfirmationAlert(true);
        }
        quizFormPage.clickSaveQuizButton();
        handleConfirmationAlert(true);
    }

    public void deleteQuizzes(){
        driver.get(baseUrl);
        mainPage.clickMyQuizzes();
        myQuizzesPage.deleteAllQuizzes();
    }
}
