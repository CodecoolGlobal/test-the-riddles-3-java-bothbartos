package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;


public class LoginPage {
    private final FluentWait<WebDriver> wait;
    @FindBy(id = "user-name")
    private WebElement username;
    @FindBy(id = "password")
    private WebElement password;
    @FindBy(xpath = "//*[text()='LOGIN']")
    private WebElement loginButton;

    public LoginPage(WebDriver driver, FluentWait<WebDriver> wait) {
        PageFactory.initElements(driver, this);
        this.wait = wait;
    }
    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOf(this.username));
        this.username.sendKeys(username);
    }
    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(this.password));
        this.password.sendKeys(password);
    }
    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public boolean isLoginButtonPresent() {
        try{
            wait.until(ExpectedConditions.visibilityOf(this.loginButton));
            return true;
        }catch (NoSuchElementException e){
            return false;
        }
    }
}
