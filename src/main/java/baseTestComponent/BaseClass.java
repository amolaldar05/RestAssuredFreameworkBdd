package baseTestComponent;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import PageObjects.HeaderPageObjects;
import io.github.bonigarcia.wdm.WebDriverManager;
import resources.Utils;

public class BaseClass {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    Utils utils = new Utils();
    private String browserName;
    private String url;
    private boolean isHeadless;

    @BeforeMethod(alwaysRun = true)
    public HeaderPageObjects launchApp() throws IOException {
        browserName = utils.getGlobalValues("browser");
        url = utils.getGlobalValues("naukariGulfURL");
        String headlessMode = utils.getGlobalValues("headless");
        isHeadless = headlessMode != null && headlessMode.equalsIgnoreCase("true");
        System.out.println("Browser: " + browserName + " | URL: " + url + " | Headless: " + isHeadless);
        initializeBrowser();
        launchUrl();
        return new HeaderPageObjects(getDriver());
    }

    private void initializeBrowser() {
        switch (browserName.toLowerCase()) {
        case "chrome":
        	WebDriverManager.chromedriver().clearDriverCache().setup();
            ChromeOptions chromeOptions = new ChromeOptions();
            if (isHeadless) {
                chromeOptions.addArguments("--headless=new"); // Use updated headless mode
                chromeOptions.addArguments("--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage");
                chromeOptions.addArguments("--window-size=1920,1080"); // Ensure full rendering
                chromeOptions.addArguments("--remote-allow-origins=*");
            }
            driver.set(new ChromeDriver(chromeOptions));

            break;
        case "firefox":
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            if (isHeadless) {
                firefoxOptions.addArguments("-headless");
            }
            driver.set(new FirefoxDriver(firefoxOptions));
            break;
        case "edge":
            WebDriverManager.edgedriver().setup();
            EdgeOptions edgeOptions = new EdgeOptions();
            if (isHeadless) {
                edgeOptions.addArguments("--headless", "--disable-gpu", "--window-size=1920,1080");
            }
            driver.set(new EdgeDriver(edgeOptions));
            break;
        default:
            throw new IllegalArgumentException("Invalid browser name provided: " + browserName);
        }

        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    private void launchUrl() throws IOException {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("URL is not specified in properties file.");
        }
        getDriver().get(url);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            getDriver().quit();
        }
    }

    public WebDriver getDriver() {
        return driver.get();
    }
}

//package baseTestComponent;
//
//import java.io.IOException;
//import java.time.Duration;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.edge.EdgeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//
//import PageObjects.HeaderPageObjects;
//import io.github.bonigarcia.wdm.WebDriverManager;
//import resources.Utils;
//
//public class BaseClass {
//
//	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
//	Utils utils = new Utils();
//	private String browserName;
//	private String url;
//
//	@BeforeMethod(alwaysRun = true)
//	public HeaderPageObjects launchApp() throws IOException {
//		browserName = utils.getGlobalValues("browser");
//		url = utils.getGlobalValues("naukariGulfURL");
//		System.out.println("Browser: " + browserName + " | URL: " + url); // Debug line
//		initializeBrowser();
//		launchUrl();
//		return new HeaderPageObjects(getDriver());
//	}
//
//	private void initializeBrowser() {
//		switch (browserName.toLowerCase()) {
//		case "chrome":
//			WebDriverManager.chromedriver().setup();
//			driver.set(new ChromeDriver());
//			break;
//		case "firefox":
//			WebDriverManager.firefoxdriver().setup();
//			driver.set(new FirefoxDriver());
//			break;
//		case "edge":
//			WebDriverManager.edgedriver().setup();
//			driver.set(new EdgeDriver());
//			break;
//		default:
//			throw new IllegalArgumentException("Invalid browser name provided: " + browserName);
//		}
//
//		getDriver().manage().window().maximize();
//		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//	}
//
//	private void launchUrl() throws IOException {
//		// url = utils.getGlobalValues("naukriGulfURL");
//		if (url == null || url.isEmpty()) {
//			throw new IllegalArgumentException("URL is not specified in properties file.");
//		}
//		getDriver().get(url);
//	}
//
//	@AfterMethod(alwaysRun = true)
//	public void tearDown() {
//		if (driver != null) {
//			getDriver().quit();
//		}
//	}
//
//	public WebDriver getDriver() {
//		return driver.get();
//	}
//}