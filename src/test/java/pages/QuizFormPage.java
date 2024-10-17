package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class QuizFormPage {
    private FluentWait<WebDriver> wait;
    private AnswerForm answerForm;

    @FindBy(xpath = "//button[text()='Add Quiz']")
    private WebElement addQuizButton;

    @FindBy(xpath = "//button[text()='Add Question']")
    private WebElement addQuestionButton;

    @FindBy(xpath = "//button[text()='Save quiz']")
    private WebElement saveQuizButton;
    @FindBy(xpath = "//button[text()='Delete quiz']")
    private WebElement deleteQuizButton;

    @FindBy(xpath = "//button[contains(text(),'1')]")
    private WebElement firstQuestionButton;
    @FindBy(xpath = "//button[contains(text(),'2')]")
    private WebElement secondQuestionButton;
    @FindBy(xpath = "//button[contains(text(),'3')]")
    private WebElement thirdQuestionButton;
    @FindBy(xpath = "//button[contains(text(),'4')]")
    private WebElement fourthQuestionButton;
    @FindBy(xpath = "//button[contains(text(),'5')]")
    private WebElement fifthQuestionButton;
    @FindBy(xpath = "//button[contains(text(),'6')]")
    private WebElement sixthQuestionButton;

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

    public void clickOnAddQuizButton(){
        wait.until(ExpectedConditions.visibilityOf(addQuizButton));
        addQuizButton.click();
    }

    public void clickSaveQuizButton() {
        wait.until(ExpectedConditions.visibilityOf(saveQuizButton));
        saveQuizButton.click();
    }

    public void createNewQuiz(String title, String question){
        clickOnAddQuizButton();
        enterQuizTitle(title);
        clickOnAddQuestionButton();
        enterQuestion(question);
    }

    public void clickOnDeleteButton() {
        wait.until(ExpectedConditions.visibilityOf(deleteQuizButton));
        deleteQuizButton.click();
    }

    private void clickOnFirstQuestionButton() {
        wait.until(ExpectedConditions.visibilityOf(firstQuestionButton));
        firstQuestionButton.click();
    }

    public boolean isEveryCheckboxChecked(){
        clickOnFirstQuestionButton();
        return answerForm.isFirstTwoCheckboxesChecked();
    }
}
