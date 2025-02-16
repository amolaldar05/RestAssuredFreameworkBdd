package baseTestComponent;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;
import resources.Utils;

public class BaseClass {
	protected WebDriver driver;
	Utils utils = new Utils();
	String browserName;

	@BeforeMethod(alwaysRun = true)
	public void setUp() throws IOException {
		
		browserName = utils.getGlobalValues("browser");
		initializeBrowser();
		launchUrl();
	}

	private void initializeBrowser() {

		switch (browserName) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		case "edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;
		default:
			throw new IllegalArgumentException("Invalid browser name provided: " + browserName);
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

	private void launchUrl() throws IOException {
		String url = utils.getGlobalValues("url");
		if (url == null || url.isEmpty()) {
			throw new IllegalArgumentException("URL is not specified in properties file.");
		}
		driver.get(url);
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	public WebDriver getDriver() {
		return driver;
	}
}
