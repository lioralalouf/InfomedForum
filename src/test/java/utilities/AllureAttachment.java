package utilities;

import org.openqa.selenium.WebDriver;

import io.qameta.allure.Attachment;

public class AllureAttachment {

	@Attachment(value = "{0}", type = "text/plain", fileExtension = ".txt")
	public static String attachText(String message) {
		return message;
	}

	public static void attachScreenshot(WebDriver webDriverAttribute) {
		// TODO Auto-generated method stub
		
	}
}
