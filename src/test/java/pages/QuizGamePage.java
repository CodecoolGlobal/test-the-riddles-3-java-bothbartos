package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import java.util.List;

public class QuizGamePage {
    private final FluentWait<WebDriver> wait;

    @FindBy(xpath = ".//button[text()='Start']")
    private WebElement startButton;
    @FindBy(xpath = ".//button[text()='Results']")
    private WebElement resultButton;
    @FindBy(xpath = ".//button[text()='Join']")
    private WebElement joinButton;
    @FindBy(id = "playerName")
    private WebElement playerNameField;
    @FindBy(xpath = ".//div[@class=' w-full col-span-2 font-bold text-xl']")
    private WebElement scoreField;

    public QuizGamePage(WebDriver driver, FluentWait<WebDriver> wait) {
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public void clickStartButton() {
        wait.until(ExpectedConditions.elementToBeClickable(startButton)).click();
    }

    public void clickJoinButton() {
        wait.until(ExpectedConditions.elementToBeClickable(joinButton)).click();
    }

    public List<WebElement> getQuizAnswers(){
        WebElement answersDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//html/body/div/div/div[2]")));
        return answersDiv.findElements(By.xpath(".//div/button"));
    }

    public void clickAnswerButton(String answer){
        List<WebElement> answers = getQuizAnswers();
        for(WebElement answerDiv : answers){
            if(answerDiv.getText().equals(answer)){
                wait.until(ExpectedConditions.elementToBeClickable(answerDiv)).click();
                break;
            }
        }
    }

    public void clickResultButton(){
        wait.until(ExpectedConditions.elementToBeClickable(resultButton)).click();
    }

    public boolean isUsernameOnScoreBoard(String playerName) {
        WebElement scoreBoardDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='m-auto mt-10 w-5/6 h-3/6 bg-zinc-500 p-3 grid grid-cols-1']")));
        return scoreBoardDiv.getText().contains(playerName);
    }

    public boolean isScoreHigherThanZero(){
        return Integer.parseInt(scoreField.getText()) > 0;
    }

    public void renamePlayer(String newPlayerUsername) {
        wait.until(ExpectedConditions.visibilityOf(playerNameField)).sendKeys(newPlayerUsername);
    }
}
