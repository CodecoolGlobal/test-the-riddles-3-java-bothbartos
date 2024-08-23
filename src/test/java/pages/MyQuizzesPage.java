package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MyQuizzesPage {
    private final WebDriverWait wait;
    private final WebDriver driver;
    @FindBy(xpath = "//button[text()='Add Quiz']")
    private WebElement addQuizButton;
    @FindBy(xpath = "//button[text()='Create game lobby']")
    private WebElement createGameLobbyButton;
    @FindBy(xpath = "//button[text()='Start']")
    private WebElement startGameButton;
    @FindBy(xpath = "//button[text()=':(']")
    private WebElement notValidGameButton;

    public MyQuizzesPage(WebDriver driver, WebDriverWait wait) {
        PageFactory.initElements(driver, this);
        this.wait = wait;
        this.driver = driver;
    }
    public void clickOnAddQuiz() {
        wait.until(ExpectedConditions.visibilityOf(addQuizButton));
        addQuizButton.click();
    }
    private WebElement getQuizByTitle(String title) {
        List<WebElement> quizDivs =  getQuizzes();
        for (WebElement quizTitle : quizDivs) {
            String quizTitleText = quizTitle.findElement(By.xpath("./span")).getText();
            //TODO: delete later
            System.out.println(quizTitleText);
            if (quizTitleText.equals(title)) {
                return quizTitle;
            }
        }
        return null;
    }

    private List<WebElement> getQuizzes(){
        WebElement quizzesDiv = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='grow pt-16']")));
        return quizzesDiv.findElements(By.xpath("./div[contains(@class, 'flex flex-row border-2 m-2 p-1 rounded-md')]"));
    }

    public void editQuiz(String title) {
        WebElement quiz = getQuizByTitle(title);
        if (quiz != null) {
            WebElement editButton = wait.until(ExpectedConditions.visibilityOf(quiz.findElement(By.xpath(".//button[text()='Edit']"))));
            editButton.click();
        }
    }
    public void deleteQuiz(String title) {
        WebElement quiz = getQuizByTitle(title);
        if (quiz != null) {
            WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(quiz.findElement(By.xpath("//button[text()='Delete']"))));
            deleteButton.click();
            handleConfirmationAlert(true);
        }
    }
    public void playQuiz(String title) {
        WebElement quiz = getQuizByTitle(title);
        if (quiz != null) {
            WebElement playButton = wait.until(ExpectedConditions.elementToBeClickable(quiz.findElement(By.xpath("//button[text()='Play']"))));
            playButton.click();
            wait.until(ExpectedConditions.elementToBeClickable(createGameLobbyButton)).click();
            wait.until(ExpectedConditions.elementToBeClickable(startGameButton)).click();
        }
    }

    public void deleteAllQuizzes(){
        List<WebElement> allUserQuizzes = getQuizzes();
        for (WebElement quiz : allUserQuizzes) {
            String quizTitleText = quiz.findElement(By.xpath("./span")).getText();
            deleteQuiz(quizTitleText);
        }
    }

    private void handleConfirmationAlert(boolean ok) {
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        assertNotNull(alert);
        if (ok) {
            alert.accept();
        } else {
            alert.dismiss();
        }
    }

    public void startInvalidGame(String title){
        WebElement quiz = getQuizByTitle(title);
        if (quiz != null) {
            WebElement playButton = quiz.findElement(By.xpath("//button[text()='Play']"));
            playButton.click();
            wait.until(ExpectedConditions.elementToBeClickable(createGameLobbyButton)).click();
            wait.until(ExpectedConditions.elementToBeClickable(notValidGameButton)).click();

        }
    }

}
