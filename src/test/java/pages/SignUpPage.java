package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public class SignUpPage {
    private final FluentWait<WebDriver> wait;
    @FindBy(id = "user-name")
    private WebElement usernameField;
    @FindBy(id = "password")
    private WebElement passwordField;
    @FindBy(id = "email")
    private WebElement emailField;
    @FindBy(xpath = "//*[text()='SIGN UP']")
    private WebElement signUpButton;

    public SignUpPage(WebDriver driver, FluentWait<WebDriver> wait) {
        PageFactory.initElements(driver, this);
        this.wait = wait;
    }

    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOf(usernameField));
        this.usernameField.sendKeys(username);
    }
    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailField));
        this.emailField.sendKeys(email);
    }
    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordField));
        this.passwordField.sendKeys(password);
    }
    public void clickSignUpButton() {
        wait.until(ExpectedConditions.visibilityOf(signUpButton));
        this.signUpButton.click();
    }

    public void signUp(String username, String email, String password) {
        enterUsername(username);
        enterEmail(email);
        enterPassword(password);
        clickSignUpButton();
    }
}
