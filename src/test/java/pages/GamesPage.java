package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import java.util.List;

public class GamesPage {
    private final FluentWait<WebDriver> wait;

    public GamesPage(WebDriver driver, FluentWait<WebDriver> wait) {
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    private List<WebElement> getGamesList(){
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//div[contains(@class, 'flex') and contains(@class, 'flex-row') and contains(@class, 'border-2') and contains(@class, 'm-2') and contains(@class, 'p-1') and contains(@class, 'rounded-md')]")
        ));
    }

    public WebElement getGameByName(String gameName){
        List<WebElement> gamesList = getGamesList();
        for(WebElement game: gamesList){
            if(game.getText().contains(gameName)){
                return game;
            }
        }
        return null;
    }

    public void clickJoinGameByName(String gameName){
        WebElement gameToJoin = getGameByName(gameName);
        gameToJoin.findElement(By.xpath(".//button[normalize-space()='Join']")).click();
        wait.until(ExpectedConditions.urlContains("game/quiz"));
    }

}
