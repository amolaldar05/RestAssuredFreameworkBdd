import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Test {

	public static void main(String[] args) throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		WebDriver driver= new ChromeDriver();
		driver.get("https://www.naukrigulf.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.findElement(By.xpath("//a[contains(@class,'nav-link')][normalize-space()='Login']")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("loginModalLoginEmail")).sendKeys("amolaldar05@gmail.com");
		driver.findElement(By.id("loginPassword")).sendKeys("Naukarigulf@7799");
		Thread.sleep(2000);
		driver.findElement(By.id("loginModalLoginSubmit")).click();
		Thread.sleep(2000);
		//driver.close();
		

	}

}
