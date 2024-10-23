package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public class MainPage {
    private final FluentWait<WebDriver> wait;
    @FindBy(xpath = "//*[text()='Logout']")
    private WebElement logout;
    @FindBy(xpath = "//*[text()='Sign up']")
    private WebElement signUp;
    @FindBy(xpath = "//*[text()='My Quizzes']")
    private WebElement myQuizzes;
    @FindBy(xpath = "//*[text()='Quizzes']")
    private WebElement allQuizzes;


    public MainPage(WebDriver driver, FluentWait<WebDriver> wait) {
        PageFactory.initElements(driver, this);
        this.wait = wait;
    }

    public void clickSignUp() {
        wait.until(ExpectedConditions.elementToBeClickable(signUp));
        signUp.click();
    }
    public void clickLogout() {
        wait.until(ExpectedConditions.elementToBeClickable(logout));
        logout.click();
    }

    public void clickMyQuizzes() {
        wait.until(ExpectedConditions.elementToBeClickable(myQuizzes));
        myQuizzes.click();
    }
    public void clickAllQuizzes() {
        wait.until(ExpectedConditions.elementToBeClickable(allQuizzes));
        allQuizzes.click();
    }

}
