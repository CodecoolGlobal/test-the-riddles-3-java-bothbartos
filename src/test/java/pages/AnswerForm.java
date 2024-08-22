package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;

public class AnswerForm {
    private WebDriverWait wait;
    private WebDriver driver;

    public AnswerForm(WebDriver driver, WebDriverWait wait) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.wait = wait;
    }

    @FindBy(xpath = "//button[text()='+ Add option']")
    private WebElement addOptionButton;

    @FindBy(xpath = "//input[contains(text(), 'time')]")
    private WebElement timeLimitField;
    @FindBy(xpath = "//button[text()='Save']")
    private WebElement saveQuestionButton;
    @FindBy(xpath = "//button[text()='Delete']")
    private WebElement deleteQuestionButton;
    @FindBy(xpath = "//button[contains(text(), '1')]")
    private WebElement questionButton;

    @FindBy(xpath = "//input[@id='answer-1']")
    private WebElement firstAnswerField;
    @FindBy(xpath = "//input[@id='answer-2']")
    private WebElement secondAnswerField;
    @FindBy(xpath = "//input[@id='answer-3']")
    private WebElement thirdAnswerField;
    @FindBy(xpath = "//input[@id='answer-4']")
    private WebElement fourthAnswerField;
    @FindBy(xpath = "//input[@id='answer-5']")
    private WebElement fifthAnswerField;
    @FindBy(xpath = "//input[@id='answer-6']")
    private WebElement sixthAnswerField;

    @FindBy(xpath = "//input[@id='checkbox-1']")
    private WebElement firstCheckBox;
    @FindBy(xpath = "//input[@id='checkbox-2']")
    private WebElement secondCheckBox;
    @FindBy(xpath = "//input[@id='checkbox-3']")
    private WebElement thirdCheckBox;
    @FindBy(xpath = "//input[@id='checkbox-4']")
    private WebElement fourthCheckBox;
    @FindBy(xpath = "//input[@id='checkbox-5']")
    private WebElement fifthCheckBox;
    @FindBy(xpath = "//input[@id='checkbox-6']")
    private WebElement sixthCheckBox;
    @FindBy(xpath = "//input[contains(@id, 'time')]")
    private WebElement timer;


    public void clickOnAddOptionButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addOptionButton));
        addOptionButton.click();
    }

    public void enterAnswer(WebElement answerField, WebElement checkbox, String answer, boolean isRight) {
        wait.until(ExpectedConditions.visibilityOf(answerField));
        answerField.sendKeys(answer);
        if (isRight) {
            wait.until(ExpectedConditions.visibilityOf(checkbox));
            checkbox.click();
        }
    }

    public void enterFirstAnswer(String answer, boolean isRight) {
        wait.until(ExpectedConditions.visibilityOf(firstAnswerField));
        firstAnswerField.sendKeys(answer);
        if (isRight) {
            wait.until(ExpectedConditions.visibilityOf(firstCheckBox));
            firstCheckBox.click();
        }
    }

    public void enterSecondAnswer(String answer, boolean isRight) {
        wait.until(ExpectedConditions.visibilityOf(secondAnswerField));
        secondAnswerField.sendKeys(answer);
        if (isRight) {
            wait.until(ExpectedConditions.visibilityOf(secondCheckBox));
            secondCheckBox.click();
        }
    }

    public void enterThirdAnswer(String answer, boolean isRight) {
        wait.until(ExpectedConditions.visibilityOf(thirdAnswerField));
        thirdAnswerField.sendKeys(answer);
        if (isRight) {
            wait.until(ExpectedConditions.visibilityOf(thirdCheckBox));
            thirdCheckBox.click();
        }
    }

    public void enterFourthAnswer(String answer, boolean isRight) {
        wait.until(ExpectedConditions.visibilityOf(fourthAnswerField));
        fourthAnswerField.sendKeys(answer);
        if (isRight) {
            wait.until(ExpectedConditions.visibilityOf(fourthCheckBox));
            fourthCheckBox.click();
        }
    }

    public void enterFifthAnswer(String answer, boolean isRight) {
        wait.until(ExpectedConditions.visibilityOf(fifthAnswerField));
        fifthAnswerField.sendKeys(answer);
        if (isRight) {
            wait.until(ExpectedConditions.visibilityOf(fifthCheckBox));
            fifthCheckBox.click();
        }
    }

    public void enterSixthAnswer(String answer, boolean isRight) {
        wait.until(ExpectedConditions.visibilityOf(sixthAnswerField));
        sixthAnswerField.sendKeys(answer);
        if (isRight) {
            wait.until(ExpectedConditions.visibilityOf(sixthCheckBox));
            sixthCheckBox.click();
        }
    }

    public void clickSaveQuestionButton() {
        wait.until(ExpectedConditions.elementToBeClickable(saveQuestionButton));
        saveQuestionButton.click();
    }


    public void setTimer(String time) {
        wait.until(ExpectedConditions.visibilityOf(timer));
        timer.clear();
        timer.sendKeys(time);
    }

    public void clickOnQuestionButton() {
        wait.until(ExpectedConditions.elementToBeClickable(questionButton));
        questionButton.click();
    }

    public String getTimerText() {
        wait.until(ExpectedConditions.visibilityOf(timer));
        return timer.getAttribute("value");
    }

    public boolean isFirstTwoCheckboxesChecked() {
        return wait.until(ExpectedConditions.elementToBeClickable(firstCheckBox)).isSelected() && wait.until(ExpectedConditions.elementToBeClickable(secondCheckBox)).isSelected();
    }

    public void enterAnswers(Map<String, Boolean> answers) {
        List<WebElement> answerFields = new LinkedList<>();
        answerFields.add(firstAnswerField);
        answerFields.add(secondAnswerField);
        answerFields.add(thirdAnswerField);
        answerFields.add(fourthAnswerField);
        answerFields.add(fifthAnswerField);
        answerFields.add(sixthAnswerField);
        List<WebElement> checkboxFields = new LinkedList<>();
        checkboxFields.add(firstCheckBox);
        checkboxFields.add(secondCheckBox);
        checkboxFields.add(thirdCheckBox);
        checkboxFields.add(fourthCheckBox);
        checkboxFields.add(fifthCheckBox);
        checkboxFields.add(sixthCheckBox);

        for (int i = 0; i < answers.size(); i++) {
            WebElement answerField = answerFields.get(i);
            WebElement checkboxField = checkboxFields.get(i);
            String answer = (String) answers.keySet().toArray()[i];
            boolean isCorrect = answers.get(answer);
            enterAnswer(answerField, checkboxField, answer, isCorrect);
        }
    }
}
