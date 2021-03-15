package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import pageObjects.ForumPage;
import utilities.AllureAttachment;

@Epic("Forum")
@Feature("publish topic on forum")
public class ForumTest extends BaseTest {
	@Severity(SeverityLevel.CRITICAL)
	@Story("As A user, I want to publish new topic and get success message for the action.")
	@Test(description = "create new topic in forum")
	@Description("create new forum topic and check it has been uploaded to forum page")
	public void CreateNewTopic() {
		ForumPage fp = new ForumPage(driver);
		fp.askQuestion("topic", "content", "liora", "liora@odoro.co.il");
		Assert.assertEquals(fp.getSuccessMsg(), "שאלתך/תגובתך התקבלה!");
		AllureAttachment.attachText("found succesfully the following text - " + fp.getSuccessMsg());
	}

	@Severity(SeverityLevel.CRITICAL)
	@Story("As A user, I want my topic to be uploaded to forum page.")
	@Test(description = "search my topic appear on the topics list")
	@Description("after 2 minutes from topic creation, check the my topic has bben uploaded to the forum page")
	public void checkTopicIsOnTheList() {
		ForumPage fp = new ForumPage(driver);
		fp.clickClose();
		boolean actual = fp.searchForMyTopic("topic", "topic (לת)");
		Assert.assertTrue(actual, "The searched topic has not been found on the list");
	}
}
