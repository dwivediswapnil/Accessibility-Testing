package allyTest;

import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.deque.axe.AXE;

public class AmazonAllyTest {

	WebDriver driver;
	private static final URL scriptUrl = AmazonAllyTest.class.getResource("/axe.min.js");

	@BeforeMethod
	public void setup() {
		driver = new ChromeDriver();
		driver.get("https://www.amazon.in/");
	}

	@Test
	public void amazonAllyTest() {
		JSONObject responseJson = new AXE.Builder(driver, scriptUrl).analyze();
		JSONArray violations = responseJson.getJSONArray("violations");
		if (violations.length() == 0) {
			System.out.println("No errors");
		} else {
			AXE.writeResults("amazonAllyTest", responseJson);
			Assert.assertTrue(false, AXE.report(violations));
		}

	}

	@Test
	public void testAccessabilitySelector() {
		JSONObject responseJson = new AXE.Builder(driver, scriptUrl).include("title").analyze();
		JSONArray violations = responseJson.getJSONArray("violations");
		if (violations.length() == 0) {
			System.out.println("No errors");
		} else {
			AXE.writeResults("testAccessabilitySelector", responseJson);
			Assert.assertTrue(false, AXE.report(violations));
		}
	}

	@Test
	public void testAccessabilityWithWebElement() {
		JSONObject responseJson = new AXE.Builder(driver, scriptUrl)
				.analyze(driver.findElement(By.linkText("Customer Service")));
		JSONArray violations = responseJson.getJSONArray("violations");
		if (violations.length() == 0) {
			System.out.println("No errors");
		} else {
			AXE.writeResults("testAccessabilityWithWebElement", responseJson);
			Assert.assertTrue(false, AXE.report(violations));
		}
	}

	// new AxeBuilder(driver,scriptUrl).analyze(driver);

//	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
