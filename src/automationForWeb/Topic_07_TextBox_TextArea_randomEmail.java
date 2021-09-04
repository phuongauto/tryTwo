package automationForWeb;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import static org.testng.Assert.assertTrue;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class Topic_07_TextBox_TextArea_randomEmail {
	WebDriver driver;
	String loginPageURL, userIDtext, passwordText, name, dob, dobCompare, address, city, state, pin, mobile, email, customerID;
	String editAddress, editCity, editState, editMobile, editPin;
	
	By hereBtn = By.xpath("//a[text()='here']");
	By emailTextboxRegisterPage = By.xpath("//input[@name='emailid']");
	By submitRegisterPage = By.xpath("//input[@name='btnLogin']");
	By userID = By.xpath("//td[text()='User ID :']/following-sibling::td");
	By pword = By.xpath("//td[text()='Password :']/following-sibling::td");
	By emailLogin = By.xpath("//input[@name='uid']");
	By passLogin = By.xpath("//input[@name='password']");
	By btnLogin = By.xpath("//input[@name='btnLogin']");
	By welcomeMs = By.xpath("//marquee[contains(text(), \"Welcome To Manager's Page of Guru99 Bank\")]");
	By newCustomerBtn = By.xpath("//a[text()='New Customer']");
	By editCustomerBtn = By.xpath("//a[text()='Edit Customer']");
	By nameTextbox = By.name("name");
	By genderRadio = By.name("gender");
	By dobTextbox = By.name("dob");
	By addressTextArea = By.name("addr");
	By cityTextbox = By.name("city");
	By stateTextbox = By.name("state");
	By pinTextbox = By.name("pinno");
	By mobileTextbox = By.name("telephoneno");
	By emailTextbox = By.name("emailid");
	By passwordTextbox = By.name("password");

	// Random number for random email
	public int randomNumber() {
		Random random = new Random();
		int number = random.nextInt(999999);
		System.out.println("Random number = " + number);
		return number;
	}
	
	String emailRandom = "autoxendit" + randomNumber() + "@gmail.com";
	String editEmailRandom = "phuonggoco" + randomNumber() + "@gmail.com";
	// Here we do not need to manually change email and editEmail every time we run test case
	
	@BeforeTest
	public void beforeTest() {
//		System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/winLib/geckodriver.exe");
//		driver = new FirefoxDriver();
		
		System.setProperty("webdriver.chrome.driver", ".\\winLib\\chromedriver.exe");
		driver = new ChromeDriver();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://demo.guru99.com/v4/");
		name = "phuong yeah";
		dob = "30-12-1989";
		// format of input dob is different to format of output dob, so we need "dobCompare" to verify
		dobCompare = "1989-12-30";
		address = "564 Suitable Address";
		city = "New York";
		state = "California";
		pin = "400000";
		mobile = "0982075723";
		editAddress = "16825 Northchase Dr";
		editCity = "Houston";
		editState = "Texas";
		editMobile = "0374555688";
		editPin = "77060";
	}

	@Test
	public void TC_01_Register() throws Exception {
		// Store login page url to use later
		loginPageURL = driver.getCurrentUrl();
		clickToElement(hereBtn);
		sendkeyToElement(emailTextboxRegisterPage, "naucom@gmail.com");
		clickToElement(submitRegisterPage);
		userIDtext = driver.findElement(userID).getText();
		passwordText = driver.findElement(pword).getText();
		// naucom@gmail.com
		// mngr350163 bAdEnaz
	}

	@Test
	public void TC_02_Login() throws Exception {
		driver.navigate().to(loginPageURL);
		sendkeyToElement(emailLogin, userIDtext);
		sendkeyToElement(passLogin, passwordText);
		clickToElement(btnLogin);
		Assert.assertTrue(isElementDisplayed(welcomeMs));
		Assert.assertTrue(driver.findElement(By.xpath("//td[text()='Manger Id : " + userIDtext + "']")).isDisplayed());
	}

	@Test
	public void TC_03_New_Customer() throws Exception {
		clickToElement(newCustomerBtn);
		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='Add New Customer']")).isDisplayed());
		// input valid data
		sendkeyToElement(nameTextbox, name);
		sendkeyToElement(dobTextbox, dob);
		sendkeyToElement(addressTextArea, address);
		sendkeyToElement(cityTextbox, city);
		sendkeyToElement(stateTextbox, state);
		sendkeyToElement(pinTextbox, pin);
		sendkeyToElement(mobileTextbox, mobile);
		sendkeyToElement(emailTextbox, emailRandom);
		sendkeyToElement(passwordTextbox, passwordText);
		Thread.sleep(2000);
		driver.findElement(By.name("sub")).click();

		// verify inputed data
		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='Customer Registered Successfully!!!']")).isDisplayed());
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(), name);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText(), dobCompare);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(),address);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(),city);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(),state);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(),pin);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(), mobile);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(), emailRandom);

		// Get and store CustomerID here
		customerID = driver.findElement(By.xpath("//td[text()='Customer ID']/following-sibling::td")).getText();
	}

	@Test
	public void TC_04_Edit_Customer() throws Exception {
		clickToElement(editCustomerBtn);
		driver.findElement(By.name("cusid")).sendKeys(customerID);
		driver.findElement(By.name("AccSubmit")).click();

		// Verify 3 fields are disabled
		Assert.assertFalse(isElementEnabled(nameTextbox));
		Assert.assertFalse(isElementEnabled(genderRadio));
		Assert.assertFalse(isElementEnabled(dobTextbox));

		// Verify data
		Assert.assertEquals(driver.findElement(nameTextbox).getAttribute("value"), name);
		Assert.assertEquals(driver.findElement(dobTextbox).getAttribute("value"), dobCompare);
		Assert.assertEquals(driver.findElement(addressTextArea).getAttribute("value"), address);
		Assert.assertEquals(driver.findElement(cityTextbox).getAttribute("value"), city);
		Assert.assertEquals(driver.findElement(stateTextbox).getAttribute("value"), state);
		Assert.assertEquals(driver.findElement(pinTextbox).getAttribute("value"), pin);
		Assert.assertEquals(driver.findElement(mobileTextbox).getAttribute("value"), mobile);
		Assert.assertEquals(driver.findElement(emailTextbox).getAttribute("value"), emailRandom);

		// Edit customer
		sendkeyToElement(addressTextArea, editAddress);
		sendkeyToElement(cityTextbox, editCity);
		sendkeyToElement(stateTextbox, editState);
		sendkeyToElement(pinTextbox, editPin);
		sendkeyToElement(mobileTextbox, editMobile);
		sendkeyToElement(emailTextbox, editEmailRandom);
		driver.findElement(By.name("sub")).click();

		// verify data after edit
		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='Customer details updated Successfully!!!']")).isDisplayed());
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(), name);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText(), dobCompare);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(),editAddress);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(),editCity);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(),editState);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(),editPin);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(), editMobile);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(),editEmailRandom);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Customer ID']/following-sibling::td")).getText(), customerID);
	}

	public boolean isElementDisplayed(By by) {
		WebElement element = driver.findElement(by);
		if (element.isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isElementEnabled(By by) {
		WebElement element = driver.findElement(by);
		if (element.isEnabled()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isElementSelected(By by) {
		WebElement element = driver.findElement(by);
		if (element.isSelected()) {
			return true;
		} else {
			return false;
		}
	}

	public void clickToElement(By by) {
		WebElement element = driver.findElement(by);
		element.click();
	}

	public void sendkeyToElement(By by, String value) {
		WebElement element = driver.findElement(by);
		element.clear();
		element.sendKeys(value);
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}

}