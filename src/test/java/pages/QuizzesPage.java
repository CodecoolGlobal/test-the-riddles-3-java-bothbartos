package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class QuizzesPage {
    private FluentWait<WebDriver> wait;

    @FindBy(xpath = "//button[normalize-space()='Add Quiz']")
    private WebElement addQuizButton;

    public QuizzesPage(WebDriver driver, FluentWait<WebDriver> wait) {
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    private List<WebElement> getQuizzes(){
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[1]/div")));
    }

    public WebElement getQuizByTitle(String title){
        return getQuizzes().stream().filter(webElement -> webElement.getText().contains(title)).toList().getFirst();
    }

    public void clickAddQuizButton(){
        wait.until(ExpectedConditions.elementToBeClickable(addQuizButton)).click();
    }

    public void copyQuizByTitle(String title){
        WebElement quizDiv = getQuizByTitle(title);
        wait.until(ExpectedConditions.visibilityOf(quizDiv.findElement(By.xpath("//button[text()='Copy']")))).click();
    }
}
