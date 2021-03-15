package tests;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import io.qameta.allure.Attachment;
import utils.Utils;

public class BaseTest {
	WebDriver driver;

	@BeforeClass
	public void setup(ITestContext testContext) {
		System.setProperty("webdriver.chrome.driver", "C:\\automation\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(Utils.readProperty("url"));
		testContext.setAttribute("WebDriver", this.driver);
	}

	/*
	 * @BeforeClass public void validLogin() { HomePage hp = new HomePage(driver);
	 * hp.pressLogin(); LoginPage lp = new LoginPage(driver);
	 * lp.login("alalouflior@gmail.com", "Sean151113");
	 * 
	 * }
	 */
	//@AfterClass
	public void tearDown() {
		driver.quit();
	}

	/*
	 * This method will run after wach test, it will take screen shot only for tests
	 * that failed
	 */
	@AfterMethod
	public void failedTest(ITestResult result) {
		// check if the test failed
		if (result.getStatus() == ITestResult.FAILURE) {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File srcFile = ts.getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(srcFile, new File("./ScreenShots/" + result.getName() + ".jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// result.getname() method will give you current test case name.
			// ./ScreenShots/ tell you that, in your current directory, create folder
			// ScreenShots. dot represents current directory
		}
	}

}
