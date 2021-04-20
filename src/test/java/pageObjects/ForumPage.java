package pageObjects;

import java.sql.Date;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Step;

public class ForumPage extends BasePage {
	@FindBy(css = ".forumButtons>li .newForumTopicPopup")
	private WebElement askQuestionBtn;
	@FindBy(css = "[name='topic']")
	private WebElement topicInput;
	@FindBy(css = "[name='content']")
	private WebElement contentInput;
	@FindBy(css = "[name='fullName']")
	private WebElement fullNameInput;
	@FindBy(css = "[name='email']")
	private WebElement emailInput;
	@FindBy(css = ".askQuestion_chkInput [type='checkbox']")
	private WebElement mailingCheckbox;
	@FindBy(css = "#submitAddComment")
	private WebElement submitBtn;
	@FindBy(css = "#sidePanelSuccessMessage h4")
	private WebElement sendingTitle;
	@FindBy(css = "#sidePanelSuccessMessage  a")
	private WebElement closeSuccessMsgBtn;
	@FindBy(css = "#freeText")
	private WebElement searchField;
	@FindBy(css = ".relatedForum_list li strong")
	private List<WebElement> listOfTopics;
	@FindBy(css = ".relatedForum_question")
	private List<WebElement> listOfPosts;
	@FindBy(css = ".relatedForum_questionAuthor span")
	private List<WebElement> listOfDates;
	@FindBy(css = ".relatedForum_questionFrame strong")
	private WebElement topicFrame;
	@FindBy(css = ".newForumTopicPopup span")
	private WebElement addResponseBtn;
	@FindBy(css = ".innerMessageButtons > a > span")
	private WebElement openNewBlank;

	public ForumPage(WebDriver driver) {
		super(driver);
	}

	@Step("fill in topic: {topic}, content: {content}, full name: {fullName} and email: {email} ")
	public void askQuestion(String topic, String content, String fullName, String email) {
		click(askQuestionBtn);
		fillText(topicInput, topic);
		fillText(contentInput, content);
		fillText(fullNameInput, fullName);
		fillText(emailInput, email);
		// click(mailingCheckbox);
		sleep(2000);
		scrollMouse(submitBtn);
		click(submitBtn);
		sleep(4000);

	}

	@Step("getting the success text: {שאלתך/תגובתך התקבלה!}")
	public String getSuccessMsg() {
		return getText(sendingTitle);

	}

	@Step("close success popup window")
	public void clickClose() {
		click(closeSuccessMsgBtn);
	}

	public boolean searchForMyTopic(String textSearched, String textFound) {
		click(searchField);
		sleep(1500);
		fillText(searchField, textSearched);
		waitForLoad(driver);
		sleep(2000);
		for (WebElement el : listOfTopics) {
			if (el.getText().equalsIgnoreCase(textFound)) {
				return true;
			}
		}
		return false;
	}

	public boolean searchForMyTopic3(String textSearched, String textFound) {
		// sleep(2*60*1000);
		click(searchField);
		sleep(1500);
		fillText(searchField, textSearched);
		waitForLoad(driver);
		sleep(2000);
		for (WebElement el : listOfPosts) {
			WebElement postName = el.findElement(By.cssSelector(".relatedForum_questionFrame strong"));
			WebElement postDate = el.findElement(By.cssSelector(".relatedForum_questionAuthor span"));
			String topicName = getText(postName);
			String topicDay = getText(postDate);
			System.out.println(topicName);
			System.out.println(topicDay);
			System.out.println(getCurrentDay());
			if ((topicName.equalsIgnoreCase(textFound))) {
				if (topicDay.contains(getCurrentDay()))
					return true;
			}
		}
		return false;
	}

	public String getCurrentDay() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	public void getd() {
		Date date = (Date) Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		String strDate = dateFormat.format(date);
		System.out.println("Converted String: " + strDate);
	}

	public void AddResponse(String text, String topic, String content, String fullName, String email) {
		for (WebElement el : listOfPosts) {
			WebElement postName = el.findElement(By.cssSelector(".relatedForum_questionFrame strong"));
			WebElement postDate = el.findElement(By.cssSelector(".relatedForum_questionAuthor span"));
			String topicName = getText(postName);
			String topicDay = getText(postDate);
			if ((topicName.equalsIgnoreCase(text))) {
				if (topicDay.contains(getCurrentDay()))
				sleep(2000);
			    WebDriverWait wait = new WebDriverWait(driver, 30);
			    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".relatedForum_questionFrame strong")));
			    wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".relatedForum_questionFrame strong")));
				click(postName);
				String main = driver.getWindowHandle();
				click(openNewBlank);
				Set<String> list = driver.getWindowHandles();
				for (String win : list) {
					driver.switchTo().window(win);
				}
				//scrollMouse(addResponseBtn);
			    wait = new WebDriverWait(driver, 30);
			    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".newForumTopicPopup span")));
			    wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".newForumTopicPopup span")));
				click(addResponseBtn);
				fillText(topicInput, topic);
				fillText(contentInput, content);
				fillText(fullNameInput, fullName);
				fillText(emailInput, email);
				// click(mailingCheckbox);
				sleep(2000);
				scrollMouse(submitBtn);
				click(submitBtn);
				sleep(1500);
				clickClose();

			}

		}

	}
	public void newWindow() {
		String main = driver.getWindowHandle();
		click(openNewBlank);
		Set<String> list = driver.getWindowHandles();
		for (String win : list) {
			driver.switchTo().window(win);
		}
	}
}
