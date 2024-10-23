package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public class QuizFormPage {
    private final FluentWait<WebDriver> wait;
    private final AnswerForm answerForm;

    @FindBy(xpath = "//button[text()='Add Question']")
    private WebElement addQuestionButton;

    @FindBy(xpath = "//button[text()='Save quiz']")
    private WebElement saveQuizButton;
    @FindBy(xpath = "//button[text()='Delete quiz']")
    private WebElement deleteQuizButton;

    @FindBy(xpath = "//button[contains(text(),'1')]")
    private WebElement firstQuestionButton;

    @FindBy(id = "name")
    private WebElement quizName;
    @FindBy(xpath = "//input[@id='-1question']")
    private WebElement questionField;


    public QuizFormPage(WebDriver driver, FluentWait<WebDriver> wait, AnswerForm answerForm) {
        PageFactory.initElements(driver, this);
        this.wait = wait;
        this.answerForm = answerForm;
    }
    public void enterQuizTitle(String quizTitle) {
        wait.until(ExpectedConditions.visibilityOf(quizName));
        quizName.clear();
        quizName.sendKeys(quizTitle);
    }
    public void clickOnAddQuestionButton() {
        wait.until(ExpectedConditions.visibilityOf(addQuestionButton));
        addQuestionButton.click();
    }
    public void enterQuestion(String question) {
        wait.until(ExpectedConditions.visibilityOf(questionField));
        questionField.sendKeys(question);
    }

    public void clickSaveQuizButton() {
        wait.until(ExpectedConditions.elementToBeClickable(saveQuizButton));
        saveQuizButton.click();
    }

    public void clickOnDeleteButton() {
        wait.until(ExpectedConditions.visibilityOf(deleteQuizButton));
        deleteQuizButton.click();
    }

    private void clickOnFirstQuestionButton() {
        wait.until(ExpectedConditions.elementToBeClickable(firstQuestionButton));
        firstQuestionButton.click();
    }

    public boolean isEveryCheckboxChecked(){
        clickOnFirstQuestionButton();
        return answerForm.isFirstTwoCheckboxesChecked();
    }
}
