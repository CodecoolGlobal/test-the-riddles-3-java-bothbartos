package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    private final WebDriverWait wait;
    @FindBy(xpath = "//*[text()='Logout']")
    private WebElement logout;
    @FindBy(xpath = "//*[text()='Login']")
    private WebElement login;
    @FindBy(xpath = "//*[text()='Sign up']")
    private WebElement signUp;
    @FindBy(xpath = "//*[text()='Account']")
    private WebElement account;
    @FindBy(xpath = "//*[text()='My Quizzes']")
    private WebElement myQuizzes;
    @FindBy(xpath = "//*[text()='Quizzes']")
    private WebElement allQuizzes;
    @FindBy(xpath = "//*[text()='Games']")
    private WebElement games;
    @FindBy(xpath = "//*[text()='Reptile Riddles']")
    private WebElement reptileRiddles;

    public MainPage(WebDriver driver, WebDriverWait wait) {
        PageFactory.initElements(driver, this);
        this.wait = wait;
    }
    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(login));
        login.click();
    }
    public void clickSignUp() {
        wait.until(ExpectedConditions.elementToBeClickable(signUp));
        signUp.click();
    }
    public void clickLogout() {
        wait.until(ExpectedConditions.elementToBeClickable(logout));
        logout.click();
    }
    public void clickAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(account));
        account.click();
    }
    public void clickMyQuizzes() {
        wait.until(ExpectedConditions.elementToBeClickable(myQuizzes));
        myQuizzes.click();
    }
    public void clickAllQuizzes() {
        wait.until(ExpectedConditions.elementToBeClickable(allQuizzes));
        allQuizzes.click();
    }
    public void clickGames() {
        wait.until(ExpectedConditions.elementToBeClickable(games));
        games.click();
    }
    public void clickReptileRiddles() {
        wait.until(ExpectedConditions.elementToBeClickable(reptileRiddles));
        reptileRiddles.click();
    }

}
