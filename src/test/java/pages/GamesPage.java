package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class GamesPage {
    private final FluentWait<WebDriver> wait;

    public GamesPage(WebDriver driver, FluentWait<WebDriver> wait) {
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    private List<WebElement> getGamesList(){
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='flex flex-row border-2 m-2 p-1 rounded-md']")));
    }

    public WebElement getGameByName(String gameName){
        return getGamesList().stream().filter(element -> element.findElement(By.xpath("//div[@class='grow pt-16']//div//span")).getText().contains(gameName)).toList().getFirst();
    }

    public void clickJoinGameByName(String gameName){
        WebElement gameToJoin = getGameByName(gameName);
        gameToJoin.findElement(By.xpath("//button[normalize-space()='Join']")).click();
    }

    public void renamePlayer(String playerName) {
        WebElement playerNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("playerName")));
        WebElement joinButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Join']")));

        playerNameField.sendKeys(playerName);
        joinButton.click();
    }
}
