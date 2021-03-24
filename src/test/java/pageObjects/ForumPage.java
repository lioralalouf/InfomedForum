package pageObjects;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
		ScrollMouse(submitBtn);
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
		sleep(1500);
		for (WebElement el : listOfTopics) {
			if (el.getText().equalsIgnoreCase(textFound)) {
				return true;
			}
		}
		return false;
	}
}
