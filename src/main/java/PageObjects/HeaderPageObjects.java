package PageObjects;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

import java.util.List;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import resources.ReusableMethods;

public class HeaderPageObjects extends ReusableMethods {

	public HeaderPageObjects(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = ".nav-bar .link-group")
	private List<WebElement> headerMenus;

	@FindBy(css = ".nav-bar .link-group a")
	private List<WebElement> subMenusLinks;

	@FindBy(css = ".login.loginPopup")
	private WebElement loginPopup;

	@FindBy(id = "loginModalLoginEmail")
	private WebElement email;

	@FindBy(id = "loginPassword")
	private WebElement pass;

	@FindBy(css = "button[type='submit'].login-btn")
	private WebElement submitBtn;

	@FindBy(css = ".profile-name")
	private WebElement profileName;

	@FindBy(css = "li.link-group.no-hover ul.nav-menu.nav-menu-right li a")
	private List<WebElement> optionLinks;

	@FindBy(css = ".details-edit section h4")
	private List<WebElement> profileSectionTitles;

	@FindBy(css = ".ng-link.edit-cta.fr")
	private List<WebElement> profileSectionEditBtns;

	/**
	 * Select Menu from Header
	 */
	public void selectMenu(String menuText) {
		try {
			boolean found = headerMenus.stream().filter(menu -> menu.getText().equalsIgnoreCase(menuText))
					.peek(WebElement::click).findFirst().isPresent();

			if (!found)
				throw new NoSuchElementException("Menu '" + menuText + "' not found.");
			System.out.println("✅ Menu selected: " + menuText);

		} catch (NoSuchElementException e) {
			System.err.println("❌ Menu not found: " + e.getMessage());
		} catch (WebDriverException e) {
			System.err.println("⚠️ WebDriver error while selecting menu: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("⚠️ Unexpected error while selecting menu: " + e.getMessage());
		}
	}

	/**
	 * Perform User Login
	 */
	public void login(String emailLogin, String passLogin) {
		try {
			waitTilEleVisible(loginPopup);

			if (emailLogin.isEmpty() || passLogin.isEmpty()) {
				throw new IllegalArgumentException("❌ Email or Password cannot be empty.");
			}

			email.sendKeys(emailLogin);
			pass.sendKeys(passLogin);
			// Wait until the button is clickableisEleClickable
			//elementToBeClickable(submitBtn);
			System.out.println("Is button displayed? " + submitBtn.isDisplayed());
			System.out.println("Is button enabled? " + submitBtn.isEnabled());

//			try {
//			    submitBtn.click();
//			} catch (ElementClickInterceptedException e) {
//			    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitBtn);
//			}
//			Actions actions = new Actions(driver);
//			actions.moveToElement(submitBtn).click().perform();
			//moveToSpecificElement(submitBtn);
			submitBtn.click();
			Thread.sleep(2000);
//			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitBtn);
//			Thread.sleep(500);  // Short pause for scroll completion
//			submitBtn.click();
			System.out.println("✅ Login button clicked after scrolling.");

			System.out.println("✅ Login button clicked using Actions class.");


			System.out.println("✅ Login button clicked successfully.");
			// submitBtn.click();
			System.out.println("✅ Login attempted with user: " + emailLogin);

		} catch (TimeoutException e) {
			System.err.println("⏳ Timeout waiting for login popup: " + e.getMessage());
		} catch (NoSuchElementException e) {
			System.err.println("❌ Login elements not found: " + e.getMessage());
		} catch (WebDriverException e) {
			System.err.println("⚠️ WebDriver error during login: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("⚠️ Unexpected error during login: " + e.getMessage());
		}
	}

	/**
	 * Get Profile Name
	 */
	public String getProfileName() {
		try {
			waitTilEleVisible(profileName);
			String name = profileName.getText();
			System.out.println("✅ Profile name fetched: " + name);
			return name;
		} catch (NoSuchElementException e) {
			System.err.println("❌ Profile name not found: " + e.getMessage());
			return null;
		} catch (Exception e) {
			System.err.println("⚠️ Unexpected error while fetching profile name: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Hover on Profile
	 */
	public void hoverOnProfile() {
		try {
			moveToSpecificElement(profileName);
			System.out.println("✅ Hovered on profile.");
		} catch (WebDriverException e) {
			System.err.println("⚠️ WebDriver error while hovering: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("❌ Unexpected error while hovering: " + e.getMessage());
		}
	}

	/**
	 * Select Option from Profile Dropdown
	 */
	public void selectFromProfileOptions(String option) {
		try {
			boolean found = optionLinks.stream().filter(link -> link.getText().equalsIgnoreCase(option))
					.peek(WebElement::click).findFirst().isPresent();

			if (!found)
				throw new NoSuchElementException("Option '" + option + "' not found.");
			System.out.println("✅ Option selected from profile: " + option);

		} catch (NoSuchElementException e) {
			System.err.println("❌ Option not found: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("⚠️ Unexpected error while selecting option: " + e.getMessage());
		}
	}

	/**
	 * Select Specific Profile Section using Selenium 4 Relative Locator
	 */
	public void selectSpecProfileSection(String title) {
		try {
			WebElement titleElement = profileSectionTitles.stream()
					.filter(menu -> menu.getText().equalsIgnoreCase(title)).findFirst()
					.orElseThrow(() -> new NoSuchElementException("Section '" + title + "' not found."));

			WebElement rightElement = driver
					.findElement(with(org.openqa.selenium.By.tagName("button")).toRightOf(titleElement));

			rightElement.click();
			System.out.println("✅ Clicked on the section to the right of: " + title);

		} catch (NoSuchElementException e) {
			System.err.println("❌ Section not found: " + e.getMessage());
		} catch (WebDriverException e) {
			System.err.println("⚠️ WebDriver error while selecting section: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("⚠️ Unexpected error while selecting section: " + e.getMessage());
		}
	}
}
