package tests;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class QuizzesPageTest extends BaseTest {

    @BeforeEach
    public void setupTest(){
        initializeDriver();
        driver.get(baseUrl);
        driver.manage().window().maximize();

        mainPage.clickLogin();
        loginPage.login(dotenv.get("USERNAME_1"), dotenv.get("PASSWORD_1"));
        mainPage.clickAllQuizzes();
    }

    @Test
    @DisplayName("Edit button only clickable when logged in")
    public void editButtonOnQuizTest() {
        mainPage.clickLogout();
        mainPage.clickMyQuizzes();
        assertCorrectUrl(baseUrl + "login");
    }


    @Test
    @DisplayName("Create quiz with one profile then switch to an other and copy created quiz")
    public void createQuizWithOneProfileThenCopyQuizWithOtherProfileTest() {
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
    }

    @Test
    @DisplayName("Find quiz by title")
    public void findQuizByTitleTest() {
        assertTrue(quizzesPage.getQuizByTitle(dotenv.get("QUIZ_TITLE_3")).isDisplayed());
    }
    @AfterEach
    public void teardown(){
        logoutIfLoggedIn();
    }
}