package resources;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReusableMethods {

	protected WebDriver driver;
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	public ReusableMethods(WebDriver driver) {
		this.driver = driver;
	}

	public void waitTilEleVisible(WebElement ele) {
		wait.until(ExpectedConditions.visibilityOf(ele));
	}

	public void elementToBeClickable(WebElement ele) {

		wait.until(ExpectedConditions.elementToBeClickable(ele)).click();;
	}

	public void moveToSpecificElement(WebElement ele) {
		Actions actions = new Actions(driver);
		actions.moveToElement(ele);
	}

}
